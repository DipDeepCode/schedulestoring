package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import ru.ddc.schedulestoring.exception.AssignEmployeeException;
import ru.ddc.schedulestoring.exception.RemoveEmployeeException;

import java.time.LocalDate;

@Entity
@Table(name = "vacancy")
@SQLDelete(sql = "update vacancy set deleted_at=current_date, is_deleted=true where id=?")
public class Vacancy {
    public static final String NEGATIVE_OR_ZERO_SALARY_MESSAGE = "Зарплата не может быть меньше ноля";
    public static final String NULL_OR_BLANK_POSITION_MESSAGE = "Не указана должность";
    public static final String OCCUPIED_VACANCY_MESSAGE = "Вакансия занята";
    public static final String DELETED_VACANCY_MESSAGE = "Вакансия удалена";
    public static final String VACANCY_WITHOUT_EMPLOYEE_MESSAGE = "За вакансией не закреплен сотрудник";

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "salary", nullable = false)
    private Long salary;

    @Getter
    @Setter
    @Column(name = "position", nullable = false, length = 64)
    private String position;

    @Getter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    @Getter
    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDate addedAt;

    @Getter
    @Column(name = "is_deleted", nullable = false, updatable = false)
    private boolean isDeleted;

    @Getter
    @Column(name = "deleted_at", insertable = false, updatable = false)
    private LocalDate deletedAt;

    public Vacancy() {
    }

    public Vacancy(Long salary, String position) {
        if (salary <= 0) {
            throw new IllegalArgumentException(NEGATIVE_OR_ZERO_SALARY_MESSAGE);
        }
        this.salary = salary;
        if (position == null || position.isEmpty()) {
            throw new IllegalArgumentException(NULL_OR_BLANK_POSITION_MESSAGE);
        }
        this.position = position;
        this.employee = null;
        this.addedAt = LocalDate.now();
        this.isDeleted = false;
        this.deletedAt = null;
    }

    public void assignEmployee(final Employee employee) {
        if (this.employee != null) {
            throw new AssignEmployeeException(OCCUPIED_VACANCY_MESSAGE);
        } else if (this.isDeleted) {
            throw new AssignEmployeeException(DELETED_VACANCY_MESSAGE);
        } else {
            this.employee = employee;
            employee.setVacancy(this);
        }
    }

    public void removeEmployee() {
        if (this.employee == null) {
            throw new RemoveEmployeeException(VACANCY_WITHOUT_EMPLOYEE_MESSAGE);
        } else {
            this.employee.setVacancy(null);
            this.employee = null;
        }
    }

    // TODO удалить метод toString
    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", salary=" + salary +
                ", position='" + position + '\'' +
                ", employee_id=" + (employee == null ? null : employee.getId()) +
                ", addedAt=" + addedAt +
                ", isDeleted=" + isDeleted +
                ", deletedAt=" + deletedAt +
                '}';
    }
}

/*
*               const
*               arg     nullable    unique  insertable  updatable   setter  getter  init_method change_method
*   ---------------------------------------------------------------------------------------------------------
*   id          no      no          yes     yes         no          no      yes     ---         ---
*   salary      yes     no          no      yes         yes         yes     yes     constructor setter
*   position    yes     no          no      yes         yes         yes     yes     constructor setter
*   employee    no      yes         yes     yes         yes         no      yes     ---         assignEmployee, removeEmployee
*   addedAt     no      no          no      yes         no          no      yes     constructor ---
*   isDeleted   no      no          no      yes         no          no      yes     constructor @SQLDelete
*   deletedAt   no      yes         no      no          no          no      yes     ---         @SQLDelete
*
**/