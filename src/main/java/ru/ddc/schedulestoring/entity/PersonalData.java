package ru.ddc.schedulestoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Embeddable
public class PersonalData {
    public static final String FIRSTNAME_IS_BLANK_MESSAGE = "Имя не указано";
    public static final String FIRSTNAME_LENGTH_NOT_VALID_MESSAGE = "Имя должно быть больше 2 и меньше 64 символов";
    public static final String LASTNAME_IS_BLANK_MESSAGE = "Не указана фамилия";
    public static final String LASTNAME_LENGTH_NOT_VALID_MESSAGE = "Фамилия должна быть больше 2 и меньше 64 символов";
    public static final String BIRTHDATE_IS_BLANK_MESSAGE = "Дата рождения не указана";
    public static final String BIRTHDATE_MUST_BE_IN_PAST_MESSAGE = "Дата рождения должна быть в прошлом";

    @NotBlank(message = FIRSTNAME_IS_BLANK_MESSAGE)
    @Size(min = 2, max = 64, message = FIRSTNAME_LENGTH_NOT_VALID_MESSAGE)
    @Column(name = "firstname", nullable = false, length = 64)
    private String firstname;

    @NotBlank(message = LASTNAME_IS_BLANK_MESSAGE)
    @Size(min = 2, max = 64, message = LASTNAME_LENGTH_NOT_VALID_MESSAGE)
    @Column(name = "lastname", nullable = false, length = 64)
    private String lastname;

    @Column(name = "patronymic", length = 64)
    private String patronymic;

    @NotNull(message = BIRTHDATE_IS_BLANK_MESSAGE)
    @Past(message = BIRTHDATE_MUST_BE_IN_PAST_MESSAGE)
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
        this(firstname, lastname, birthdate);
        this.patronymic = patronymic;
    }
}

/*
 *              null            insert  update                  init    change
 *  field       able    unique  able    able    setter  getter  method  method
 *  --------------------------------------------------------------------------
 *  --- THIS -----------------------------------------------------------------
 *  --------------------------------------------------------------------------
 *  firstname   no      no      yes     yes     yes     yes     no      setter
 *  lastname    no      no      yes     yes     yes     yes     no      setter
 *  patronymic  yes     no      yes     yes     yes     yes     no      setter
 *  birthdate   no      no      yes     yes     yes     yes     no      setter
 *  --------------------------------------------------------------------------
 *
 **/
