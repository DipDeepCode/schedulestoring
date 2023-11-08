package ru.ddc.schedulestoring.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecializationTest {

    @Test
    @DisplayName("Создание специализации с допустимыми аргументами")
    public void givenValidArguments_whenConstructSpecialization_thenSuccessful() {
        assertDoesNotThrow(
                () -> new Specialization(
                        "brief description",
                        "full description",
                        "code")
        );
    }

    @Test
    @DisplayName("Создание специализации с недопустимыми аргументами")
    public void givenIllegalArguments_whenConstructSpecialization_thenThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization(null, "full description", "code"),
                Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE);
        assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("", "full description", "code"),
                Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE);
        assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("brief description", null, "code"),
                Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE);
        assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("brief description", "", "code"),
                Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE);
        assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("brief description", "full description", null),
                Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE);
        assertThrows(
                IllegalArgumentException.class,
                () -> new Specialization("brief description", "full description", ""),
                Specialization.NULL_OR_BLANK_ARGUMENT_MESSAGE);
    }
}
