package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("physician")
public class Physician extends Employee {
    public static final String NULL_OR_EMPTY_SPECIALIZATIONS_MESSAGE = "Не указана специализация";
    public static final String NULL_SPECIALIZATION_MESSAGE = "Не указана специализация";
    public static final String SPECIALIZATIONS_ALREADY_ADDED_MESSAGE = "Такая специализация уже добавлена";
    public static final String DELETED_PHYSICIAN_MESSAGE = "Врач удален";
    public static final String MISSING_SPECIALIZATION_MESSAGE = "Специализация отсутствует";

    @Getter
    @OneToMany(mappedBy = "physician",
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST,
                    CascadeType.REFRESH, CascadeType.DETACH
            })
    private Set<Slot> slots;

    @Getter
    @ManyToMany
    @JoinTable(name = "physician_specializations",
            joinColumns = @JoinColumn(name = "physician_id"),
            inverseJoinColumns = @JoinColumn(name = "specializations_id"))
    private Set<Specialization> specializations;

    public Physician() {
    }

    public Physician(PersonalData personalData, Set<Specialization> specializations) {
        super(personalData);
        if (specializations == null || specializations.isEmpty()) {
            throw new IllegalArgumentException(NULL_OR_EMPTY_SPECIALIZATIONS_MESSAGE);
        } else {
            this.specializations = specializations;
            this.slots = new HashSet<>();
        }
    }

    public void addSpecialization(final Specialization specialization) {
        if (specialization == null) {
            throw new IllegalArgumentException(NULL_SPECIALIZATION_MESSAGE);
        } else if (super.isDeleted()) {
            throw new IllegalArgumentException(DELETED_PHYSICIAN_MESSAGE);
        } else if (!this.specializations.add(specialization)) {
            throw new IllegalArgumentException(SPECIALIZATIONS_ALREADY_ADDED_MESSAGE);
        }
    }

    public void removeSpecialization(final Specialization specialization) {
        if (specialization == null) {
            throw new IllegalArgumentException(NULL_SPECIALIZATION_MESSAGE);
        } else if (super.isDeleted()) {
            throw new IllegalArgumentException(DELETED_PHYSICIAN_MESSAGE);
        } else if (!this.specializations.remove(specialization)) {
            throw new IllegalArgumentException(MISSING_SPECIALIZATION_MESSAGE);
        }
    }

    public void addSlot(final Slot slot) {
        if (slot == null) {
            throw new IllegalArgumentException();
        } else if (super.isDeleted()) {
            throw new IllegalArgumentException();
        } else if (super.getVacancy() == null) {
            throw new IllegalArgumentException();
        } else if (!this.slots.add(slot)) {
            throw new IllegalArgumentException();
        }
    }

    // TODO удалить метод toString
    @Override
    public String toString() {
        return "Physician{" +
                "personalData=" + super.getPersonalData() +
                ", slots=" + slots +
                ", specializations=" + specializations +
                '}';
    }
}

/*
 *                  const   null            insert  update                  init    change
 *  field           arg     able    unique  able    able    setter  getter  method  method
 *  -------------------------------------------------------------------------------------------------
 *  --- SUPER ---------------------------------------------------------------------------------------
 *  -------------------------------------------------------------------------------------------------
 *  id              no      no      yes     yes     no      no      yes     ---     ---
 *  personal_data   yes     ---     yes     ---     ---     yes     yes     const   setter
 *      firstname   ---     no      ---     yes     yes     ---     yes     ---     ---
 *      lastname    ---     no      ---     yes     yes     ---     yes     ---     ---
 *      patronymic  ---     yes     ---     yes     yes     ---     yes     ---     ---
 *      birthdate   ---     no      ---     yes     yes     ---     yes     ---     ---
 *  vacancy         no      yes     ---     yes     yes     yes     yes     const   setter
 *  addedAt         no      no      no      yes     no      no      yes     const   ---
 *  isDeleted       no      no      no      yes     no      no      yes     const   @SQLDelete
 *  deletedAt       no      yes     no      no      no      no      yes     const   @SQLDelete
 *  -------------------------------------------------------------------------------------------------
 *  --- THIS ----------------------------------------------------------------------------------------
 *  -------------------------------------------------------------------------------------------------
 *  specializations yes     ---     ---     ---     ---     no      yes     const   addSpec, removeSpec
 *  slots           no      ---     ---     ---     ---     no      yes     const   addSlot, removeSlot
 *  -------------------------------------------------------------------------------------------------
 *
 **/
