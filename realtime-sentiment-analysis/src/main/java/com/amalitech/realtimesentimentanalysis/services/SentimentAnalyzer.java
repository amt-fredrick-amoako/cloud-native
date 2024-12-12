package com.amalitech.realtimesentimentanalysis.services;
public class SentimentAnalyzer {

    public static String analyze(String review) {
        if (review.toLowerCase().contains("good") || review.toLowerCase().contains("excellent")) {
            return "Positive";
        } else if (review.toLowerCase().contains("bad") || review.toLowerCase().contains("poor")) {
            return "Negative";
        }
        return "Neutral";
    }
}

