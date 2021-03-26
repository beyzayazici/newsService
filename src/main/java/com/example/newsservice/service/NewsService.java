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
import java.io.IOException;
import java.util.*;

@Service
public class NewsService {

    private final String CONDITION_NAME="keywords";
    public HashMap<String, List<News>> callNewsApi(HttpServletResponse response, RestTemplate restTemplate, int pageNo, int limit) throws IOException {
        String url = "http://mock.artiwise.com/api/news?_page=" + pageNo + "&_limit=" + limit;
        try {
            List<News> news = Arrays.asList(restTemplate.getForEntity(url, News[].class).getBody());
            ExcelExporter excelExporter = new ExcelExporter(findRuleNews(news));
            excelExporter.createSheetAndContent(response);
            return findRuleNews(news);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<News> callGetNewsApi(HttpServletResponse response, RestTemplate restTemplate, int pageNo, int limit) {
        String url = "http://mock.artiwise.com/api/news?_page=" + pageNo + "&_limit=" + limit;
        List<News> news = Arrays.asList(restTemplate.getForEntity(url, News[].class).getBody());
        return news;
    }

    public HashMap<String, List<News>> findRuleNews(List<News> news) {
        //readFile methods map configuration file to Rule object.
        //configuration file must be replaced with the actual configuration file.
        List<RuleSet> ruleSets = Helper.readFile();
        HashMap<String, List<News>> results = new HashMap<>();

            for (RuleSet ruleSet : ruleSets) {
                results.put(ruleSet.getName(), isNewsRuleMatch(news, ruleSet));
            }

        return results;
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
                .addKeywords(condition.getValues())
                .build();

        for(News n : news) {
            text = getValuesAsString(n, condition);
            emits = trie.parseText(text);

            if (condition.getKey().equals(CONDITION_NAME) && isMatchAllCase(condition, emits)) {
                if(isEndRuleOfCondition)
                    n.setRules(rule + ", ");
                newsList.add(n);

            } else if(isMatchAnyCase(condition, emits)){
                if(isEndRuleOfCondition)
                    n.setRules(rule + ", ");
                newsList.add(n);
            }
        }

        return newsList;
    }

    private boolean isMatchAnyCase(Condition condition, Collection<Emit> emits) {
       if(!condition.getKey().equals(CONDITION_NAME) && !emits.isEmpty()){
           return true;
       }
        return false;
    }

    public void getNormalization(News news) {
        String result = "";

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
                return Helper.convertListToString(news.getTags());
            case "categories":
                return Helper.convertListToString(news.getCategories());
            default:
                return "";
        }
    }

    private boolean isMatchAllCase(Condition condition, Collection<Emit> emits) {

        for (String s : condition.getValues()) {
            if (!emits.stream().anyMatch(f -> f.getKeyword().equals(s))) {
                return false;
            }
        }
        return true;
    }

    private boolean isMatchAnyCase(Condition condition, Collection<Emit> emits, News news) {
        if (!emits.isEmpty()) {
            news.setRules(condition.getKey() + ",");
            return true;
        }
        return false;
    }
}
