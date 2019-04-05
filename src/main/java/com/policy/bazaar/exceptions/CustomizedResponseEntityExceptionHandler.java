package com.policy.bazaar.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.policy.bazaar.Security.NotValidException;

@ControllerAdvice
@RestController
@Configuration
@PropertySource("classpath:errors.properties")
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
	
	@Value("${errors.validation}")
	String messagevalid = "{errors.validation}";
	
	@Value("${errors.exception}")
	String messageExc = "{errors.exception}";
	

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionDetails handleAllException(Exception ex) {

		return new ExceptionDetails(new Date(), messageExc, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionDetails empNotFoundException(NotFoundException ex) {
		return new ExceptionDetails(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NotValidException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionDetails notValidException(NotValidException ex) {
		return new ExceptionDetails(new Date(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}

		ErrorDetails errorDetails = new ErrorDetails(new Date(), messagevalid, errors, HttpStatus.BAD_REQUEST);

		return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
		
		
	}

}
