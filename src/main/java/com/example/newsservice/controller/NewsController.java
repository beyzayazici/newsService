package com.example.newsservice.controller;

import com.example.newsservice.ExcelExporter;
import com.example.newsservice.model.News;
import com.example.newsservice.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/api/news")
public class NewsController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NewsService newsService;

    @GetMapping("/{pageNo}/{limit}")
    public @ResponseBody ResponseEntity<List<News>> getNewsList(HttpServletResponse response, @PathVariable int pageNo, @PathVariable int limit)
    {
        try {
            return new ResponseEntity<List<News>>(newsService.callGetNewsApi(response, restTemplate, pageNo, limit), new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());;
        }
        return null;
    }

    @GetMapping(value = "/export/excel/{pageNo}/{limit}")
    public void exportToExcel(HttpServletResponse response, @PathVariable int pageNo, @PathVariable int limit) throws IOException {
        newsService.callNewsApi(response, restTemplate, pageNo, limit);
    }
}
