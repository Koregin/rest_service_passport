package ru.job4j.passport.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.job4j.passport.model.Passport;

import java.util.List;

@Service
public class MailService {

    @KafkaListener(topics = "passport")
    public void sendMail(ConsumerRecord<Long, List<Passport>> record) {
        System.out.println("Sending email....");
        System.out.println("List unavailable passports");
        System.out.println("Topic: " + record.topic());
        System.out.println("Key: " + record.key());
        System.out.println(record.value());
    }
}
