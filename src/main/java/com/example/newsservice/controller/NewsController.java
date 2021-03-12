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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/news", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NewsService newsService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    @GetMapping
    public ResponseEntity<List<News>> getAllNews(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer limit)
    {
        return new ResponseEntity<List<News>>(newsService.callNewsApi(restTemplate, pageNo, limit), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<News> news = newsService.callNewsApi(restTemplate, 0, 100);

        //ExcelExporter excelExporter = new ExcelExporter(news);
        //excelExporter.export(response);
    }
     */
}