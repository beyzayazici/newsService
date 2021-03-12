package com.example.newsservice.controller;

import com.example.newsservice.ExcelExporter;
import com.example.newsservice.model.Movie;
import com.example.newsservice.model.MovieResult;
import com.example.newsservice.model.RuleSet;
import com.example.newsservice.service.MovieService;
import com.example.newsservice.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieService movieService;

    @Value("${api.key}")
    private String apiKey;

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovieInfo(@PathVariable("movieId") String movieId)
    {
        try {
            if(!movieId.isEmpty() && !apiKey.isEmpty()) {
                return new ResponseEntity<Movie>(movieService.callMovieApi(restTemplate, movieId, apiKey), new HttpHeaders(), HttpStatus.OK);
            }
        }
        catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    @GetMapping()
    public @ResponseBody ResponseEntity<MovieResult> getMovieList()
    {
        try {
            if(!apiKey.isEmpty()) {
                return new ResponseEntity<MovieResult>(movieService.callMovieListApi(restTemplate, apiKey), new HttpHeaders(), HttpStatus.OK);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());;
        }
        return null;
    }

    @GetMapping(value = "/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        Movie movie = movieService.callMovieApi(restTemplate, "150", apiKey);
        ExcelExporter excelExporter = new ExcelExporter(movie);
        excelExporter.export(response);

    }



}


