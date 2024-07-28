package com.example.ticketbooking.config;

import com.example.ticketbooking.events.UserDataEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.app.topic}")
    private String topic;

    public void sendUserDataEvent(UserDataEvent userDataEvent) throws ExecutionException, InterruptedException {
        log.info("Data Event received: {}", userDataEvent);

        try {
            String userDataJson = objectMapper.writeValueAsString(userDataEvent);
            log.info("Converted to JSON: {}", userDataJson);
            SendResult<String, String> data = kafkaTemplate.send(topic, userDataJson).get();
            log.info("Published the message: {}", data);
        } catch (Exception e) {
            log.error("Error serializing UserDataEvent", e);
            throw new RuntimeException("Error sending message to Kafka", e);
        }
    }
}