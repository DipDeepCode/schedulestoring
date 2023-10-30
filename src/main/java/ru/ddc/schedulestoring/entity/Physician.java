package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("physician")
public class Physician extends Employee {

    @OneToMany(mappedBy = "physician",
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST,
                    CascadeType.REFRESH, CascadeType.DETACH
            })
    private final Set<Slot> slots = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "physician_specializations",
            joinColumns = @JoinColumn(name = "physician_id"),
            inverseJoinColumns = @JoinColumn(name = "specializations_id"))
    private Set<Specialization> specializations = new LinkedHashSet<>();

}
