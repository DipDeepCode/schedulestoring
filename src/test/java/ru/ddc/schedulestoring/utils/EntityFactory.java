package ru.ddc.schedulestoring.utils;

import ru.ddc.schedulestoring.entity.Employee;
import ru.ddc.schedulestoring.entity.PersonalData;
import ru.ddc.schedulestoring.entity.Vacancy;

import java.lang.reflect.Field;

public class EntityFactory {

    public Vacancy createBlankVacancy() {
        return new Vacancy();
    }

    public Employee createBlankEmployee() {
        return new Employee();
    }

    public PersonalData createBlancPersonalData() {
        return new PersonalData();
    }

    public PersonalData createPersonalDataWithRandomValues() {
        PersonalData personalData = createBlancPersonalData();
        ObjectFieldsFillerA.fillFieldsWithRandomValues(personalData,
                "firstname", "lastname", "patronymic", "birthdate");
        return personalData;
    }

    public Vacancy createVacancyWithRandomValues() {
        Vacancy vacancy = createBlankVacancy();
        ObjectFieldsFillerA.fillFieldsWithRandomValues(vacancy, "salary", "position");
        System.out.println(vacancy);
        return vacancy;
    }

    public Vacancy createVacancyWithSalaryAndPosition(Long salary, String position) {
        return new Vacancy(salary, position);
    }

    public Vacancy createVacancyWithBlancEmployee() {
        Vacancy vacancy = createBlankVacancy();
        try {
            Field employeeField = vacancy.getClass().getDeclaredField("employee");
            employeeField.setAccessible(true);
            employeeField.set(vacancy, createBlankEmployee());
            return vacancy;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Vacancy createDeletedVacancy() {
        Vacancy vacancy = createBlankVacancy();
        try {
            Field isDeleted = vacancy.getClass().getDeclaredField("isDeleted");
            isDeleted.setAccessible(true);
            isDeleted.setBoolean(vacancy, true);
            return vacancy;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
