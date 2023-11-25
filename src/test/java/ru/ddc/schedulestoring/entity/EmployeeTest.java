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

class EmployeeTest {
    private static Validator validator;

    private final EntityFactory entityFactory = new EntityFactory();

    @BeforeAll
    public static void setup() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        validatorFactory.close();
    }

    @Test
    public void givenEmployeeClass_whenConstructNewEmployee_thenSomeFieldsHaveSpecificValue() {
        Employee employee = new Employee();
        assertNull(employee.getVacancy());
        assertTrue(employee.getAddedAt().isEqual(LocalDate.now()));
        assertFalse(employee.isDeleted());
        assertNull(employee.getDeletedAt());
    }

    @Test
    public void givenEmployeeWithNullPersonalData_whenValidate_thenOneConstraintViolationWithSpecificMessage() {
        Employee employee = entityFactory.createEmployeeWithRandomValues();
        employee.setPersonalData(null);
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertEquals(1, violations.size());
        String message = violations.stream().findAny().orElseThrow().getMessage();
        assertEquals(Employee.NULL_PERSONAL_DATA_MESSAGE, message);
    }
}