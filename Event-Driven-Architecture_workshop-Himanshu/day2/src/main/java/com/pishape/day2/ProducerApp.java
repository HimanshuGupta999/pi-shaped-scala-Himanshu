package com.pishape.day2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.UUID;

public class ProducerApp {
    private static final String TOPIC = "demo-topic";

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        ObjectMapper mapper = new ObjectMapper();

        for (int i = 1; i <= 10; i++) {
            String json = mapper.writeValueAsString(
                    new Event("user_login", UUID.randomUUID().toString())
            );
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, json);

            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("✅ Sent: " + json + " to partition " + metadata.partition());
                } else {
                    exception.printStackTrace();
                }
            });
        }

        producer.close();
    }

    static class Event {
        public String event;
        public String user_id;

        public Event(String event, String user_id) {
            this.event = event;
            this.user_id = user_id;
        }
    }
}

