package ru.job4j.passport.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.passport.model.Passport;

import java.util.List;

public interface PassportRepository extends CrudRepository<Passport, Integer> {

    List<Passport> findAllBySeries(String series);
}
