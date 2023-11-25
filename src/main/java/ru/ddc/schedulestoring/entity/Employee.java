package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;

@Getter
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
    public static final String NULL_PERSONAL_DATA_MESSAGE = "Не указаны персональные данные";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter
    @Embedded
    @NotNull(message = NULL_PERSONAL_DATA_MESSAGE)
    private PersonalData personalData;

    @Setter
    @OneToOne(mappedBy = "employee")
    private Vacancy vacancy = null;

    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDate addedAt = LocalDate.now();

    @Column(name = "is_deleted", nullable = false, updatable = false)
    private boolean isDeleted = false;

    @Column(name = "deleted_at", insertable = false, updatable = false)
    private LocalDate deletedAt = null;

    public Employee() {
    }

    public Employee(PersonalData personalData) {
        this.personalData = personalData;
    }
}

/*
 *                  null            insert  update                  init    change
 *  field           able    unique  able    able    setter  getter  method  method
 *  ------------------------------------------------------------------------------
 *  --- THIS ---------------------------------------------------------------------
 *  ------------------------------------------------------------------------------
 *  id              no      yes     yes     no      no      yes     ---     ---
 *  personal_data   ---     yes     ---     ---     yes     yes     const   setter
 *      firstname   no      ---     yes     yes     ---     yes     ---     ---
 *      lastname    no      ---     yes     yes     ---     yes     ---     ---
 *      patronymic  yes     ---     yes     yes     ---     yes     ---     ---
 *      birthdate   no      ---     yes     yes     ---     yes     ---     ---
 *  vacancy         yes     ---     yes     yes     yes     yes     const   setter
 *  addedAt         no      no      yes     no      no      yes     const   ---
 *  isDeleted       no      no      yes     no      no      yes     const   @SQLDelete
 *  deletedAt       yes     no      no      no      no      yes     const   @SQLDelete
 *  ------------------------------------------------------------------------------
 *
 **/
