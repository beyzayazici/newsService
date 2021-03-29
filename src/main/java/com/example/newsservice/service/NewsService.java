package com.example.newsservice.service;

import com.example.newsservice.ExcelExporter;
import com.example.newsservice.model.Condition;
import com.example.newsservice.model.News;
import com.example.newsservice.model.Rule;
import com.example.newsservice.model.RuleSet;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class NewsService {

    private final String CONDITION_NAME="keywords";

    public void callNewsApi(HttpServletResponse response, RestTemplate restTemplate, int pageNo, int limit) {
        String url = "http://mock.artiwise.com/api/news?_page=" + pageNo + "&_limit=" + limit;
        try {
            List<News> news = Arrays.asList(restTemplate.getForEntity(url, News[].class).getBody());
            ExcelExporter excelExporter = new ExcelExporter(findRuleNews(news));
            excelExporter.createSheetAndContent(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<News> callGetNewsApi(RestTemplate restTemplate, int pageNo, int limit) {
        String url = "http://mock.artiwise.com/api/news?_page=" + pageNo + "&_limit=" + limit;
        return Arrays.asList(restTemplate.getForEntity(url, News[].class).getBody());
    }

    public HashMap<String, List<News>> findRuleNews(List<News> news) {
        //readFile method map configuration file that is Rule object.
        //Configuration file must be replaced with the actual configuration file as json.
        //Note : Configuration file separator is comma (,). Make sure don't add any expression after comma.
        List<RuleSet> ruleSets = Helper.readFile();
        HashMap<String, List<News>> results = new HashMap<>();
        try {
            if(ruleSets != null) {
                for (RuleSet ruleSet : ruleSets) {
                    results.put(ruleSet.getName(), isNewsRuleMatch(news, ruleSet));
                }
                return results;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<News> isNewsRuleMatch(List<News> news, RuleSet ruleSet) {
        Iterator<Condition> conditionIterator;
        List<News> resultNews = new ArrayList<>();
        List<News> ruleNews;
        boolean isEndRuleOfCondition = false;
        Condition nextCondition;

        for (Rule rule : ruleSet.getRule()) {
            conditionIterator = rule.getCondition().iterator();
            ruleNews = news;

            while (conditionIterator.hasNext() && ruleNews.size() != 0) {
                nextCondition = conditionIterator.next();
                if(!conditionIterator.hasNext())
                    isEndRuleOfCondition = true;
                ruleNews = ahoCorasickAlgorithm(ruleNews, nextCondition, rule.getRuleName(), isEndRuleOfCondition);
            }
            resultNews.addAll(ruleNews);
        }

        return resultNews;
    }

    public List<News> ahoCorasickAlgorithm(List<News> news, Condition condition, String rule, boolean isEndRuleOfCondition) {
        String text;
        Collection<Emit> emits;
        List<News> newsList = new ArrayList<>();

        // apply all rules for every news in (isMatchAllCase and  isMatchAnyCase) methods
        // int this method control for AND,OR rules of conditions.
        Trie trie = Trie.builder()
                //.onlyWholeWords()
                .ignoreOverlaps()
                .ignoreCase()
                .addKeywords(condition.getValue().split(","))
                .build();

        for(News n : news) {
            text = getValuesAsString(n, condition);
            emits = trie.parseText(text);

            if (condition.getKey().equals(CONDITION_NAME) && isMatchAllCase(condition, emits)) {
                if(isEndRuleOfCondition)
                    addRules(n, rule);
                newsList.add(n);
            } else if(isMatchAnyCase(condition, emits)){
                if(isEndRuleOfCondition)
                    addRules(n, rule);
                newsList.add(n);
            }
        }

        return newsList;
    }

    private void addRules(News n, String rule) {
        if(n.getRules() == null)
            n.setRules(rule);
        else
            n.setRules(n.getRules() + "," + rule);
    }

    private boolean isMatchAnyCase(Condition condition, Collection<Emit> emits) {
        return !condition.getKey().equals(CONDITION_NAME) && !emits.isEmpty();
    }

    public void getNormalization(News news) {
        String result;

        if (news.getDescription() != null) {
            result = news.getTitle() + news.getDescription() + news.getContent();
        } else {
            result = news.getTitle() + news.getContent();
        }
        news.setText(Helper.normalization(result, news.getLang()));
    }

    private String getValuesAsString(News news, Condition condition) {
        switch (condition.getKey()) {
            case "keywords":
                getNormalization(news);
                return news.getText();
            case "lang":
                return news.getLang();
            case "type":
                return news.getType();
            case "tags":
                return news.getTags().toString();
            case "categories":
                return news.getCategories().toString();
            default:
                return "";
        }
    }

    private boolean isMatchAllCase(Condition condition, Collection<Emit> emits) {
        for (String s : condition.getValue().split(",")) {
            if (emits.stream().noneMatch(f -> f.getKeyword().equals(s)))
                return false;
        }
        return true;
    }
}
