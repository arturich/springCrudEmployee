package org.accenture.crud.crud.thymeleaf.dao;

import org.accenture.crud.crud.thymeleaf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
