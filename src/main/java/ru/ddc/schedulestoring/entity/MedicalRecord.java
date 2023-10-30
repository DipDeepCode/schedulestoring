package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;

@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "note", length = 64)
    private String note;
}
