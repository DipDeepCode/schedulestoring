package ru.ddc.schedulestoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ddc.schedulestoring.entity.Vacancy;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}