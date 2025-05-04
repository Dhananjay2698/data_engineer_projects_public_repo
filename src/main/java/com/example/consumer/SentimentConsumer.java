package com.example.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import utils.SentimentAnalyzer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SentimentConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("group.id", "sentiment-consumer-group");
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("social-media-posts"));

        System.out.println("SentimentConsumer started. Waiting for messages...");

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                String tweet = record.value();
                String sentiment = SentimentAnalyzer.analyzeSentiment(tweet);
                System.out.println("Message: " + tweet);
                System.out.println("Sentiment: " + sentiment);
            }
        }
    }
}
