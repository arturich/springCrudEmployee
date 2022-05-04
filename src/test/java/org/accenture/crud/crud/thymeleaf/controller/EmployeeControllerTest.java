package org.accenture.crud.crud.thymeleaf.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.accenture.crud.crud.thymeleaf.dao.EmployeeRepository;
import org.accenture.crud.crud.thymeleaf.entity.Employee;
import org.accenture.crud.crud.thymeleaf.service.EmployeeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	EmployeeServiceImpl employeeServiceMock;
	
	@Autowired
	EmployeeRepository employeeDAO;
	

	@Test
	@DisplayName("Get the list of employees")
	@Order(1)
	@WithMockUser(username = "john", password = "test123", roles = {"EMPLOYEE","ADMIN"})
	public void getTheListOfEmployees()  throws Exception
	{
		Employee employee 		= new Employee(0,"Arturo","Reyes","arturo@gmail.com");
		Employee employeeTwo 	= new Employee(0,"Mario","Bros","mario@gmail.com");
		
		List<Employee> listEmployees = new ArrayList<>(Arrays.asList(employee,employeeTwo));
		
		when(employeeServiceMock.findAll())
			.thenReturn(listEmployees);
		
		assertIterableEquals(listEmployees, employeeServiceMock.findAll(), "Expected list of employees should be of 2");
		
		MvcResult mockResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees/list"))
				.andExpect(status().isOk())
				.andReturn();
		
		ModelAndView mav = mockResult.getModelAndView();
		
		//assertEquals("employees/list-all",mav.getViewName());
		ModelAndViewAssert.assertViewName(mav, "employees/list-all");
				
	}
	
	@Test
	@DisplayName("Show form for adding an employee")
	@Order(2)
	@WithMockUser(username = "john", password = "test123", roles = {"EMPLOYEE","ADMIN"})
	public void showFormForAddUser() throws Exception
	{
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees/showFormForAdd"))
				.andExpect(status().isOk())
				.andReturn();
				
		ModelAndView mav = mvcResult.getModelAndView();
		
		ModelAndViewAssert.assertViewName(mav, "employees/add-employee-form");
		
	}

}
