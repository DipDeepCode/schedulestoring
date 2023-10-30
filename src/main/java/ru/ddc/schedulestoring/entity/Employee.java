package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;

@Entity
@Table(
        name = "employee",
        uniqueConstraints = @UniqueConstraint(
                name = "UniquePersonalData",
                columnNames = {"firstname", "lastname", "patronymic", "birthdate"}
        )
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "employee_type")
@SQLDelete(sql = "update employee set deleted_at=current_date, is_deleted=true where id=?")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    private PersonalData personalData;

    @OneToOne(mappedBy = "employee")
    private Vacancy vacancy;

    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDate addedAt;

    @Column(name = "is_deleted", nullable = false, updatable = false)
    private boolean isDeleted;

    @Column(name = "deleted_at", insertable = false, updatable = false)
    private LocalDate deletedAt;

    public Employee() {
    }

    public Employee(PersonalData personalData) {
        this.personalData = personalData;
        this.addedAt = LocalDate.now();
        this.isDeleted = false;
        this.deletedAt = null;
    }

    public Long getId() {
        return id;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public LocalDate getAddedAt() {
        return addedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    /*
*
*                   const   nullable    unique  insertable  updatable   setter  getter  init_method change_method
*   id              no      false       true    true        false       no      yes     ---         ---
*   personal_data   yes     ---         true    ---         ---         yes     yes     constructor setter
*       firstname   ---     false       ---     true        true        ---     yes     ---         ---
*       lastname    ---     false       ---     true        true        ---     yes     ---         ---
*       patronymic  ---     true        ---     true        true        ---     yes     ---         ---
*       birthdate   ---     false       ---     true        true        ---     yes     ---         ---
*   vacancy         no      true        ---     true        true        yes     yes     ---         setter
*   addedAt         no      false       false   true        false       no      yes     constructor ---
*   isDeleted       no      false       false   true        false       no      yes     constructor @SQLDelete
*   deletedAt       no      true        false   false       false       no      yes     constructor @SQLDelete
*
**/
}
