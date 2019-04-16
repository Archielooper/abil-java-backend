package com.policy.bazaar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.policy.bazaar.security.CustomerAuthorizationFilter;
import com.policy.bazaar.security.EmployeeAuthorizationFilter;

@SpringBootApplication
@EnableScheduling
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
	public FilterRegistrationBean<CustomerAuthorizationFilter> JWTAuthorizationFilterRegistration() {

		FilterRegistrationBean<CustomerAuthorizationFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(getJWTAuthorizationFilter());
		registration.addUrlPatterns("/customers/getProfile/*", "/customers/getpurchasedpolicies/*", "/addpolicies");
		registration.setName("jwtAuthorizationFilter");
		registration.setOrder(2);
		return registration;
	}

	@Bean(name = "jWTAuthorizationFilterBean")
	public CustomerAuthorizationFilter getJWTAuthorizationFilter() {
		return new CustomerAuthorizationFilter();
	}

	@Bean
	public FilterRegistrationBean<EmployeeAuthorizationFilter> employeeAuthorizationFilterRegistration() {

		FilterRegistrationBean<EmployeeAuthorizationFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(getEmployeeAuthorizationFilter());
		registration.addUrlPatterns("/employee/createEmployee", "/employee/createPolicy", "/employee/getProfile/*",
				"/employee/getEmployees", "/employee/updatepassword/*");
		registration.setName("employeeAuthorizationFilter");
		registration.setOrder(1);
		return registration;
	}

	@Bean(name = "employeeAuthorizationFilterBean")
	public EmployeeAuthorizationFilter getEmployeeAuthorizationFilter() {
		return new EmployeeAuthorizationFilter();
	}

}
