package ru.job4j.passport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.passport.model.Passport;

import java.util.List;

public interface PassportRepository extends JpaRepository<Passport, Integer> {

    List<Passport> findAllBySeries(int series);

    @Query(value = "select * "
            + "from passport "
            + "where case"
            + "          when DATE_PART('year', AGE(current_date, date_of_birth)) >= 14 AND"
            + "               DATE_PART('year', AGE(current_date, date_of_birth)) < 20"
            + "              then"
            + "              case"
            + "                  when date_of_issue > date_of_birth + interval '14 years' AND"
            + "                       date_of_issue < date_of_birth + interval '20 years' then false"
            + "                  else true"
            + "                  end"
            + "          when DATE_PART('year', AGE(current_date, date_of_birth)) >= 20 AND"
            + "               DATE_PART('year', AGE(current_date, date_of_birth)) < 45"
            + "              then"
            + "              case"
            + "                  when date_of_issue > date_of_birth + interval '20 years' AND"
            + "                       date_of_issue < date_of_birth + interval '45 years' then false"
            + "                  else true"
            + "                  end"
            + "          when DATE_PART('year', AGE(current_date, date_of_birth)) > 45"
            + "              then"
            + "              case"
            + "                  when date_of_issue > date_of_birth + interval '45 years' then false"
            + "                  else true"
            + "                  end"
            + "          end",
    nativeQuery = true)
    List<Passport> findUnavailable();

    @Query(value = "select *"
            + "from passport "
            + "where case"
            + "          when DATE_PART('year', AGE(current_date, date_of_birth)) >= 14 AND"
            + "               DATE_PART('year', AGE(current_date, date_of_birth)) < 20"
            + "              then"
            + "              case"
            + "                  when current_date > date_of_birth + interval '14' year AND"
            + "                       current_date < date_of_birth + interval '20' year - interval '3' month then false"
            + "                  else true"
            + "                  end"
            + "          when DATE_PART('year', AGE(current_date, date_of_birth)) >= 20 AND"
            + "               DATE_PART('year', AGE(current_date, date_of_birth)) < 45"
            + "              then"
            + "              case"
            + "                  when current_date > date_of_birth + interval '20' year AND"
            + "                       current_date < date_of_birth + interval '45' year - interval '3' month then false"
            + "                  else true"
            + "                  end"
            + "          when DATE_PART('year', AGE(current_date, date_of_birth)) > 45"
            + "              then"
            + "              case"
            + "                  when current_date > date_of_birth + interval '45' year then false"
            + "                  else true"
            + "                  end"
            + "          end",
    nativeQuery = true)
    List<Passport> findReplaceable();
}
