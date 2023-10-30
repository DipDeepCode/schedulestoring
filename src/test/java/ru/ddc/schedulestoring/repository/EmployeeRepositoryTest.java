package ru.ddc.schedulestoring.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.ddc.schedulestoring.entity.*;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void addPhysicianEndOperator() {
//        Specialization specialization = new Specialization("Терапевт");
//        PersonalData personalData = new PersonalData("firstname1", "lastname1", "patronymic1", LocalDate.parse("1990-01-04"));
//        Physician physician = new Physician(personalData, 100L, specialization);
//        employeeRepository.saveAndFlush(physician);
//
//        PersonalData personalData1 = new PersonalData("firstname2", "lastname2", "patronymic2", LocalDate.parse("2000-01-04"));
//        Operator operator = new Operator(personalData1, 50L, "note");
//        employeeRepository.saveAndFlush(operator);
//
//        entityManager.detach(physician);
//        entityManager.detach(operator);
//
//        List<Employee> all = employeeRepository.findAll();
//        all.forEach(System.out::println);
    }
}
