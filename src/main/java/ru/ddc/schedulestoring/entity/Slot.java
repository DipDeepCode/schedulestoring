package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "day_date", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private SlotStartTime startTime;

    @Column(name = "note", length = 128)
    private String note;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "physician_id", nullable = false)
    private Physician physician;
}
