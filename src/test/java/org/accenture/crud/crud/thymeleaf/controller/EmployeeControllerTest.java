package org.accenture.crud.crud.thymeleaf.controller;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.accenture.crud.crud.thymeleaf.dao.EmployeeRepository;
import org.accenture.crud.crud.thymeleaf.entity.Employee;
import org.accenture.crud.crud.thymeleaf.service.EmployeeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
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
	
	Employee employeeTest;
	
	private static MockHttpServletRequest request;
	
	@AfterEach
	public void createEmployee()
	{
		employeeTest = new Employee(0,"Arturo","Reyes","arturo@gmail.com");		
	}
	
	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
		request.setParameter("firstname", "Chad");
		request.setParameter("lastname", "Darby");
		request.setParameter("emailAddress", "chad.darby@luv2code_school.com");
	}
	

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
	
	@Test
	@DisplayName("Save an employee")
	@Order(3)	
	@WithMockUser(username = "john", password = "test123", roles = {"EMPLOYEE","ADMIN","MANAGER"})
	public void saveAnEmployeeRest() throws Exception
	{
		when(employeeServiceMock.saveEmployee(any(Employee.class))).thenReturn(employeeTest);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/employees/save")
				.with(csrf()) // <--- missing part
				.contentType(MediaType.APPLICATION_JSON)
				.param("firstname", request.getParameterValues("firstname"))
				.param("lastname", request.getParameterValues("lastname"))
				.param("emailAddress", request.getParameterValues("emailAddress")))
		.andExpect(status().is3xxRedirection()).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		
		ModelAndViewAssert.assertViewName(mav, "redirect:/employees/list");
		
		verify(employeeServiceMock,times(0)).saveEmployee(employeeTest);
		
		
	}
	
	@Test
	@DisplayName("Delete an employee")
	@Order(4)
	@WithMockUser(username = "john", password = "test123", roles = {"EMPLOYEE","ADMIN","MANAGER"})
	public void deleteAnEmployee() throws Exception
	{
		doNothing().when(employeeServiceMock).deleteEmployeeById(1);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees/delete")
				.param("employeeId", "1"))
			.andExpect(status().is3xxRedirection())
			.andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		ModelAndViewAssert.assertViewName(mav,"redirect:/employees/list");
	}

}
