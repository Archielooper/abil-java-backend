package com.policy.bazaar.employee;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

		return empService.createEmployee(empDetails);
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

	@GetMapping("/getEmployees")
	public GlobalResponse getEmployees() {

		return empService.getEmployees();
	}

	@GetMapping("/getCustomers")
	public GlobalResponse getCustomers(HttpServletRequest requestSS) {

		return empService.getCustomers();

	}
	@PutMapping("/update/{empId}")
	public GlobalResponse updateEmployee(@PathVariable Integer empId,@RequestBody EmployeeUpdateRequest empUpdateRequest) {
		
		return empService.updateEmployee(empId,empUpdateRequest);
	}
	
	@PutMapping("/updatepassword/{empId}")
	public GlobalResponse updatePassword(@PathVariable Integer empId, @RequestBody EmployeeUpdatePasswordRequest empPasswordRequest) {
		
		return empService.updatePassword(empId,empPasswordRequest);
	}
	
	@DeleteMapping("/delete/{empId}")
	public GlobalResponse deleteEmployee(@PathVariable Integer empId)
	{
		
		return empService.deleteEmployee(empId);
		
	}
	
	@GetMapping("/viewallpurchasedpolicies")
	public GlobalResponse viewAllPurchased() {
		
		return empService.viewAllPurchased();
	}
	
	@PutMapping("/updateStatus")
	public GlobalResponse updateStatus(@RequestBody updateStatusRequest updateRequest) {
		
		return empService.updateStatus(updateRequest);
	}
	
}
