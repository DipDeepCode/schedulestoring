package ru.ddc.schedulestoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ddc.schedulestoring.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}