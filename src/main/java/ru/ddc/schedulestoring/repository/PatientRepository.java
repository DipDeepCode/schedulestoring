package ru.ddc.schedulestoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ddc.schedulestoring.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}