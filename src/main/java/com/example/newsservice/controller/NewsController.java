package com.example.newsservice.controller;

import com.example.newsservice.model.News;
import com.example.newsservice.service.NewsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(path = "/api/news")
public class NewsController {
    private final RestTemplate restTemplate;

    private final NewsService newsService;

    public NewsController(RestTemplate restTemplate, NewsService newsService) {
        this.restTemplate = restTemplate;
        this.newsService = newsService;
    }

    @GetMapping("/{pageNo}/{limit}")
    public @ResponseBody ResponseEntity<List<News>> getNewsList(@PathVariable int pageNo, @PathVariable int limit) {
        try {
            return new ResponseEntity<>(newsService.callGetNewsApi(restTemplate, pageNo, limit), new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping(value = "/export/excel/{pageNo}/{limit}")
    public void exportToExcel(HttpServletResponse response, @PathVariable int pageNo, @PathVariable int limit) {
        newsService.callNewsApi(response, restTemplate, pageNo, limit);
    }
}
