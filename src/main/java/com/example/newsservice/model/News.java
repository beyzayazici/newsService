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

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }


    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
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


    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCrawl_date() {
        return crawl_date;
    }

    public void setCrawl_date(String crawl_date) {
        this.crawl_date = crawl_date;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public boolean allMatchCondition(String text) {
        return true;
    }

    public boolean anyMatchCondition(String text, String condition) {
        return true;
    }
}
