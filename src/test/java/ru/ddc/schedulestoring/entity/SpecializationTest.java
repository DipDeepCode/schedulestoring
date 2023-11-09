package ru.ddc.schedulestoring.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecializationTest {

    @Test
    @DisplayName("Создание специализации с допустимыми аргументами")
    public void givenValidParameters_whenConstructSpecialization_thenSuccessful() {
        assertDoesNotThrow(
                () -> new Specialization("brief description", "full description", "code"));
    }

    @Test
    @DisplayName("Создание специализации с недопустимыми аргументами")
    public void givenIllegalArguments_whenConstructSpecialization_thenThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization(null, "full description", "code"));
        assertEquals(Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE, exception.getMessage());

        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("brief description", null, "code"));
        assertEquals(Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE, exception.getMessage());

        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("brief description", "full description", null));
        assertEquals(Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE, exception.getMessage());

        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("     ", "full description", "code"));
        assertEquals(Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE, exception.getMessage());

        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("brief description", "     ", "code"));
        assertEquals(Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE, exception.getMessage());

        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("brief description", "full description", "     "));
        assertEquals(Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE, exception.getMessage());

        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("abc", "full description", "code"));
        assertEquals(Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE, exception.getMessage());

        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("brief description", "abc", "code"));
        assertEquals(Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE, exception.getMessage());

        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("brief description", "full description", "a"));
        assertEquals(Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE, exception.getMessage());
    }
}
