package com.example.newsservice;

import com.example.newsservice.model.News;
import com.example.newsservice.service.NewsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
public class NewsServiceTest {

    private NewsService newsService;

    @Before
    public void setup(){
        newsService=new NewsService();
    }

    @Test
    public void ahoCorasickAlgorithmRuleControl(){
        List<News> newsMockData=mockData();
        HashMap<String,List<News>> hashMap= newsService.findRuleNews(newsMockData);
        assertThat(hashMap.keySet().size()).isEqualTo(2);
    }


    private List<News> mockData() {
        List<News> news = new ArrayList();

        News news1 = new News(1, "www.atv.com.tr", "Covid", "TR", "Özel", "#news", "sağlık", "Corona Virüs", "Dünya Sağlık Örgütü", "Duyuru", "11.03.2020", "11.03.2020","11.03.2020");
        News news2 = new News(2, "www.atv1.com.tr", "Covid", "TR", "Özel", "#news", "sağlık", "Corona", "Corona Virüse karşı önlemler", "Corona", "11.03.2020", "11.03.2020","11.03.2020");
        news.add(news1);
        news.add(news2);

        return news;
    }


}
