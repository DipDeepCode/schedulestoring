package ru.ddc.schedulestoring.utils;

import ru.ddc.schedulestoring.entity.*;

import static ru.ddc.schedulestoring.utils.ObjectFieldsFiller.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class EntityFactory {

    public PersonalData createPersonalDataWithRandomValues() {
        PersonalData personalData = new PersonalData();
        fillFieldsWithRandomValues(personalData, "firstname", "lastname", "patronymic", "birthdate");
        return personalData;
    }

    public Employee createEmployeeWithRandomValues() {
        Employee employee = new Employee();
        PersonalData personalData = createPersonalDataWithRandomValues();
        employee.setPersonalData(personalData);
        return employee;
    }

    public Vacancy createVacancyWithRandomValues() {
        Vacancy vacancy = new Vacancy();
        fillFieldsWithRandomValues(vacancy, "salary", "position");
        return vacancy;
    }

    public Vacancy createVacancyWithRandomValuesAndEmployee() {
        Vacancy vacancy = createVacancyWithRandomValues();
        try {
            Field employeeField = vacancy.getClass().getDeclaredField("employee");
            employeeField.setAccessible(true);
            employeeField.set(vacancy, createEmployeeWithRandomValues());
            return vacancy;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Vacancy createDeletedVacancy() {
        Vacancy vacancy = createVacancyWithRandomValues();
        try {
            Field isDeleted = vacancy.getClass().getDeclaredField("isDeleted");
            isDeleted.setAccessible(true);
            isDeleted.setBoolean(vacancy, true);
            Field deletedAt = vacancy.getClass().getDeclaredField("deletedAt");
            deletedAt.setAccessible(true);
            deletedAt.set(vacancy, LocalDate.now());
            return vacancy;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Specialization createSpecializationWithRandomValues() {
        Specialization specialization = new Specialization();
        fillFieldsWithRandomValues(specialization, "briefDescription", "fullDescription", "code");
        return specialization;
    }

    public Physician createPhysicianWithRandomValues() {
        PersonalData personalData = createPersonalDataWithRandomValues();
        Specialization specialization = createSpecializationWithRandomValues();
        Set<Specialization> specializationSet = new HashSet<>();
        specializationSet.add(specialization);
        return new Physician(personalData, specializationSet);
    }

    public Physician createDeletedPhysicianWithRandomValues() {
        Physician physician = createPhysicianWithRandomValues();
        try {

            Field isDeleted = physician.getClass().getSuperclass().getDeclaredField("isDeleted");
            isDeleted.setAccessible(true);
            isDeleted.setBoolean(physician, true);
            Field deletedAt = physician.getClass().getSuperclass().getDeclaredField("deletedAt");
            deletedAt.setAccessible(true);
            deletedAt.set(physician, LocalDate.now());
            return physician;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
