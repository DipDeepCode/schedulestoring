package ru.ddc.schedulestoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Embeddable
public class PersonalData {

    @Getter
    @Setter
    @Column(name = "firstname", nullable = false, length = 64)
    private String firstname;

    @Getter
    @Setter
    @Column(name = "lastname", nullable = false, length = 64)
    private String lastname;

    @Getter
    @Setter
    @Column(name = "patronymic", length = 64)
    private String patronymic;

    @Getter
    @Setter
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

    // TODO удалить метод toString
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
*               const1  const2
*               arg     arg     nullable    unique  insertable  updatable   setter  getter  init_method change_method
*   -----------------------------------------------------------------------------------------------------------------
*   firstname   yes     yes     no          no      yes         yes         yes     yes     constructor setter
*   lastname    yes     yes     no          no      yes         yes         yes     yes     constructor setter
*   patronymic  no      yes     yes         no      yes         yes         yes     yes     constructor setter
*   birthdate   yes     yes     no          no      yes         yes         yes     yes     constructor setter
*
* */
