package org.accenture.crud.crud.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.accenture.crud.crud.thymeleaf.dao.EmployeeRepository;
import org.accenture.crud.crud.thymeleaf.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl()
	{
		
	}
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository)
	{
		this.employeeRepository = employeeRepository;
	}
	@Transactional
	public List<Employee> findAll()
	{
		return employeeRepository.findAll();
	}
	
	@Transactional
	public Employee findById(int theId)
	{
		Optional<Employee> empOpt = employeeRepository.findById(theId);
		
		Employee theEmployee = null;
		
		if(empOpt.isPresent())
		{
			theEmployee = empOpt.get();
		}
		else
		{
			throw new RuntimeException("The employee Id does not exist: "+ theId );
		}
		
		
		return theEmployee;
	}
	

	@Override
	@Transactional
	public Employee saveEmployee(Employee theEmployee) {
		return employeeRepository.save(theEmployee);
	}

	@Override
	@Transactional
	public void deleteEmployeeById(int theId) {
		employeeRepository.deleteById(theId);
		
	}
	

}
