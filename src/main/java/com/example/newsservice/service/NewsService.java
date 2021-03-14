package com.example.newsservice.service;

import com.example.newsservice.ExcelExporter;
import com.example.newsservice.model.News;
import com.example.newsservice.model.RuleSet;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class NewsService {

    public HashMap<String,List<News>> callNewsApi(HttpServletResponse response,RestTemplate restTemplate, int pageNo, int limit) throws IOException {
        //String url = "http://mock.artiwise.com/api/news?_page="+ pageNo + "&_limit=" + limit;
        //List<News> news = (List<News>) restTemplate.getForObject(url, News.class);
        try {
            List<News> news = mockData();
            ExcelExporter excelExporter = new ExcelExporter(findRuleNews(news));
            excelExporter.createSheetAndContent(response);
            return findRuleNews(news);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());;
        }
        return null;
    }

    private List<News> mockData() {
        List<News> news = new ArrayList();

        News news1 = new News(1, "www.atv.com.tr", "Covid", "TR", "Özel", "#news", "sağlık", "CoRo04na Virüs1# ama", "Dünya Sağlık 'Örgütü'", " Du54yuru ", "11.03.2020", "11.03.2020","11.03.2020");
        News news2 = new News(2, "www.atv1.com.tr", "Covid", "TR", "Özel", "#news", "sağlık", " C23orona+  belki4.    ", "Corona Virüse karşı %önlemler", "Cor2ona ", "11.03.2020", "11.03.2020","11.03.2020");
        news.add(news1);
        news.add(news2);

        return news;
    }

    public HashMap<String,List<News>> findRuleNews(List<News> news) {
        List<RuleSet> rules = Helper.readFile();
        HashMap<String,List<News>> results = new HashMap<String,List<News>>();

        for(News n : news){
            for(RuleSet ruleSet : rules){
                if(ahoCorasickAlgorithm(n, ruleSet)){
                    if(!results.containsKey(ruleSet.getName())){
                        List<News> keyNews = new ArrayList<>();
                        keyNews.add(n);
                        results.put(ruleSet.getName(), keyNews);
                    }
                    else{
                        results.get(ruleSet.getName()).add(n);
                    }
                }
            }
        }
        return results;
    }

    public boolean ahoCorasickAlgorithm(News news,RuleSet ruleSet){
        if(news!=null){
            return true;
        }
       return false;
    }
}
