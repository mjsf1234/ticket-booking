package com.example.ticketbooking.config;

import com.example.ticketbooking.events.UserDataEvent;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, UserDataEvent> kafkaTemplate;

    @Value("${spring.kafka.app.topic}")
    private String topic;

    public void sendUserDataEvent(UserDataEvent userDataEvent) {
        log.info(" Data Event received: {}", userDataEvent);

        kafkaTemplate.send(topic, userDataEvent);
    }
}