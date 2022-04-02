package org.accenture.crud.crud.thymeleaf.controller;

import java.util.List;

import org.accenture.crud.crud.thymeleaf.entity.Employee;
import org.accenture.crud.crud.thymeleaf.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	EmployeeService empService;
	
	public EmployeeController()
	{
		
	}
	
	@Autowired
	public EmployeeController(EmployeeService empService)
	{
		this.empService = empService;
	}
	
	@GetMapping("/list")
	public String getEmployees(Model theModel)
	{
		
		List<Employee> listEmployees = empService.findAll();
		
		theModel.addAttribute("employees",listEmployees);
		
		return "employees/list-all";
		
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel)
	{
		Employee emp = new Employee();
		
		theModel.addAttribute("employee",emp);
		
		return "employees/add-employee-form";
	}
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee)
	{
		empService.saveEmployee(theEmployee);
		
		return "redirect:/employees/list";
	}

}
