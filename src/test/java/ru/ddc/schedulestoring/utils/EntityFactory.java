package ru.ddc.schedulestoring.utils;

import ru.ddc.schedulestoring.entity.Employee;
import ru.ddc.schedulestoring.entity.PersonalData;
import ru.ddc.schedulestoring.entity.Vacancy;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class EntityFactory {

    public PersonalData createPersonalDataWithRandomValues() {
        PersonalData personalData = new PersonalData();
        ObjectFieldsFiller.fillFieldsWithRandomValues(personalData,
                "firstname", "lastname", "patronymic", "birthdate");
        return personalData;
    }

    public Employee createEmployeeWithRandomValues() {
        return new Employee(createPersonalDataWithRandomValues());
    }

    public Vacancy createVacancyWithRandomValues() {
        Vacancy vacancy = new Vacancy();
        ObjectFieldsFiller.fillFieldsWithRandomValues(vacancy, "salary", "position");
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
}
