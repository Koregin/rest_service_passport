package ru.job4j.passport.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PassportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassportService.class.getSimpleName());

    private final PassportRepository repository;

    private final KafkaTemplate<Long, List<Passport>> kafkaTemplate;

    private Long messageId = 1L;

    public PassportService(PassportRepository repository, KafkaTemplate<Long, List<Passport>> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Passport save(Passport passport) {
        return repository.save(passport);
    }

    public boolean update(int id, Passport passport) {
        Optional<Passport> foundPassport = repository.findById(id);
        boolean status = false;
        if (foundPassport.isPresent()) {
            passport.setId(foundPassport.get().getId());
            repository.save(passport);
            status = true;
        }
        return status;
    }

    public boolean delete(int id) {
        Optional<Passport> foundPassport = repository.findById(id);
        boolean status = false;
        if (foundPassport.isPresent()) {
            repository.deleteById(id);
            status = true;
        }
        return status;
    }

    public List<Passport> findAll(Optional<Integer> series) {
        return series.isPresent() ? repository.findAllBySeries(series.get()) : repository.findAll();
    }

    public List<Passport> findUnavailable() {
        return repository.findUnavailable();
    }

    public List<Passport> findReplaceable() {
        return repository.findReplaceable();
    }

    @Scheduled(fixedRate = 30000)
    public void sendMessagesWithUnavailablePassports() {
        LOGGER.info("Check passports");
        List<Passport> passports = findUnavailable();
        if (passports.size() > 0) {
            kafkaTemplate.send("passport", messageId, passports);
        }
        messageId++;
    }
}
