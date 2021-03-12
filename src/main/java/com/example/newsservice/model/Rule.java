package com.example.newsservice.model;

import java.util.List;

public class Rule {
    private String ruleName;
    private List<Condition> condition;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public List<Condition> getCondition() {
        return condition;
    }

    public void setCondition(List<Condition> condition) {
        this.condition = condition;
    }
}
