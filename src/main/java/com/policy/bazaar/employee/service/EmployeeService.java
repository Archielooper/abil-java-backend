package com.policy.bazaar.employee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.policy.bazaar.common.exception.NotFoundException;
import com.policy.bazaar.common.exception.PolicyBazaarServiceException;
import com.policy.bazaar.communication.SendEmail;
import com.policy.bazaar.customers.model.Customers;
import com.policy.bazaar.customers.response.CustomerProfileResponse;
import com.policy.bazaar.employee.model.Employees;
import com.policy.bazaar.employee.request.EmployeeCreateRequest;
import com.policy.bazaar.employee.request.EmployeeLoginRequest;
import com.policy.bazaar.employee.request.EmployeeRequest;
import com.policy.bazaar.employee.request.EmployeeUpdatePasswordRequest;
import com.policy.bazaar.employee.request.EmployeeUpdateRequest;
import com.policy.bazaar.employee.request.updateStatusRequest;
import com.policy.bazaar.employee.response.AllCountResponse;
import com.policy.bazaar.employee.response.EmployeeCreateResponse;
import com.policy.bazaar.employee.response.EmployeeResponse;
import com.policy.bazaar.employee.response.Employeepasswords;
import com.policy.bazaar.employee.response.ViewPurchasedPoliciesResponse;
import com.policy.bazaar.encryption.AES256Encryption;
import com.policy.bazaar.globalresponse.GlobalResponse;
import com.policy.bazaar.globalresponse.Response;
import com.policy.bazaar.policy.model.Policies;
import com.policy.bazaar.policy.model.Purchasedpolicies;
import com.policy.bazaar.policy.response.PurchasedPoliciesResponse;
import com.policy.bazaar.repository.ClaimsRepository;
import com.policy.bazaar.repository.CustomerRepository;
import com.policy.bazaar.repository.EmployeePasswordRepository;
import com.policy.bazaar.repository.EmployeeRepository;
import com.policy.bazaar.repository.PoliciesRepository;
import com.policy.bazaar.repository.PurchasedPoliciesRepository;
import com.policy.bazaar.security.JWTEmployeeToken;

