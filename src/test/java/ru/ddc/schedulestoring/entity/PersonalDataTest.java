package ru.ddc.schedulestoring.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.ddc.schedulestoring.utils.EntityFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonalDataTest {
    private static Validator validator;

    private final EntityFactory entityFactory = new EntityFactory();

    @BeforeAll
    public static void setup() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        validatorFactory.close();
    }

    @Test
    public void givenValidPersonalData_whenValidate_thenNoConstraintViolation() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        Set<ConstraintViolation<PersonalData>> violations = validator.validate(personalData);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void givenPersonalDataWithNullFirstname_whenValidate_thenOneConstraintViolationWithSpecificMessage() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        personalData.setFirstname(null);
        Set<ConstraintViolation<PersonalData>> violations = validator.validate(personalData);
        assertEquals(1, violations.size());
        String message = violations.stream().findAny().orElseThrow().getMessage();
        assertEquals(PersonalData.FIRSTNAME_IS_BLANK_MESSAGE, message);
    }

    @Test
    public void givenPersonalDataWithShortFirstname_whenValidate_thenOneConstraintViolationWithSpecificMessage() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        personalData.setFirstname("a");
        Set<ConstraintViolation<PersonalData>> violations = validator.validate(personalData);
        assertEquals(1, violations.size());
        String message = violations.stream().findAny().orElseThrow().getMessage();
        assertEquals(PersonalData.FIRSTNAME_LENGTH_NOT_VALID_MESSAGE, message);
    }

    @Test
    public void givenPersonalDataWithLongFirstname_whenValidate_thenOneConstraintViolationWithSpecificMessage() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        personalData.setFirstname("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij");
        Set<ConstraintViolation<PersonalData>> violations = validator.validate(personalData);
        assertEquals(1, violations.size());
        String message = violations.stream().findAny().orElseThrow().getMessage();
        assertEquals(PersonalData.FIRSTNAME_LENGTH_NOT_VALID_MESSAGE, message);
    }

    @Test
    public void givenPersonalDataWithNullLastname_whenValidate_thenOneConstraintViolationWithSpecificMessage() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        personalData.setLastname(null);
        Set<ConstraintViolation<PersonalData>> violations = validator.validate(personalData);
        assertEquals(1, violations.size());
        String message = violations.stream().findAny().orElseThrow().getMessage();
        assertEquals(PersonalData.LASTNAME_IS_BLANK_MESSAGE, message);
    }

    @Test
    public void givenPersonalDataWithShortLastname_whenValidate_thenOneConstraintViolationWithSpecificMessage() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        personalData.setLastname("a");
        Set<ConstraintViolation<PersonalData>> violations = validator.validate(personalData);
        assertEquals(1, violations.size());
        String message = violations.stream().findAny().orElseThrow().getMessage();
        assertEquals(PersonalData.LASTNAME_LENGTH_NOT_VALID_MESSAGE, message);
    }
    @Test
    public void givenPersonalDataWithLongLastname_whenValidate_thenOneConstraintViolationWithSpecificMessage() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        personalData.setLastname("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij");
        Set<ConstraintViolation<PersonalData>> violations = validator.validate(personalData);
        assertEquals(1, violations.size());
        String message = violations.stream().findAny().orElseThrow().getMessage();
        assertEquals(PersonalData.LASTNAME_LENGTH_NOT_VALID_MESSAGE, message);
    }

    @Test
    public void givenPersonalDateWithNullBirthdate_whenValidate_thenOneConstraintViolationWithSpecificMessage() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        personalData.setBirthdate(null);
        Set<ConstraintViolation<PersonalData>> violations = validator.validate(personalData);
        assertEquals(1, violations.size());
        String message = violations.stream().findAny().orElseThrow().getMessage();
        assertEquals(PersonalData.BIRTHDATE_IS_BLANK_MESSAGE, message);
    }

    @Test
    public void givenPersonalDataWithBirthdateInFuture_whenValidate_thenOneConstraintViolationWithSpecificMessage() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        personalData.setBirthdate(LocalDate.now());
        Set<ConstraintViolation<PersonalData>> violations = validator.validate(personalData);
        assertEquals(1, violations.size());
        String message = violations.stream().findAny().orElseThrow().getMessage();
        assertEquals(PersonalData.BIRTHDATE_MUST_BE_IN_PAST_MESSAGE, message);
    }
}
