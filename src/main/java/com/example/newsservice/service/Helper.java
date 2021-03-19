package com.example.newsservice.service;

import com.example.newsservice.model.Condition;
import com.example.newsservice.model.Rule;
import com.example.newsservice.model.RuleSet;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Helper {

    public static List<RuleSet> readFile() {

        String fileName = "src//main//resources//RuleSet.json";
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(fileName)));
            List<RuleSet> ruleSets = JsonMapper.readList(jsonString, RuleSet.class);

            for (RuleSet ruleSet : ruleSets) {
                List<Rule> rules = ruleSet.getRule();
                for (Rule rule : rules) {
                    List<Condition> conditions = rule.getCondition();
                    for (Condition condition : conditions) {
                        if (condition.getValue() != null) {
                            condition.setValues(Arrays.asList(condition.getValue().split(",")));
                        }
                    }
                }
            }

            return ruleSets;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String removeSpecialMarks(String text) {
        return text.replaceAll("[“’§½£]", "");
    }

    private static String removePunctuationMarks(String text) {
        return text.replaceAll("\\p{Punct}", "");
    }

    private static String removeNumbersOfString(String text) {
        return text.replaceAll("[0-9]", "");
    }

    public static String normalization(String text, String lang) {
        Locale locale;

        if(lang.equalsIgnoreCase("TR")) {
            locale = Locale.forLanguageTag("tr-TR");
        }
        else {
            locale = Locale.forLanguageTag("en-EN");
        }

        return removeSpecialMarks(removeStopWords(removeNumbersOfString(removePunctuationMarks(text)))).toLowerCase(locale).trim();

    }

    private static String removeStopWords(String text) {
        String pathName = "src//main//resources//TR_Stop_Words.txt";

        File file = new File(pathName);

        if (file.exists() && !file.isDirectory()) {
            try {
                List<String> stopwords = Files.readAllLines(Paths.get(pathName));
                StringBuilder builder = new StringBuilder();
                String[] allWords = text.split(" ");
                for (String word : allWords) {
                    if (!stopwords.contains(word)) {
                        builder.append(word);
                        builder.append(' ');
                    }
                }
                return builder.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    public static String convertListToString(List<String> values) {
        String newString = "";
        for (String s : values)
            newString = newString + " " + s;
        return newString;
    }

}
