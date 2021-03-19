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

    public HashMap<String, List<News>> callNewsApi(HttpServletResponse response, RestTemplate restTemplate, int pageNo, int limit) throws IOException {
        String url = "http://mock.artiwise.com/api/news?_page=" + pageNo + "&_limit=" + limit;
        //String url = "http://mock.artiwise.com/api/news";
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
        List<RuleSet> rules = Helper.readFile();
        HashMap<String, List<News>> results = new HashMap<>();
        for (News n : news) {
            for (RuleSet ruleSet : rules) {
                if (isNewsRuleMatch(n, ruleSet)) {
                    if (!results.containsKey(ruleSet.getName())) {
                        List<News> keyNews = new ArrayList<>();
                        keyNews.add(n);
                        results.put(ruleSet.getName(), keyNews);
                    } else {
                        results.get(ruleSet.getName()).add(n);
                    }
                }
            }
        }
        return results;
    }


    public String getNormalization(News news) {
        String result = "";

        if (news.getDescription() != null) {
            result = news.getTitle() + news.getDescription() + news.getContent();
        } else {
            result = news.getTitle() + news.getContent();
        }

        return Helper.normalization(result, news.getLang());
    }


    private String getValuesAsString(News news, Condition condition) {
        switch (condition.getKey()) {
            case "keywords":
                return getNormalization(news);
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

    public boolean ahoCorasickAlgorithm(News news, Condition condition) {

        Trie trie = Trie.builder()
                .ignoreOverlaps() //search in the text anywhere
                .ignoreCase()
                .addKeywords(condition.getValues())
                .build();

        String text = getValuesAsString(news, condition);

        Collection<Emit> emits = trie.parseText(text);

        if (condition.getKey().equals("keywords")) {
            return isMatchAllCase(condition, emits);

        } else {
            return isMatchAnyCase(emits);
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

    private boolean isMatchAnyCase(Collection<Emit> emits) {
        if (!emits.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isNewsRuleMatch(News news, RuleSet ruleSet) {
        boolean isMatchCondition;
        boolean isMatchRule = false;

        for (Rule rule : ruleSet.getRule()) {
            isMatchCondition = true;
            Iterator<Condition> conditionIterator = rule.getCondition().iterator();
            while (conditionIterator.hasNext() && isMatchCondition) {
                isMatchCondition = ahoCorasickAlgorithm(news, conditionIterator.next());
            }
            if (isMatchCondition) {
                if (news.getRules() != null) {
                    news.setRules(news.getRules() + "," + ruleSet.getName());
                } else {
                    news.setRules(ruleSet.getName() + ",");
                }
                isMatchRule = true;
            }

        }
        return isMatchRule;
    }
}
