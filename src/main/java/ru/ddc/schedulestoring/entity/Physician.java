package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("physician")
public class Physician extends Employee {


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
        this.slots = new HashSet<>();
        this.specializations = specializations;
    }

    public void addSlot(final Slot slot) {
        if (slot == null) {
            throw new IllegalArgumentException();
        } else if (super.isDeleted()) {
            throw new IllegalArgumentException();
        } else if (super.getVacancy() == null) {
            throw new IllegalArgumentException();
        } else if (this.slots.contains(slot)) {
            throw new IllegalArgumentException();
        } else {
            this.slots.add(slot);
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
 *  slots           no      ---     ---     ---     ---     no      yes     const   addSlot, removeSlot
 *  specializations yes     ---     ---     ---     ---     no      yes     const
 *  -------------------------------------------------------------------------------------------------
 *
 **/

}
