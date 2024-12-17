package com.amalitech.realtimesentimentanalysis.services;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;
import scala.Function1;

import java.util.Arrays;

@Service
public class SentimentService {

    public void analyzeSentiment(String inputText) {
        SparkSession spark = SparkSession.builder()
                .appName("Sentiment Analysis")
                .master("local[*]") // For local testing
                .getOrCreate();

        // Simulate data: Convert input text into Spark Dataset
        Dataset<Row> inputData = spark.createDataset(Arrays.asList(inputText), org.apache.spark.sql.Encoders.STRING())
                .toDF("text");

        // Basic sentiment analysis logic (for demonstration)
        Dataset<Row> sentimentResults = inputData.map((Function1<Row, String>) row -> {
            String text = row.getString(0).toLowerCase();
            if (text.contains("happy") || text.contains("good") || text.contains("great")) {
                return "Positive Sentiment";
            } else if (text.contains("sad") || text.contains("bad") || text.contains("terrible")) {
                return "Negative Sentiment";
            } else {
                return "Neutral Sentiment";
            }
        }, org.apache.spark.sql.Encoders.STRING()).toDF("sentiment");

        // Show results
        sentimentResults.show();

        // Clean up
        spark.stop();
    }
}