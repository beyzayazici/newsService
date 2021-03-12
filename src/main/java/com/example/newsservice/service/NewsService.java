package com.example.newsservice.service;

import com.example.newsservice.model.News;
import com.example.newsservice.model.RuleSet;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NewsService {

    public List<RuleSet> getRuleSet()
    {
        List<RuleSet> rules=Helper.readFile();


        return rules;
    }

    public List<News> callNewsApi(RestTemplate restTemplate, int pageNo, int limit)
    {
        String url = "http://mock.artiwise.com/api/news?_page="+ pageNo + "&_limit=" + limit;
        List<News> news = (List<News>) restTemplate.getForObject(url, News.class);

        return news;
    }

}
