package com.example.newsservice.model;

public class News {
    private final long id;
    private String Name;

    public News(long id, String name)
    {
        this.id = id;
        Name = name;
    }

    /*
    private String url;
    private String name;
    private String lang;
    private String type;
    private String tags;
    private String categories;
    private String title;
    private String description;
    private String content;
    private Date crawlDate;
    private Date modifiedDate;
    private Date publishedDate;

    public News(long id, String url, String name, String lang, String type, String tags, String categories, String title, String description, String content, Date crawlDate, Date modifiedDate, Date publishedDate)
    {
        this.id = id;
        this.url = url;
        this.name = name;
        this.lang = lang;
        this.type = type;
        this.tags = tags;
        this.categories = categories;
        this.title = title;
        this.description = description;
        this.content = content;
        this.crawlDate = crawlDate;
        this.modifiedDate = modifiedDate;
        this.publishedDate = publishedDate;
    }
     */
}
