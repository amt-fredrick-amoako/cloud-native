package com.amalitech.realtimesentimentanalysis.controller;

import com.amalitech.realtimesentimentanalysis.services.SentimentAnalyzer;
import com.amalitech.realtimesentimentanalysis.services.SentimentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/sentiment")
public class SentimentController {
    private SentimentService sentimentService;

    public SentimentController(SentimentService sentimentService) {
        this.sentimentService =  sentimentService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeSentiment(@RequestBody String review) {
        CompletableFuture.runAsync(()-> sentimentService.analyzeSentiment(review));
        return ResponseEntity.ok("Sentiment analysis job submitted successfully");
    }

    @GetMapping("/bulkAnalyze")
    public ResponseEntity<String> triggerSparkJob() {
        try {
            // Command to execute the Spark application
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "java", "-jar", "/path/to/spark-sentiment.jar");

            Process process = processBuilder.start();

            // Capture the output (optional, for debugging/logging)
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return ResponseEntity.ok("Spark Job Triggered Successfully! Output:\n" + output);
            } else {
                return ResponseEntity.status(500).body("Spark Job Failed with Exit Code: " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error while triggering Spark Job: " + e.getMessage());
        }
    }
}
