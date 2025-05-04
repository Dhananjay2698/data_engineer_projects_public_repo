package utils;

public class SentimentAnalyzer {
    public static String analyzeSentiment(String text) {
        text = text.toLowerCase();
        if (text.contains("moon") || text.contains("strong") || text.contains("gains") || text.contains("growth")) {
            return "Positive";
        } else if (text.contains("terrible") || text.contains("uncertain") || text.contains("selling")) {
            return "Negative";
        } else {
            return "Neutral";
        }
    }
}
