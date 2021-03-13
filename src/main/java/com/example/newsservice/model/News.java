package com.example.newsservice.model;

public class News {
    private long id;
    private String url;
    private String name;
    private String lang;
    private String type;
    private String tags;
    private String categories;
    private String title;
    private String description;
    private String content;
    private String crawl_Date;
    private String  modified_Date;
    private String publishedDate;
    private String text;
    private String rules;

    public News(long id, String url, String name, String lang, String type, String tags, String categories, String title, String description, String content, String crawl_Date, String modified_Date, String publishedDate) {
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
        this.crawl_Date = crawl_Date;
        this.modified_Date = modified_Date;
        this.publishedDate = publishedDate;
        this.text = setText();
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getLang() {
        return lang;
    }

    public String getType() {
        return type;
    }

    public String getTags() {
        return tags;
    }

    public String getCategories() {
        return categories;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public String getCrawl_Date() {
        return crawl_Date;
    }

    public String getModified_Date() {
        return modified_Date;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getText() {
        return text;
    }

    public String getRules() {
        return rules;
    }

    public String setText() {
        return this.title + this.description + this.content;
    }
}
