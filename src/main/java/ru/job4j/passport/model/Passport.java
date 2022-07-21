package ru.job4j.passport.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Passport", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueSeriesNumber", columnNames = {"series", "number"})})
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "series")
    private int series;

    @Column(name = "number")
    private int number;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Column(name = "fio")
    private String fio;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    public Passport() {
    }

    public Passport(int series, int number, LocalDate dateOfIssue, String fio, LocalDate dateOfBirth) {
        this.series = series;
        this.number = number;
        this.dateOfIssue = dateOfIssue;
        this.fio = fio;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Passport{"
                + "id=" + id
                + ", series='" + series + '\''
                + ", number='" + number + '\''
                + ", dateOfIssue=" + dateOfIssue
                + ", fio='" + fio + '\''
                + ", dateOfBirth=" + dateOfBirth
                + '}';
    }
}
