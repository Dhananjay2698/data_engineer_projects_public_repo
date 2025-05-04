package com.example.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class SocialMediaProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        String filePath = "src/main/resources/tweets.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int id = 1;
            while ((line = reader.readLine()) != null) {
                ProducerRecord<String, String> record = new ProducerRecord<>("social-media-posts", String.valueOf(id), line);
                producer.send(record);
                System.out.println("Produced: " + line);
                id++;
                Thread.sleep(500); // simulate streaming
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
