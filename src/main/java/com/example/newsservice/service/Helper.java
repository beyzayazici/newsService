package com.example.newsservice.service;

import com.example.newsservice.model.Condition;
import com.example.newsservice.model.Rule;
import com.example.newsservice.model.RuleSet;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Helper {

    public static List<RuleSet> readFile() {

        String  fileName = "DataSet.json";
        try {
            String jsonString=new String(Files.readAllBytes(Paths.get(fileName)));
            List<RuleSet> ruleSets = JsonMapper.readList(jsonString, RuleSet.class);

            for(RuleSet ruleSet: ruleSets) {
                List<Rule> rules = ruleSet.getRule();
                for(Rule rule:rules){
                    List<Condition> conditions=rule.getCondition();
                    for(Condition condition:conditions){
                        if (condition.getValue() != null) {
                            condition.setValues(Arrays.asList(condition.getValue().split(",")));
                        }
                    }
                }
            }

        return ruleSets;

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
