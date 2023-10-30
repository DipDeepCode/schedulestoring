package ru.ddc.schedulestoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDate;

@Embeddable
public class PersonalData {

    @Column(name = "firstname", nullable = false, length = 64)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 64)
    private String lastname;

    @Column(name = "patronymic", length = 64)
    private String patronymic;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    public PersonalData() {
    }

    public PersonalData(String firstname, String lastname, LocalDate birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }

    public PersonalData(String firstname, String lastname, String patronymic, LocalDate birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "PersonalData{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}

/*
*               const1  const2  nullable    unique  insertable  updatable
*   firstname   yes     yes     no          no      yes         yes
*   lastname    yes     yes     no          no      yes         yes
*   patronymic  no      yes     yes         no      yes         yes
*   birthdate   yes     yes     no          no      yes         yes
*
* */
