package com.example.newsservice.model;

import java.util.List;

public class Rule {
    private String ruleName;
    private List<Condition> condition;

    public String getRuleName() {
        return ruleName;
    }

    public List<Condition> getCondition() {
        return condition;
    }
}
