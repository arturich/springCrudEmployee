package org.accenture.crud.crud.thymeleaf.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class CrudSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource securityDataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		
		http.authorizeRequests()
       // .anyRequest().authenticated()
		.antMatchers("/").hasRole("EMPLOYEE")
        .and()
        .formLogin()
        .loginPage("/showMyLoginPage")
        .loginProcessingUrl("/validateUser")
        .permitAll()
        .and()
        .logout().permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedPage("/access-denied");
		
		
//		http.authorizeHttpRequests()
//		.antMatchers("/").hasRole("EMPLOYEE")
//		.antMatchers("/actuator/**").hasRole("ADMIN")
//		.and()
//		.formLogin()
//		.loginPage("/login/custom-page")
//		.loginProcessingUrl("/authenticateTheUser")
//		.permitAll()
//		.and()
//		.logout().permitAll()
//		.and()
//		.exceptionHandling()
//		.accessDeniedPage("/login/access-denied");
	}
}
