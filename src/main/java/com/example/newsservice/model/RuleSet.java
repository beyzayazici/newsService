package com.example.newsservice.model;

import java.util.List;

public class RuleSet {
    private String name;
    private List<Rule> rule;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rule> getRule() {
        return rule;
    }

    public void setRule(List<Rule> rule) {
        this.rule = rule;
    }

}
