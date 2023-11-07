package ru.ddc.schedulestoring.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonalDataTest {

    @Test
    @DisplayName("Создание персональных данных с допустимыми аргументами")
    public void givenValidParameters_whenConstructPersonalData_thenSuccessful() {
        assertDoesNotThrow(() -> new PersonalData(
                "firstname",
                "lastname",
                LocalDate.now().minusYears(18))
        );
        assertDoesNotThrow(() -> new PersonalData(
                "firstname",
                "lastname",
                "patronymic",
                LocalDate.now().minusYears(18))
        );
    }

    @Test
    @DisplayName("Создание персональных данных с пустым или null именем")
    public void givenNullOrBlankFirstname_whenConstructPersonalData_thenThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PersonalData(null, "lastname", LocalDate.now().minusYears(18))
        );
        assertEquals(PersonalData.NULL_OR_BLANK_FIRSTNAME_MESSAGE, exception.getMessage());
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PersonalData("", "lastname", LocalDate.now().minusYears(18))

        );
        assertEquals(PersonalData.NULL_OR_BLANK_FIRSTNAME_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Создание персональных данных с пустой или null фамилией")
    public void givenNullOrBlankLastname_whenConstructPersonalData_thenThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PersonalData("firstname", null, LocalDate.now().minusYears(18))
        );
        assertEquals(PersonalData.NULL_OR_BLANK_LASTNAME_MESSAGE, exception.getMessage());
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PersonalData("firstname", "", LocalDate.now().minusYears(18))

        );
        assertEquals(PersonalData.NULL_OR_BLANK_LASTNAME_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Создание персональных данных с пустой датой рождения или датой рождения в будущем")
    public void givenNullOrWrongBirthdate_whenConstructPersonalData_thenThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PersonalData("firstname", "lastname", null)
        );
        assertEquals(PersonalData.NULL_BIRTHDATE_MESSAGE, exception.getMessage());
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PersonalData("firstname", "lastname", LocalDate.now().plusDays(1L))

        );
        assertEquals(PersonalData.WRONG_BIRTHDATE_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Создание персональных данных с пустым или null отчеством")
    public void givenNullOrBlankBirthdate_whenConstructPersonalData_thenThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PersonalData(
                        "firstname",
                        "lastname",
                        null,
                        LocalDate.now().minusYears(18)
                )
        );
        assertEquals(PersonalData.NULL_OR_BLANK_PATRONYMIC_MESSAGE, exception.getMessage());
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PersonalData(
                        "firstname",
                        "lastname",
                        "",
                        LocalDate.now().minusYears(18)
                )

        );
        assertEquals(PersonalData.NULL_OR_BLANK_PATRONYMIC_MESSAGE, exception.getMessage());
    }
}