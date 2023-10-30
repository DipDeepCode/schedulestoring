package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Embeddable
    public static class AppointmentId {

        @Column(name = "patient_id")
        private Long patientId;

        @Column(name = "slot_id")
        private Long slotId;
    }
    @EmbeddedId
    @Column(name = "id", nullable = false)
    private AppointmentId id;

    @ManyToOne
    @JoinColumn(
            name = "patient_id",
            insertable = false,
            updatable = false
    )
    private Patient patient;

    @ManyToOne
    @JoinColumn(
            name = "slot_id",
            insertable = false,
            updatable = false,
            unique = true
    )
    private Slot slot;

    @Column(name = "added_at", updatable = false)
    private LocalDateTime addedAt;

    @Column(name = "is_initial", nullable = false)
    private boolean isInitial;

    @Column(name = "note", length = 128)
    private String note;

    @ManyToOne(optional = false)
    @JoinColumn(name = "added_by", nullable = false)
    private Employee employee;
}
