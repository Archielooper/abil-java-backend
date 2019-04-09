package com.policy.bazaar.employee;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.bazaar.customers.Customers;
import com.policy.bazaar.encryption.AES256Encryption;
import com.policy.bazaar.exceptions.NotFoundException;
import com.policy.bazaar.globalresponse.GlobalResponse;
import com.policy.bazaar.globalresponse.Response;
import com.policy.bazaar.mail.SendEmail;
import com.policy.bazaar.policy.Policies;
import com.policy.bazaar.policy.PurchasedPoliciesResponse;
import com.policy.bazaar.policy.Purchasedpolicies;
import com.policy.bazaar.repository.CustomerRepository;
import com.policy.bazaar.repository.EmployeePasswordRepository;
import com.policy.bazaar.repository.EmployeeRepository;
import com.policy.bazaar.repository.PoliciesRepository;
import com.policy.bazaar.repository.PurchasedPoliciesRepository;
import com.policy.bazaar.security.JWTEmployeeToken;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository empRepository;

	@Autowired
	EmployeePasswordRepository empPasswordRepository;

	@Autowired
	PurchasedPoliciesRepository purchasedPoliciesRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PoliciesRepository policyRepository;

	public GlobalResponse createEmployee(@Valid EmployeeCreateRequest empDetails) {
		Employees emp = new Employees();
		GlobalResponse response = new GlobalResponse();
		EmployeeCreateResponse emp1 = new EmployeeCreateResponse();
		Employeepasswords emppass = new Employeepasswords();
		UUID uuid = UUID.randomUUID();
		Employees findByEmail = empRepository.findByEmail(empDetails.getEmail());
        SendEmail email = new SendEmail();
		if (findByEmail != null) {

			response.setData(null);
			response.setStatus(false);
			response.setMessage("Employee with the email- " + empDetails.getEmail() + " already registered");
		} else {
			emp.setFullname(empDetails.getFullname());
			emp.setEmail(empDetails.getEmail());
			emp.setMobile(empDetails.getMobile());
			emp.setCreatedon(new Date(System.currentTimeMillis()));
			emp.setUsertype(empDetails.getUsertype());
			empRepository.save(emp);
			emppass.setUuid(uuid);
			emppass.setEmpId(emp.getEmpid());
			emppass.setStatus(true);
			empPasswordRepository.save(emppass);
			emp1.setEmpid(emp.getEmpid());
			emp1.setFullname(emp.getFullname());
			email.sendEmail(empDetails.getEmail());
			response.setData(emp1);
			response.setStatus(true);
			response.setMessage("Successfully Registered");
		}
		return response;
	}

	public GlobalResponse getLogin(EmployeeLoginRequest empLogin) {

		Employees findByEmail = empRepository.findByEmail(empLogin.getEmail());
		GlobalResponse response = new GlobalResponse();
		if (findByEmail == null) {
			response.setData(null);
			response.setStatus(false);
			response.setMessage("Email or Password not valid");
		} else {

			String password = findByEmail.getPassword();
			String userPassword = AES256Encryption.encrypt(empLogin.getPassword(), AES256Encryption.secretKey);
			Integer empUserType = findByEmail.getUsertype();

			Response response1 = new Response();
			response1.setAuth(true);
			response1.setUserType(empUserType);
			if (password.equals(userPassword)) {
				response.setData(JWTEmployeeToken.createJWT(2000000000L, findByEmail));
				response.setStatus(true);
				response.setMessage("Authenticated!!!!!");
			} else {
				response.setData(null);
				response.setStatus(false);
				response.setMessage("Email or Password not valid");
			}
		}
		return response;
	}

	public GlobalResponse setPass(EmployeeRequest empDetails) throws NotFoundException {

		Optional<Employeepasswords> empPasswordopt = empPasswordRepository.findByUuid(empDetails.getUuid());

		GlobalResponse response = new GlobalResponse();

		if (empPasswordopt.isPresent()) {
			Employeepasswords empPassword = empPasswordopt.get();
			if (empPassword.getStatus() == false) {

				response.setData(null);
				response.setStatus(false);
				response.setMessage("UUID already used!!");
			} else {
				Employees emp = empRepository.findById(empPassword.getEmpId()).get();
				String encryptedString = AES256Encryption.encrypt(empDetails.getPassword(), AES256Encryption.secretKey);
				empPassword.setPassword(encryptedString);
				empPassword.setStatus(false);
				emp.setPassword(encryptedString);
				empPasswordRepository.save(empPassword);
				empRepository.save(emp);
				response.setData(null);
				response.setStatus(true);
				response.setMessage("Password Successfully Updated");
			}

		} else if (!empPasswordopt.isPresent()) {

			throw new NotFoundException("Employee with the uuid-" + empDetails.getUuid() + " not found!!!");
		}
		return response;

	}

	public GlobalResponse getProfile(Integer id) {

		Optional<Employees> emp = empRepository.findById(id);
		EmployeeResponse employeeResponse = new EmployeeResponse();
		GlobalResponse globalResponse = new GlobalResponse();
		if (!emp.isPresent()) {

			throw new NotFoundException("Employee with the id- " + id + " not found!!!!");
		} else {
			Employees emp1 = emp.get();
			employeeResponse.setFullname(emp1.getFullname());
			employeeResponse.setEmail(emp1.getEmail());
			employeeResponse.setMobile(emp1.getMobile());
			employeeResponse.setUserType(emp1.getUsertype());
			globalResponse.setData(employeeResponse);
			globalResponse.setStatus(true);
			globalResponse.setMessage("Authorized!!!");
		}
		return globalResponse;
	}

	public GlobalResponse getEmployees() {

		List<Employees> listEmployees = empRepository.findAll();

		List<EmployeeResponse> employeeResponse = new ArrayList<EmployeeResponse>();
		GlobalResponse globalResponse = new GlobalResponse();

		listEmployees.stream().forEach((i) -> {
			if (i.getUsertype() == 2 || i.getUsertype() == 3) {
				EmployeeResponse empResponse = new EmployeeResponse();
				empResponse.setFullname(i.getFullname());
				empResponse.setEmail(i.getEmail());
				empResponse.setMobile(i.getMobile());
				empResponse.setUserType(i.getUsertype());
				employeeResponse.add(empResponse);
				globalResponse.setData(employeeResponse);
				globalResponse.setStatus(true);
				globalResponse.setMessage("Authorized!!!!");
			}
		});

		return globalResponse;
	}

	public GlobalResponse getCustomers() {

		List<Purchasedpolicies> purchasedpolicies = purchasedPoliciesRepository.findAll();
		GlobalResponse globalResponse = new GlobalResponse();
		List<PurchasedPoliciesResponse> purchasedPoliciesResponses = new ArrayList<PurchasedPoliciesResponse>();

		purchasedpolicies.stream().forEach((j) -> {

			Optional<Customers> customers = customerRepository.findById(j.getCid());
			Optional<Policies> policies = policyRepository.findById(j.getPid());
			PurchasedPoliciesResponse purchasedresponse = new PurchasedPoliciesResponse();
			Customers customer = customers.get();
			Policies policy = policies.get();
			purchasedresponse.setCustomerfirstname(customer.getFirstname());
			purchasedresponse.setCustomerlastname(customer.getLastname());
			purchasedresponse.setStartdate(j.getStartdate());
			purchasedresponse.setEnddate(j.getEnddate());
			purchasedresponse.setPolicyname(policy.getPolicyname());
			purchasedresponse.setAmount(policy.getAmount());
			purchasedPoliciesResponses.add(purchasedresponse);
			globalResponse.setData(purchasedPoliciesResponses);
			globalResponse.setStatus(true);
			globalResponse.setMessage("Authorized!!!!");

		});

		return globalResponse;
	}

	public GlobalResponse updateEmployee(Integer empId, EmployeeUpdateRequest empUpdateRequest) {

		Optional<Employees> employees = empRepository.findById(empId);
		GlobalResponse globalResponse = new GlobalResponse();

		if (!employees.isPresent()) {

			throw new NotFoundException("Employee with id- " + empId + " not foumd!!!");

		} else {
			Employees emp = employees.get();
			emp.setFullname(empUpdateRequest.getFullname());
			emp.setMobile(empUpdateRequest.getMobile());
			emp.setLastupdatedon(new Date(System.currentTimeMillis()));
			empRepository.save(emp);

			globalResponse.setData(null);
			globalResponse.setStatus(true);
			globalResponse.setMessage("Updated Successfully!!!!");

		}

		return globalResponse;
	}

	public GlobalResponse updatePassword(Integer empId, EmployeeUpdatePasswordRequest empPasswordRequest) {

		Optional<Employees> employees = empRepository.findById(empId);
		GlobalResponse globalResponse = new GlobalResponse();
		Employees emp = employees.get();

		if (!employees.isPresent()) {

			throw new NotFoundException("Employee with id- " + empId + " not found!!!");

		} else {

			String encryptedOldPassword = AES256Encryption.encrypt(empPasswordRequest.getOldPassword(),
					AES256Encryption.secretKey);
			if (emp.getPassword() != encryptedOldPassword) {

				globalResponse.setData(null);
				globalResponse.setMessage("You have entered a wrong password");
				globalResponse.setStatus(false);
			} else {

				String encryptedNewPassword = AES256Encryption.encrypt(empPasswordRequest.getNewPassword(),
						AES256Encryption.secretKey);

				emp.setPassword(encryptedNewPassword);
				empRepository.save(emp);
				globalResponse.setData(null);
				globalResponse.setMessage("Password Change Successfully!!!");
				globalResponse.setStatus(true);
			}

		}

		return globalResponse;
	}

	public GlobalResponse deleteEmployee(Integer empId) {

		Optional<Employees> employee = empRepository.findById(empId);
		GlobalResponse globalResponse = new GlobalResponse();

		if (!employee.isPresent()) {

			throw new NotFoundException("Employee with id- " + empId + " not found!!!");
		} else {

			empRepository.deleteById(empId);
            globalResponse.setData(null);
            globalResponse.setStatus(true);
            globalResponse.setMessage("Employee deleted successfully!!!!");
		}

		return globalResponse;
	}
}
