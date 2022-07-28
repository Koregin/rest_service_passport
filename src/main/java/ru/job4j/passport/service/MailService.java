package ru.job4j.passport.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.job4j.passport.model.Passport;

import java.util.List;

@Service
public class MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class.getSimpleName());

    @KafkaListener(topics = "passport")
    public void sendMail(ConsumerRecord<Long, List<Passport>> record) {
        LOGGER.info("Sending email...");
        LOGGER.info("Topic: " + record.topic());
        LOGGER.info("Key: " + record.key());
        LOGGER.info(String.valueOf(record.value()));
    }
}
