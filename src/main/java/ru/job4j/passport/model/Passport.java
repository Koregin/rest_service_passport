package ru.job4j.passport.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "series")
    private String series;

    @Column(name = "number")
    private String number;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Column(name = "fio")
    private String fio;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    public Passport() {
    }

    public Passport(String series, String number, LocalDate dateOfIssue, String fio, LocalDate dateOfBirth) {
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

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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
}
