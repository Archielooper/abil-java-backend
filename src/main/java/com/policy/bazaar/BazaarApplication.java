package com.policy.bazaar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.policy.bazaar.security.EmployeeAuthorizationFilter;
import com.policy.bazaar.security.JWTAuthorizationFilter;

@SpringBootApplication
public class BazaarApplication {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:validation");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	public static void main(String[] args) {
		SpringApplication.run(BazaarApplication.class, args);
	}
	
	
	@Bean
	public FilterRegistrationBean<JWTAuthorizationFilter> JWTAuthorizationFilterRegistration() {

	    FilterRegistrationBean<JWTAuthorizationFilter> registration = new FilterRegistrationBean<>();
	    registration.setFilter(getJWTAuthorizationFilter());
	    registration.addUrlPatterns("/customers/getProfile/*","/customers/getpurchasedpolicies/*","/addpolicies");
	    registration.setName("jwtAuthorizationFilter");
	    registration.setOrder(2);
	    return registration;
	} 
	
	
	@Bean(name = "jWTAuthorizationFilterBean")
	public JWTAuthorizationFilter getJWTAuthorizationFilter() {
	    return new JWTAuthorizationFilter();
	}
       
	@Bean
	public FilterRegistrationBean<EmployeeAuthorizationFilter> employeeAuthorizationFilterRegistration() {

	    FilterRegistrationBean<EmployeeAuthorizationFilter> registration = new FilterRegistrationBean<>();
	    registration.setFilter(getEmployeeAuthorizationFilter());
	    registration.addUrlPatterns("/employee/createEmployee","/employee/createPolicy","/employee/setPassword","/employee/getProfile/*");
	    registration.setName("employeeAuthorizationFilter");
	    registration.setOrder(1);
	    return registration;
	} 
	
	
	@Bean(name = "employeeAuthorizationFilterBean")
	public EmployeeAuthorizationFilter getEmployeeAuthorizationFilter() {
	    return new EmployeeAuthorizationFilter();
	}


}
