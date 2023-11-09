package ru.ddc.schedulestoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Embeddable
public class PersonalData {
    public static final String NULL_OR_BLANK_FIRSTNAME_MESSAGE = "Не указано имя";
    public static final String NULL_OR_BLANK_LASTNAME_MESSAGE = "Не указана фамилия";
    public static final String NULL_OR_BLANK_PATRONYMIC_MESSAGE = "Не указано отчество";
    public static final String NULL_BIRTHDATE_MESSAGE = "Не указана дата рождения";
    public static final String WRONG_BIRTHDATE_MESSAGE = "Дата рождения не может быть в будущем";

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
        if (firstname == null || firstname.isBlank() || firstname.length() < 2) {
            throw new IllegalArgumentException(NULL_OR_BLANK_FIRSTNAME_MESSAGE);
        } else if (lastname == null || lastname.isBlank() || lastname.length() < 2) {
            throw new IllegalArgumentException(NULL_OR_BLANK_LASTNAME_MESSAGE);
        } else if (birthdate == null) {
            throw new IllegalArgumentException(NULL_BIRTHDATE_MESSAGE);
        } else if (birthdate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(WRONG_BIRTHDATE_MESSAGE);
        } else {
            this.firstname = firstname;
            this.lastname = lastname;
            this.birthdate = birthdate;
        }
    }

    public PersonalData(String firstname, String lastname, String patronymic, LocalDate birthdate) {
        this(firstname, lastname, birthdate);
        if (patronymic == null || patronymic.isBlank() || patronymic.length() < 2) {
            throw new IllegalArgumentException(NULL_OR_BLANK_PATRONYMIC_MESSAGE);
        } else {
            this.patronymic = patronymic;
        }
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
 *              const1  const2  null            insert  update                  init    change
 *  field       arg     arg     able    unique  able    able    setter  getter  method  method
 *  ------------------------------------------------------------------------------------------
 *  --- THIS ---------------------------------------------------------------------------------
 *  ------------------------------------------------------------------------------------------
 *  firstname   yes     yes     no      no      yes     yes     yes     yes     const   setter
 *  lastname    yes     yes     no      no      yes     yes     yes     yes     const   setter
 *  patronymic  no      yes     yes     no      yes     yes     yes     yes     const   setter
 *  birthdate   yes     yes     no      no      yes     yes     yes     yes     const   setter
 *  ------------------------------------------------------------------------------------------
 *
 **/
