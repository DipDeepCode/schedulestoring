package ru.ddc.schedulestoring.repository;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.ddc.schedulestoring.entity.Employee;
import ru.ddc.schedulestoring.utils.EntityFactory;
import ru.ddc.schedulestoring.entity.PersonalData;
import ru.ddc.schedulestoring.entity.Vacancy;
import ru.ddc.schedulestoring.exception.AssignEmployeeException;
import ru.ddc.schedulestoring.exception.DeleteEntityException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@SpringBootTest
class VacancyRepositoryTest {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager entityManager;

    EntityFactory entityFactory = new EntityFactory();

    @Test
    @DisplayName("Получение всех вакансий без фильтра")
    @Transactional
    public void given_whenFindAllVacanciesWithoutFiler_thenFindAllWithDeletedVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAll();
        assertEquals(7, vacancies.size());
    }

    @Test
    @DisplayName("Получение всех вакансий с фильтром")
    public void given_whenFindAllVacanciesWithFilter_thenFindAllNonDeletedVacancies() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedVacancyFilter").setParameter("is_deleted", false);
        List<Vacancy> vacancies = vacancyRepository.findAll();
        assertEquals(4, vacancies.size());
        session.disableFilter("deletedVacancyFilter");
    }

    @Test
    @DisplayName("Добавление вакансии")
    public void givenNewVacancy_whenSaveToRepository_thenSuccessful() {
        Vacancy vacancy = entityFactory.createVacancyWithRandomValues();
        vacancyRepository.saveAndFlush(vacancy);
        Long id = vacancy.getId();
        entityManager.detach(vacancy);
        Vacancy vacancyFromDB = vacancyRepository.findById(id).orElseThrow();
        assertTrue(new ReflectionEquals(vacancyFromDB).matches(vacancy));
    }

    @Test
    @DisplayName("Изменение вакансии")
    public void givenVacancyId_whenUpdateSalaryAndPosition_thenSuccessful() {
        Long id = 1L;
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        vacancy.setSalary(10L);
        vacancy.setPosition("Врач");
        vacancyRepository.saveAndFlush(vacancy);
        entityManager.detach(vacancy);
        Vacancy vacancyFromDB = vacancyRepository.findById(id).orElseThrow();
        assertTrue(new ReflectionEquals(vacancy).matches(vacancyFromDB));
    }

    @Test
    @DisplayName("Назначить существующего сотрудника на свободную вакансию")
    public void givenExistingEmployeeIdAndVacancyId_whenAssignEmployeeToOpenVacancy_thenSuccessful() {
        Long vacancyId = 1L;
        Long employeeId = 1L;
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        vacancy.assignEmployee(employee);
        vacancyRepository.saveAndFlush(vacancy);
        entityManager.detach(vacancy);
        entityManager.detach(employee);
        Vacancy vacancyFromDB = vacancyRepository.findById(vacancyId).orElseThrow();
        assertTrue(new ReflectionEquals(vacancy, "employee").matches(vacancyFromDB));
        assertTrue(new ReflectionEquals(vacancy.getEmployee()).matches(employee));
    }

    @Test
    @DisplayName("Назначить созданного сотрудника на свободную вакансию")
    public void givenNewEmployeeIdAndVacancyId_whenAssignEmployeeToOpenVacancy_thenSuccessful() {
        Long vacancyId = 3L;
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();
        PersonalData personalData = new PersonalData("firstname3", "lastname3", "patronymic3",
                LocalDate.parse("2022-11-11"));
        Employee employee = new Employee(personalData);
        vacancy.assignEmployee(employee);
        vacancyRepository.saveAndFlush(vacancy);
        entityManager.detach(vacancy);
        entityManager.detach(employee);
        Vacancy vacancyFromDB = vacancyRepository.findById(vacancyId).orElseThrow();
        assertTrue(new ReflectionEquals(vacancy, "employee").matches(vacancyFromDB));
        assertTrue(new ReflectionEquals(vacancy.getEmployee()).matches(employee));
    }

    @Test
    @DisplayName("Назначить существующего сотрудника на занятую вакансию")
    public void givenEmployeeIdAndVacancyId_whenAssignToOccupiedVacancy_thenThrowException() {
        Long vacancyId = 2L;
        Long employeeId = 3L;
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        assertThrows(AssignEmployeeException.class, () -> vacancy.assignEmployee(employee));
    }

    @Test
    @DisplayName("Назначить созданного сотрудника на занятую вакансию")
    public void givenNewEmployeeIdAndVacancyId_whenAssignEmployeeToOccupiedVacancy_thenThrowException() {
        Long vacancyId = 2L;
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();
        PersonalData personalData = new PersonalData("firstname4", "lastname4", "patronymic4",
                LocalDate.parse("2022-11-11"));
        Employee employee = new Employee(personalData);
        assertThrows(AssignEmployeeException.class, () -> vacancy.assignEmployee(employee));
    }

    @Test
    @DisplayName("Снять сотрудника с вакансии")
    public void givenVacancyId_whenRemoveEmployee_thenSuccessful() {
        Long vacancyId = 2L;
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();
        vacancy.removeEmployee();
        vacancyRepository.saveAndFlush(vacancy);
        entityManager.detach(vacancy);
        Vacancy vacancyFromDB = vacancyRepository.findById(vacancyId).orElseThrow();
        assertTrue(new ReflectionEquals(vacancy, "employee").matches(vacancyFromDB));
        assertNull(vacancy.getEmployee());
    }

    @Test
    @DisplayName("Удалить незанятую вакансию")
    public void givenVacancyIdWithoutEmployee_whenDeleteVacancy_thenIsDeletedIsTrue() {
        Long vacancyId = 4L;
        vacancyRepository.deleteById(vacancyId);
        vacancyRepository.flush();
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow();
        System.out.println(vacancy);
        assertTrue(vacancy.isDeleted());
        assertNotNull(vacancy.getDeletedAt());
    }

    @Test
    @DisplayName("Удалить занятую вакансию")
    public void givenVacancyIdWithEmployee_whenRemoveVacancy_thenThrowException() {
        Long vacancyId = 2L;
        assertThrows(DeleteEntityException.class, () -> vacancyRepository.deleteById(vacancyId));
    }

//    @Test
//    @DisplayName("Закрепить сотрудника за двумя вакансиями")
//    public void givenEmployee_whenAssignEmployeeToSecondVacancy_thenThrowException() {
//        Vacancy vacancy1 = entityFactory.createBlankVacancy();
//        Vacancy vacancy2 = entityFactory.createBlankVacancy();
//        Employee employee = entityFactory.createBlankEmployee();
//        vacancy1.assignEmployee(employee);
//        vacancyRepository.saveAndFlush(vacancy1);
//
//
//        vacancy2.assignEmployee(employee);
//    }
}