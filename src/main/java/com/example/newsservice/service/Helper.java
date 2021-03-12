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

    public static RuleSet readFile() {
        String  fileName = "/Users/beyzayazici/Desktop/NewsProject/DataSet.json";
        try {
            String jsonString=new String(Files.readAllBytes(Paths.get(fileName)));
            Gson gson=new Gson();
            RuleSet ruleSet = gson.fromJson(jsonString,RuleSet.class);
            List<Rule> rules =ruleSet.getRule();
            for(Rule rule:rules){
                List<Condition> conditions=rule.getCondition();
                for(Condition condition:conditions){
                    if (condition.getValue() != null) {
                        condition.setValues(Arrays.asList(condition.getValue().split(",")));
                    }
                }
            }

        return ruleSet;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
