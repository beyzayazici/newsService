package com.example.newsservice.model;

public class Movie {
    private String id;
    private String overview;
    private boolean adult;
    private String release_date;
    private double vote_average;
    private String original_title;

    public String getRelease_date()
    {
        return release_date;
    }

    public void setRelease_date(String release_date)
    {
        this.release_date = release_date;
    }

    public boolean isAdult()
    {
        return adult;
    }

    public void setAdult(boolean adult)
    {
        this.adult = adult;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOverview()
    {
        return overview;
    }

    public void setOverview(String overview)
    {
        this.overview = overview;
    }

    public double getVote_average()
    {
        return vote_average;
    }

    public void setVote_average(double vote_average)
    {
        this.vote_average = vote_average;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }
}