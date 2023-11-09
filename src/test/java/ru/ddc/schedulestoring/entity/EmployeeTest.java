package ru.ddc.schedulestoring.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ddc.schedulestoring.utils.EntityFactory;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    private final EntityFactory entityFactory = new EntityFactory();

    @Test
    @DisplayName("Создание сотрудника с допустимыми аргументами")
    public void givenValidParameters_whenConstructEmployee_thenSuccessful() {
        PersonalData personalData = entityFactory.createPersonalDataWithRandomValues();
        Employee employee = new Employee(personalData);
        assertNotNull(employee.getPersonalData());
        assertNull(employee.getVacancy());
        assertNotNull(employee.getAddedAt());
        assertFalse(employee.isDeleted());
        assertNull(employee.getDeletedAt());
    }

    @Test
    @DisplayName("Создание сотрудника с PersonalData = null")
    public void givenNullPersonalData_whenConstructEmployee_thenThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(null));
        assertEquals(Employee.NULL_PERSONAL_DATA_MESSAGE, exception.getMessage());
    }
}
