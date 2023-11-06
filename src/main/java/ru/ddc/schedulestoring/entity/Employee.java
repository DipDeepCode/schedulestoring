package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Embedded
    private PersonalData personalData;

    @Getter
    @Setter
    @OneToOne(mappedBy = "employee")
    private Vacancy vacancy;

    @Getter
    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDate addedAt;

    @Getter
    @Column(name = "is_deleted", nullable = false, updatable = false)
    private boolean isDeleted;

    @Getter
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

    // TODO удалить метод toString
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", personalData=" + personalData +
                ", vacancy_id=" + (vacancy == null ? null : vacancy.getId()) +
                ", addedAt=" + addedAt +
                ", isDeleted=" + isDeleted +
                ", deletedAt=" + deletedAt +
                '}';
    }

/*
*                   const
*                   arg     nullable    unique  insertable  updatable   setter  getter  init_method change_method
*   -------------------------------------------------------------------------------------------------------------
*   id              no      no          yes     yes         no          no      yes     ---         ---
*   personal_data   yes     ---         yes     ---         ---         yes     yes     constructor setter
*       firstname   ---     no          ---     yes         yes         ---     yes     ---         ---
*       lastname    ---     no          ---     yes         yes         ---     yes     ---         ---
*       patronymic  ---     yes         ---     yes         yes         ---     yes     ---         ---
*       birthdate   ---     no          ---     yes         yes         ---     yes     ---         ---
*   vacancy         no      yes         ---     yes         yes         yes     yes     ---         setter
*   addedAt         no      no          no      yes         no          no      yes     constructor ---
*   isDeleted       no      no          no      yes         no          no      yes     constructor @SQLDelete
*   deletedAt       no      yes         no      no          no          no      yes     constructor @SQLDelete
*
**/
}
