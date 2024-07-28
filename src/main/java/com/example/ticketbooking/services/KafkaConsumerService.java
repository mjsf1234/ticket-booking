package com.example.ticketbooking.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = "my_topic", groupId = "group_category")
    public void consumeMessage(String message) {
        log.info("Consumed message {}", message);
    }
}
