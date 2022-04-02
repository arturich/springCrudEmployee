package org.accenture.crud.crud.thymeleaf.service;

import java.util.List;

import org.accenture.crud.crud.thymeleaf.entity.Employee;

public interface EmployeeService {
	
	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void saveEmployee(Employee theEmployee);
	
	public void deleteEmployeeById(int theId);

}
