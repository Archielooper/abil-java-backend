package com.policy.bazaar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.policy.bazaar.security.CorsFilter1;
import com.policy.bazaar.security.CustomerAuthorizationFilter;
import com.policy.bazaar.security.EmployeeAuthorizationFilter;

@SpringBootApplication
@EnableScheduling
public class BazaarApplication implements WebMvcConfigurer{
	
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:src/main/resources/static/images" };

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
	
	@Bean
	public FilterRegistrationBean<CorsFilter1> corsAuthorizationFilterRegistration() {

		FilterRegistrationBean<CorsFilter1> registration = new FilterRegistrationBean<>();
		registration.setFilter(corsAuthorizationFilter());
		registration.addUrlPatterns("*");
		registration.setName("corsAuthorizationFilter");
		registration.setOrder(1);
		return registration;
	}

	@Bean(name = "corsAuthorizationFilterBean")
	public CorsFilter1 corsAuthorizationFilter() {
		return new CorsFilter1();
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern("/webjars/**")) {
			registry.addResourceHandler("/webjars/**").addResourceLocations(
					"classpath:/META-INF/resources/webjars/");
		}
		if (!registry.hasMappingForPattern("/**")) {
			registry.addResourceHandler("/**").addResourceLocations(
					CLASSPATH_RESOURCE_LOCATIONS);
		}
    }


}
