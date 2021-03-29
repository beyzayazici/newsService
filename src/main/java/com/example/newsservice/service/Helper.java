package com.example.newsservice.service;

import com.example.newsservice.model.RuleSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

public class Helper {

    public static List<RuleSet> readFile() {

        String fileName = "src//main//resources//RuleSet.json";
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(fileName)));
            return JsonMapper.readList(jsonString, RuleSet.class);

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

    public static String removeNumbersOfString(String text) {
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

        return removeSpecialMarks(removeStopWords(removeNumbersOfString(removePunctuationMarks(text)), lang)).toLowerCase(locale).trim();

    }

    private static String removeStopWords(String text, String lang) {
        String pathName = "src//main//resources//TR_Stop_Words.txt";

        //adding if there is another language
        if(lang.equals("EN"))
            pathName = "src//main//resources//EN_Stop_Words.txt";

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    public static String convertListToString(List<String> values) {
        StringBuilder newString = new StringBuilder();
        for (String value : values)
            newString.append(value);
        return newString.toString();
    }
}