@Service
@Transactional
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
	SendEmail email;

	@Autowired
	PoliciesRepository policyRepository;
	
	@Autowired
	ClaimsRepository claimsRepository; 

	public GlobalResponse createEmployee(@Valid EmployeeCreateRequest empDetails) {
		Employees emp = new Employees();
		GlobalResponse response = new GlobalResponse();
		EmployeeCreateResponse emp1 = new EmployeeCreateResponse();
		Employeepasswords emppass = new Employeepasswords();
		UUID uuid = UUID.randomUUID();
		Employees findByEmail = empRepository.findByEmail(empDetails.getEmail());
		try {
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
				email.sendEmail(empDetails.getEmail(), emppass.getUuid());
				response.setData(emp1);
				response.setStatus(true);
				response.setMessage("Successfully Registered");

			}
		} catch (Exception e) {
			throw new PolicyBazaarServiceException("unknown error occured:", e);
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
			Byte empUserType = findByEmail.getUsertype();

			Response response1 = new Response();
			response1.setAuth(true);
			response1.setUserType(empUserType);
			if (password.equals(userPassword)) {
				response.setData(JWTEmployeeToken.createJWT(2000000L, findByEmail));
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

	public GlobalResponse getEmployees(Short page) {

		Page<Employees> employees = empRepository
				.findAll(PageRequest.of(page - 1, 10, Sort.by("createdon").descending()));

		List<EmployeeResponse> employeeResponse = new ArrayList<EmployeeResponse>();
		GlobalResponse globalResponse = new GlobalResponse();

		employees.stream().forEach((i) -> {

			EmployeeResponse empResponse = new EmployeeResponse();
			empResponse.setFullname(i.getFullname());
			empResponse.setEmail(i.getEmail());
			empResponse.setMobile(i.getMobile());
			empResponse.setUserType(i.getUsertype());
			employeeResponse.add(empResponse);
			globalResponse.setData(employeeResponse);
			globalResponse.setStatus(true);
			globalResponse.setMessage("Authorized!!!!");

		});

		return globalResponse;
	}

	public GlobalResponse getCustomers(Short page) {

		Page<Customers> customers = customerRepository
				.findAll(PageRequest.of(page - 1, 10, Sort.by("createdon").descending()));
		GlobalResponse globalResponse = new GlobalResponse();
		List<CustomerProfileResponse> customerProfileResponses = new ArrayList<CustomerProfileResponse>();

		customers.stream().forEach(customer -> {

			CustomerProfileResponse customerResponse = new CustomerProfileResponse();
			customerResponse.setFirstname(customer.getFirstname());
			customerResponse.setLastname(customer.getLastname());
			customerResponse.setEmail(customer.getEmail());
			customerResponse.setMobile(customer.getMobile());
			customerProfileResponses.add(customerResponse);

		});

		globalResponse.setData(customerProfileResponses);
		globalResponse.setStatus(true);
		globalResponse.setMessage("Authorized!!!!");

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

			String encryptedOldPassword = AES256Encryption
					.encrypt(empPasswordRequest.getOldPassword(), AES256Encryption.secretKey).intern();

			String a = emp.getPassword().intern();

			if (a == encryptedOldPassword) {
				String encryptedNewPassword = AES256Encryption.encrypt(empPasswordRequest.getNewPassword(),
						AES256Encryption.secretKey);

				emp.setPassword(encryptedNewPassword);
				emp.setLastupdatedon(new Date());
				empRepository.save(emp);
				globalResponse.setData(null);
				globalResponse.setMessage("Password Change Successfully!!!");
				globalResponse.setStatus(true);

			} else {

				globalResponse.setData(null);
				globalResponse.setMessage("You have entered a wrong password");
				globalResponse.setStatus(false);
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

	public GlobalResponse viewAllPurchased(Short page) {

		GlobalResponse globalResponse = new GlobalResponse();

		Page<Purchasedpolicies> purchasedpolicies = purchasedPoliciesRepository
				.findAll(PageRequest.of(page - 1, 10, Sort.by("createdon").descending()));
		List<ViewPurchasedPoliciesResponse> viewPurchasedPoliciesResponses = new ArrayList<ViewPurchasedPoliciesResponse>();

		purchasedpolicies.stream().forEach((i) -> {
			ViewPurchasedPoliciesResponse viewPurchasedPoliciesResponse = new ViewPurchasedPoliciesResponse();

			Optional<Customers> customers = customerRepository.findById(i.getCid());
			Optional<Policies> policies = policyRepository.findById(i.getPid());

			Customers customer = customers.get();
			Policies policy = policies.get();

			viewPurchasedPoliciesResponse.setCustomerFullName(customer.getFirstname());
			viewPurchasedPoliciesResponse.setCustomerLastName(customer.getLastname());
			viewPurchasedPoliciesResponse.setAmount(policy.getAmount());
			viewPurchasedPoliciesResponse.setPolicyName(policy.getPolicyname());
			viewPurchasedPoliciesResponse.setPurchasedid(i.getPurchasedid());

			viewPurchasedPoliciesResponses.add(viewPurchasedPoliciesResponse);

		});

		globalResponse.setData(viewPurchasedPoliciesResponses);
		globalResponse.setStatus(true);
		globalResponse.setMessage("All Purchased Policies");

		return globalResponse;
	}

	public GlobalResponse updateStatus(updateStatusRequest updateRequest) {

		GlobalResponse globalResponse = new GlobalResponse();

		Optional<Purchasedpolicies> purchasedpolicies = purchasedPoliciesRepository
				.findById(updateRequest.getPurchasedid());

		Purchasedpolicies purchasedpolicy = purchasedpolicies.get();

		purchasedpolicy.setStatus(updateRequest.getStatus());
		purchasedPoliciesRepository.save(purchasedpolicy);

		globalResponse.setData(null);
		globalResponse.setStatus(true);
		globalResponse.setMessage("Status Changed!!!!");

		return globalResponse;
	}

	public GlobalResponse allPurchasedPolicies() {

		GlobalResponse globalResponse = new GlobalResponse();

		List<Purchasedpolicies> purchasedpolicies = purchasedPoliciesRepository.findAll();

		List<PurchasedPoliciesResponse> purchasedPoliciesResponsesList = new ArrayList<PurchasedPoliciesResponse>();

		purchasedpolicies.stream().forEach((purchasedpolicy) -> {

			PurchasedPoliciesResponse purchasedPoliciesResponse = new PurchasedPoliciesResponse();
			Customers customer = customerRepository.findById(purchasedpolicy.getCid()).get();
			Policies policy = policyRepository.findById(purchasedpolicy.getPid()).get();
			purchasedPoliciesResponse.setCustomerfirstname(customer.getFirstname());
			purchasedPoliciesResponse.setCustomerlastname(customer.getLastname());
			purchasedPoliciesResponse.setPolicyname(policy.getPolicyname());
			purchasedPoliciesResponse.setStartdate(purchasedpolicy.getStartdate());
			purchasedPoliciesResponse.setAmount(policy.getAmount());
			purchasedPoliciesResponse.setStatus(purchasedpolicy.getStatus());
			purchasedPoliciesResponse.setPurchasedId(purchasedpolicy.getPurchasedid());
			purchasedPoliciesResponsesList.add(purchasedPoliciesResponse);
		});

		globalResponse.setData(purchasedPoliciesResponsesList);
		globalResponse.setMessage("All Purchased Policies!!!!!");
		globalResponse.setStatus(true);

		return globalResponse;
	}

	public GlobalResponse getAllCount() {

		GlobalResponse globalResponse = new GlobalResponse();
		List<Purchasedpolicies> purchasedpolicies = purchasedPoliciesRepository.findAll();

		Long totalPolicyCount = policyRepository.count();
		Long totalCustomerCount = customerRepository.count();
		Long totalPurchases = purchasedPoliciesRepository.count();
		Long totalEmployeesCount = empRepository.count();
		Long totalClaimsCount = claimsRepository.count();
		Long totalApprovedPolicies = purchasedpolicies.stream()
				.filter(purchasedPolicy -> purchasedPolicy.getStatus() == 1).count();
		Long totalRejectedPolicies = purchasedpolicies.stream()
				.filter(purchasedPolicy -> purchasedPolicy.getStatus() == 2).count();
		System.out.println("Total Approved Policies ---->>> "+totalApprovedPolicies + "Total Rejected Policies------>>>>" +totalRejectedPolicies);

		AllCountResponse allCountResponse = new AllCountResponse();
        
		allCountResponse.setTotalEmployeesCount(totalEmployeesCount);
		allCountResponse.setTotalClaimCount(totalClaimsCount);
		allCountResponse.setTotalCustomerCount(totalCustomerCount);
		allCountResponse.setTotalPolicyCount(totalPolicyCount);
		allCountResponse.setTotalApprovedPolicies(totalApprovedPolicies);
		allCountResponse.setTotalPurchases(totalPurchases);
		allCountResponse.setTotalRejectedPolicies(totalRejectedPolicies);

		globalResponse.setData(allCountResponse);
		globalResponse.setMessage("All Count");
		globalResponse.setStatus(true);
		return globalResponse;
	}

}
