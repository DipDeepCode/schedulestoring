package ru.ddc.schedulestoring.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ddc.schedulestoring.exception.AssignEmployeeException;
import ru.ddc.schedulestoring.exception.RemoveEmployeeException;
import ru.ddc.schedulestoring.utils.EntityFactory;

import static org.junit.jupiter.api.Assertions.*;

class VacancyTest {

    private final EntityFactory entityFactory = new EntityFactory();

    @Test
    @DisplayName("Создание вакансии с допустимыми аргументами")
    public void givenPositiveSalaryAndNotBlankPosition_whenConstructVacancy_thenSuccessful() {
        Vacancy vacancy = new Vacancy(100L, "position");
        assertNull(vacancy.getEmployee());
        assertNotNull(vacancy.getAddedAt());
        assertFalse(vacancy.isDeleted());
        assertNull(vacancy.getDeletedAt());
    }

    @Test
    @DisplayName("Создание вакансии с отрицательной или нулевой зарплатой")
    public void givenNegativeOrZeroSalary_whenConstructVacancy_thenThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Vacancy(0L, "position"),
                Vacancy.NEGATIVE_OR_ZERO_SALARY_MESSAGE);
        assertThrows(
                IllegalArgumentException.class,
                () -> new Vacancy(-100L, "position"),
                Vacancy.NEGATIVE_OR_ZERO_SALARY_MESSAGE);
    }


    @Test
    @DisplayName("Создание вакансии с пустым или null полем должность")
    public void givenNullOrBlankPosition_whenConstructVacancy_thenTrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Vacancy(100L, null),
                Vacancy.NULL_OR_BLANK_POSITION_MESSAGE);
        assertThrows(
                IllegalArgumentException.class,
                () -> new Vacancy(100L, ""),
                Vacancy.NULL_OR_BLANK_POSITION_MESSAGE);
    }

    @Test
    @DisplayName("Закрепить сотрудника за свободной вакансией")
    public void givenVacancyWithoutEmployee_whenAssignEmployee_thenEmployeeNotNull() {
        Vacancy vacancy = entityFactory.createVacancyWithRandomValues();
        Employee employee = entityFactory.createEmployeeWithRandomValues();
        vacancy.assignEmployee(employee);
        assertEquals(employee, vacancy.getEmployee());
        assertEquals(vacancy, employee.getVacancy());
    }

    @Test
    @DisplayName("Закрепить сотрудника за занятой вакансией")
    public void givenVacancyWithEmployee_whenAssignEmployee_thenThrowException() {
        Vacancy vacancy = entityFactory.createVacancyWithRandomValuesAndEmployee();
        Employee employee = entityFactory.createEmployeeWithRandomValues();
        assertThrows(
                AssignEmployeeException.class,
                () -> vacancy.assignEmployee(employee),
                Vacancy.OCCUPIED_VACANCY_MESSAGE);
    }

    @Test
    @DisplayName("Закрепить сотрудника за удаленной вакансией")
    public void givenDeletedVacancy_whenAssignEmployee_thenThrowException() {
        Vacancy vacancy = entityFactory.createDeletedVacancy();
        Employee employee = entityFactory.createEmployeeWithRandomValues();
        assertThrows(
                AssignEmployeeException.class,
                () -> vacancy.assignEmployee(employee),
                Vacancy.DELETED_VACANCY_MESSAGE);
    }

    @Test
    @DisplayName("Закрепление employee = null")
    public void givenNullEmployee_whenAssignEmployee_thenThrowException() {
        Vacancy vacancy = entityFactory.createVacancyWithRandomValues();
        assertThrows(
                IllegalArgumentException.class,
                () -> vacancy.assignEmployee(null),
                Vacancy.NULL_EMPLOYEE_ARGUMENT_MESSAGE);
    }

    @Test
    @DisplayName("Открепить сотрудника от вакансии с сотрудником")
    public void givenVacancyWithEmployee_whenRemoveEmployee_thenEmployeeEqualsNull() {
        Vacancy vacancy = entityFactory.createVacancyWithRandomValuesAndEmployee();
        Employee employee = vacancy.getEmployee();
        vacancy.removeEmployee();
        assertNull(vacancy.getEmployee());
        assertNull(employee.getVacancy());
    }

    @Test
    @DisplayName("Открепить сотрудника от вакансии без сотрудника")
    public void givenVacancyWithoutEmployee_whenRemoveEmployee_thenThrowException() {
        Vacancy vacancy = entityFactory.createVacancyWithRandomValues();
        assertThrows(
                RemoveEmployeeException.class,
                vacancy::removeEmployee,
                Vacancy.VACANCY_WITHOUT_EMPLOYEE_MESSAGE);
    }
}