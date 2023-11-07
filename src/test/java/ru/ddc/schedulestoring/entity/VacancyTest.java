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
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Vacancy(0L, "position"));
        assertEquals(Vacancy.NEGATIVE_OR_ZERO_SALARY_MESSAGE, exception.getMessage());
        exception = assertThrows(IllegalArgumentException.class, () -> new Vacancy(-100L, "position"));
        assertEquals(Vacancy.NEGATIVE_OR_ZERO_SALARY_MESSAGE, exception.getMessage());
    }


    @Test
    @DisplayName("Создание вакансии с пустым или null полем должность")
    public void givenNullOrBlankPosition_whenConstructVacancy_thenTrowException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Vacancy(100L, null));
        assertEquals(Vacancy.NULL_OR_BLANK_POSITION_MESSAGE, exception.getMessage());
        exception = assertThrows(IllegalArgumentException.class, () -> new Vacancy(100L, ""));
        assertEquals(Vacancy.NULL_OR_BLANK_POSITION_MESSAGE, exception.getMessage());
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
        AssignEmployeeException exception =
                assertThrows(AssignEmployeeException.class, () -> vacancy.assignEmployee(employee));
        assertEquals(Vacancy.OCCUPIED_VACANCY_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Закрепить сотрудника за удаленной вакансией")
    public void givenDeletedVacancy_whenAssignEmployee_thenThrowException(){
        Vacancy vacancy = entityFactory.createDeletedVacancy();
        Employee employee = entityFactory.createEmployeeWithRandomValues();
        AssignEmployeeException exception =
                assertThrows(AssignEmployeeException.class, () -> vacancy.assignEmployee(employee));
        assertEquals(Vacancy.DELETED_VACANCY_MESSAGE, exception.getMessage());
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
        RemoveEmployeeException exception = assertThrows(RemoveEmployeeException.class, vacancy::removeEmployee);
        assertEquals(Vacancy.VACANCY_WITHOUT_EMPLOYEE_MESSAGE, exception.getMessage());
    }
}