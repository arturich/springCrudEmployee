package org.accenture.crud.crud.thymeleaf.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.accenture.crud.crud.thymeleaf.dao.EmployeeRepository;
import org.accenture.crud.crud.thymeleaf.entity.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
class EmployeeServiceImplTest {
	
	static Employee employee;
	Employee savedEmployee;
	
	@Autowired
	EmployeeServiceImpl employeeService;
	
	@Autowired
	EmployeeRepository employeeDAO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		employee = new Employee(0,"Arturo","Reyes","arturo@gmail.com");		
		
	}
	
	@BeforeEach
	public void initData()
	{
		savedEmployee = employeeDAO.save(employee);
		
	}

	@AfterEach
	public void removeData()
	{
		employeeDAO.delete(savedEmployee);
	}
	



	@Test
	@DisplayName("Find all the employes in DB")
	void numberOfTotalEmployees() throws Exception	
	{
		//Check if we have at least something in the DB
		long numEmployees = employeeDAO.count();
		assertTrue(numEmployees > 0,"The number of employees must be bigger than 0 to test it");
		
		//Check if DAO and Service provide same number
		assertEquals(numEmployees,(long)employeeService.findAll().size(), "The number of employees doesn't match");
		
	}
	
	@Test
	@DisplayName("Find Employee by ID")
	public void findEmployeeByID()
	{
		//check if we have the inserted employee id
		int id = savedEmployee.getId();
		assertTrue(id > 0,"The Id of the employee must be bigger than 0");
		
		assertDoesNotThrow(()-> {employeeService.findById(id);},"No exception is expected at finding Employee");		
	}
	
	@Test
	@DisplayName("Expect exception when invalid Id is passed to find Employee")
	public void exceptionAtInvalidEmployeeId()
	{
		//Set invalid id
		int id = 0;
		//Check that the Id doesnt exist.
		assertThrows(RuntimeException.class, ()-> { employeeService.findById(id); }," Throw Exception when invalid id is passed");
	}

}
