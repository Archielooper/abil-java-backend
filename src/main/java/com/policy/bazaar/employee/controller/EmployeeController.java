package com.policy.bazaar.employee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policy.bazaar.common.exception.PolicyBazaarServiceException;
import com.policy.bazaar.employee.request.EmployeeCreateRequest;
import com.policy.bazaar.employee.request.EmployeeLoginRequest;
import com.policy.bazaar.employee.request.EmployeeRequest;
import com.policy.bazaar.employee.request.EmployeeUpdatePasswordRequest;
import com.policy.bazaar.employee.request.EmployeeUpdateRequest;
import com.policy.bazaar.employee.request.updateStatusRequest;
import com.policy.bazaar.employee.service.EmployeeService;
import com.policy.bazaar.globalresponse.GlobalResponse;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

	@Autowired
	EmployeeService empService;

	@PostMapping("/createEmployee")
	public GlobalResponse createEmployee(HttpServletRequest requestSS,
			@Valid @RequestBody EmployeeCreateRequest empDetails) {

		GlobalResponse globalResponse = new GlobalResponse();

		try {
			globalResponse = empService.createEmployee(empDetails);
		} catch (PolicyBazaarServiceException pbse) {
			globalResponse.setStatus(Boolean.FALSE);
			globalResponse.setMessage(pbse.getMessage());

		}
		return globalResponse;
	}

	@PostMapping("/emplogin")
	public GlobalResponse login(@RequestBody EmployeeLoginRequest empLogin) {
		return empService.getLogin(empLogin);
	}

	@PostMapping("/setPassword")
	public GlobalResponse setPassword(@Valid @RequestBody EmployeeRequest empDetails) {

		return empService.setPass(empDetails);
	}

	@GetMapping("/getProfile/{empid}")
	public GlobalResponse getProfile(@PathVariable Integer empid) {

		return empService.getProfile(empid);

	}

	@GetMapping("/getEmployees/")
	public GlobalResponse getEmployees(@PageableDefault(value = 3, page = 0) Pageable pageable) {

		return empService.getEmployees(pageable);
	}

	@GetMapping("/getCustomers/")
	public GlobalResponse getCustomers(@PageableDefault(value = 3, page = 0) Pageable pageable) {

		return empService.getCustomers(pageable);

	}

	@PutMapping("/update/{empId}")
	public GlobalResponse updateEmployee(@PathVariable Integer empId,
			@RequestBody EmployeeUpdateRequest empUpdateRequest) {

		return empService.updateEmployee(empId, empUpdateRequest);
	}

	@PutMapping("/updatepassword/{empId}")
	public GlobalResponse updatePassword(@PathVariable Integer empId,
			@RequestBody EmployeeUpdatePasswordRequest empPasswordRequest) {

		return empService.updatePassword(empId, empPasswordRequest);
	}

	@DeleteMapping("/delete/{empId}")
	public GlobalResponse deleteEmployee(@PathVariable Integer empId) {

		return empService.deleteEmployee(empId);

	}

	@GetMapping("/viewallpurchasedpolicies/")
	public GlobalResponse viewAllPurchased(@PageableDefault(value = 3, page = 0) Pageable pageable) {

		return empService.viewAllPurchased(pageable);
	}

	@PutMapping("/updateStatus")
	public GlobalResponse updateStatus(@RequestBody updateStatusRequest updateRequest) {

		return empService.updateStatus(updateRequest);
	}

}
