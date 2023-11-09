package ru.ddc.schedulestoring.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ddc.schedulestoring.utils.EntityFactory;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PhysicianTest {
    private final EntityFactory entityFactory = new EntityFactory();

    @Test
    @DisplayName("Создание врача с допустимыми аргументами")
    public void givenValidParameters_whenConstructEmployee_thenSuccessful() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        Specialization specialization = entityFactory.createSpecializationWithRandomValues();
//        System.out.println(specialization);
        Physician physician = new Physician(personalData, new HashSet<>(Collections.singleton(specialization)));
//        Employee employee = new Employee(personalData);

//        assertDoesNotThrow();


        assertNotNull(physician.getPersonalData());
        assertNull(physician.getVacancy());
        assertNotNull(physician.getAddedAt());
        assertFalse(physician.isDeleted());
        assertNull(physician.getDeletedAt());
    }

}
