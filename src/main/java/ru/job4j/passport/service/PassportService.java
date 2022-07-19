package ru.job4j.passport.service;

import org.springframework.stereotype.Service;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassportService {
    private final PassportRepository repository;

    public PassportService(PassportRepository repository) {
        this.repository = repository;
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

    public List<Passport> findAll(String series) {
        List<Passport> passports = new ArrayList<>();
        if (series != null) {
            passports = repository.findAllBySeries(series);
        } else {
            repository.findAll().forEach(passports::add);
        }
        return passports;
    }

    public List<Passport> findUnavailable() {
        List<Passport> passports = new ArrayList<>();
        repository.findAll().forEach(passports::add);
        List<Passport> unavailablePassports = new ArrayList<>();
        for (Passport passport : passports) {
            if (unavailable(passport.getDateOfIssue(), passport.getDateOfBirth())) {
                unavailablePassports.add(passport);
            }
        }
        return unavailablePassports;
    }

    public List<Passport> findReplaceable() {
        List<Passport> passports = new ArrayList<>();
        repository.findAll().forEach(passports::add);
        List<Passport> replaceablePassports = new ArrayList<>();
        for (Passport passport : passports) {
            if (replaceable(passport.getDateOfIssue(), passport.getDateOfBirth())) {
                replaceablePassports.add(passport);
            }
        }
        return replaceablePassports;
    }

    /*
    Check for unavailable passport status
     */
    private boolean unavailable(LocalDate passportDate, LocalDate dateOfBirth) {
        boolean status = true;
        LocalDate currentDate = LocalDate.now();
        int currAge = Period.between(dateOfBirth, currentDate).getYears();
        if (currAge > 14 && currAge < 20) {
            if (passportDate.isAfter(dateOfBirth.plusYears(14)) && passportDate.isBefore(dateOfBirth.plusYears(20))) {
                status = false;
            }
        } else if (currAge >= 20 && currAge < 45) {
            if (passportDate.isAfter(dateOfBirth.plusYears(20)) && passportDate.isBefore(dateOfBirth.plusYears(45))) {
                status = false;
            }
        } else {
            if (passportDate.isAfter(dateOfBirth.plusYears(45))) {
                status = false;
            }
        }
        return status;
    }

    /*
    Check passport for replaceable
     */
    private boolean replaceable(LocalDate passportDate, LocalDate dateOfBirth) {
        boolean status = true;
        LocalDate currentDate = LocalDate.now();
        int currAge = Period.between(dateOfBirth, currentDate).getYears();
        if (unavailable(passportDate, dateOfBirth)) {
            return false;
        }
        if (currAge > 14 && currAge < 20) {
            if (currentDate.isAfter(dateOfBirth.plusYears(14))
                    && currentDate.isBefore(dateOfBirth.plusYears(20).minusMonths(3))) {
                status = false;
            }
        } else if (currAge >= 20 && currAge < 45) {
            if (currentDate.isAfter(dateOfBirth.plusYears(20))
                    && currentDate.isBefore(dateOfBirth.plusYears(45).minusMonths(3))) {
                status = false;
            }
        } else {
            if (currentDate.isAfter(dateOfBirth.plusYears(45))) {
                status = false;
            }
        }
        return status;
    }
}
