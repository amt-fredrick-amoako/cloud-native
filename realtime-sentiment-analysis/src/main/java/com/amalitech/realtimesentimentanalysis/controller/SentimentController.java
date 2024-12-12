package com.amalitech.realtimesentimentanalysis.controller;

import com.amalitech.realtimesentimentanalysis.services.SentimentAnalyzer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sentiment")
public class SentimentController {

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeSentiment(@RequestBody String review) {
        String sentiment = SentimentAnalyzer.analyze(review); // Call processing logic
        return ResponseEntity.ok("Sentiment: " + sentiment);
    }
}
