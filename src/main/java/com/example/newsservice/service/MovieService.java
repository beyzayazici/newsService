package com.example.newsservice.service;

import com.example.newsservice.model.Movie;
import com.example.newsservice.model.MovieResult;
import com.example.newsservice.model.News;
import com.example.newsservice.model.RuleSet;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MovieService {

    public Movie callMovieApi(RestTemplate restTemplate, String movieId, String apiKey)
    {
        String url = "https://api.themoviedb.org/3/movie/"+ movieId + "?api_key=" + apiKey;
        Movie movie = restTemplate.getForObject(url, Movie.class);

        return movie;
    }


    public MovieResult callMovieListApi(RestTemplate restTemplate, String apiKey)
    {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=whiplash";
        MovieResult movieList = restTemplate.getForObject(url, MovieResult.class);
        //List<Movie> movieList = restTemplate.exchange(url, HttpMethod.GET, null,  new ParameterizedTypeReference<List<Movie>>() {}).getBody();

        return movieList;
    }
}
