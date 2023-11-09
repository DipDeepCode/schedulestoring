package ru.ddc.schedulestoring.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ddc.schedulestoring.utils.EntityFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PhysicianTest {
    private final EntityFactory entityFactory = new EntityFactory();

    @Test
    @DisplayName("Создание врача с допустимыми аргументами")
    public void givenValidParameters_whenConstructEmployee_thenSuccessful() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        Specialization specialization = entityFactory.createSpecializationWithRandomValues();
        Set<Specialization> specializationSet = new HashSet<>(Collections.singleton(specialization));
        Physician physician = assertDoesNotThrow(() -> new Physician(personalData, specializationSet));
        assertNull(physician.getVacancy());
        assertNotNull(physician.getAddedAt());
        assertFalse(physician.isDeleted());
        assertNull(physician.getDeletedAt());
    }

    @Test
    @DisplayName("Создание врача с PersonalData = null")
    public void givenNullPersonalDate_whenConstructPhysician_thenThrowException() {
        Specialization specialization = entityFactory.createSpecializationWithRandomValues();
        Set<Specialization> specializationSet = new HashSet<>(Collections.singleton(specialization));
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Physician(null, specializationSet));
        assertEquals(Employee.NULL_PERSONAL_DATA_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Создание врача с null или пустой специализацией")
    public void givenNullOrEmptySpecializationSet_whenConstructPhysician_thenThrowException() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Physician(personalData, null));
        assertEquals(Physician.NULL_OR_EMPTY_SPECIALIZATIONS_MESSAGE, exception.getMessage());
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Physician(personalData, Collections.emptySet()));
        assertEquals(Physician.NULL_OR_EMPTY_SPECIALIZATIONS_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Добавление допустимой специализации")
    public void givenValidSpecialization_whenAddSpecialization_thenSuccessful() {
        Physician physician = entityFactory.createPhysicianWithRandomValues();
        Specialization specialization = entityFactory.createSpecializationWithRandomValues();
        assertDoesNotThrow(() -> physician.addSpecialization(specialization));
        assertTrue(physician.getSpecializations().contains(specialization));
    }

    @Test
    @DisplayName("Добавление specialization = null")
    public void givenNullSpecialization_whenAddSpecialization_thenThrowException() {
        Physician physician = entityFactory.createPhysicianWithRandomValues();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> physician.addSpecialization(null));
        assertEquals(Physician.NULL_SPECIALIZATION_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Добавление уже добавленной специализации")
    public void givenAlreadyAddedSpecialization_whenAddSpecialization_thenThrowException() {
        Physician physician = entityFactory.createPhysicianWithRandomValues();
        Specialization specialization = physician.getSpecializations().stream().findAny().orElseThrow();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> physician.addSpecialization(specialization));
        assertEquals(Physician.SPECIALIZATIONS_ALREADY_ADDED_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Добавление специализации к удаленному врачу")
    public void givenDeletedPhysician_whenAddSpecialization_thenThrowException() {
        Physician physician = entityFactory.createDeletedPhysicianWithRandomValues();
        Specialization specialization = entityFactory.createSpecializationWithRandomValues();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> physician.addSpecialization(specialization));
        assertEquals(Physician.DELETED_PHYSICIAN_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Удаление существующей")
    public void givenPhysician_whenDeleteExistingSpecialization_thenSuccessful() {
        Physician physician = entityFactory.createPhysicianWithRandomValues();
        Specialization specialization = physician.getSpecializations().stream().findAny().orElseThrow();
        assertDoesNotThrow(() -> physician.removeSpecialization(specialization));
    }

    @Test
    @DisplayName("Удаление specialization = null")
    public void givenPhysician_whenDeleteNullSpecialization_thenThrowException() {
        Physician physician = entityFactory.createPhysicianWithRandomValues();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> physician.removeSpecialization(null));
        assertEquals(Physician.NULL_SPECIALIZATION_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Удаление специализации у удаленного врача")
    public void givenDeletedPhysician_whenDeleteSpecialization_thenThrowException() {
        Physician physician = entityFactory.createDeletedPhysicianWithRandomValues();
        Specialization specialization = physician.getSpecializations().stream().findAny().orElseThrow();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> physician.removeSpecialization(specialization)
        );
        assertEquals(Physician.DELETED_PHYSICIAN_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Удаление отсутствующей специализации")
    public void givenPhysician_whenDeleteMissingSpecialization_thenThrowException() {
        Physician physician = entityFactory.createPhysicianWithRandomValues();
        Specialization specialization = entityFactory.createSpecializationWithRandomValues();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> physician.removeSpecialization(specialization));
        assertEquals(Physician.MISSING_SPECIALIZATION_MESSAGE, exception.getMessage());
    }

//    @Test
//    @DisplayName("Добавление допустимого слота")
}
