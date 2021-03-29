package com.example.newsservice.model;

import java.util.List;

public class News {
    private String id;
    private String url;
    private String name;
    private String lang;
    private String type;
    private List<String> tags;
    private List<String> categories;
    private String title;
    private String description;
    private String content;
    private String crawl_date;
    private String published_date;
    private String modified_date;
    private String text;
    private String rules;

    public News() {

    }

    public News(String id, String url, String name, String lang, String type, List<String> tags, List<String> categories, String title, String description, String content, String crawl_date, String published_date, String modified_date) {
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
        this.crawl_date = crawl_date;
        this.published_date = published_date;
        this.modified_date = modified_date;
    }

    public String getId() {
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

    public List<String> getTags() {
        return tags;
    }

    public List<String> getCategories() {
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

    public String getCrawl_date() {
        return crawl_date;
    }

    public String getPublished_date() {
        return published_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public String getText() {
        return text;
    }

    public String getRules() {
        return rules;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
