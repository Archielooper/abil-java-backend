package com.policy.bazaar.customers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.bazaar.Policy.Policies;
import com.policy.bazaar.Policy.Purchasedpolicies;
import com.policy.bazaar.Security.JwtToken;
import com.policy.bazaar.encryption.AES256Encryption;
import com.policy.bazaar.exceptions.NotFoundException;
import com.policy.bazaar.globalresponse.GlobalResponse;
import com.policy.bazaar.repository.CustomerRepository;
import com.policy.bazaar.repository.PoliciesRepository;
import com.policy.bazaar.repository.PurchasedPoliciesRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PurchasedPoliciesRepository purchasedPoliciesRepository;

	@Autowired
	PoliciesRepository policyRepository;

	public GlobalResponse signup(CustomerSignUpRequest customerRequest) {

		Customers customers = new Customers();
		GlobalResponse response = new GlobalResponse();
		Customers findByEmail = customerRepository.findByEmail(customerRequest.getEmail());

		if (findByEmail == null) {
			String encryptString = AES256Encryption.encrypt(customerRequest.getPassword(), AES256Encryption.secretKey);
			customers.setFirstname(customerRequest.getFirstname());
			customers.setLastname(customerRequest.getLastname());
			customers.setEmail(customerRequest.getEmail());
			customers.setMobile(customerRequest.getMobile());
			customers.setPassword(encryptString);
			customers.setLastupdatedon(new Date());
			customers.setCreatedon(new Date());
			customers.setStatus(1);
			customerRepository.save(customers);

			response.setData(null);
			response.setMessage("Successfully Registered");
			response.setStatus(true);
		} else {

			throw new NotFoundException(
					"Customer with the email-" + customerRequest.getEmail() + " already registerd!!!");
		}

		return response;
	}

	public GlobalResponse signin(CustomerSignInRequest customerSignInRequest) {

		Customers customer = customerRepository.findByEmail(customerSignInRequest.getEmail());

		GlobalResponse response = new GlobalResponse();
		if (customer == null) {
			response.setData(null);
			response.setStatus(false);
			response.setMessage("Email or Password not valid");
		} else {

			String password = customer.getPassword();
			String userPassword = AES256Encryption.encrypt(customerSignInRequest.getPassword(),
					AES256Encryption.secretKey);
			if (password.equals(userPassword)) {
				response.setData(JwtToken.createJWT(100000000000L, customer));
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

	public GlobalResponse getProfile(Integer cid) {

		Optional<Customers> customer = customerRepository.findById(cid);
		CustomerProfileResponse customerResponse = new CustomerProfileResponse();
		GlobalResponse globalResponse = new GlobalResponse();

		if (!customer.isPresent()) {

			throw new NotFoundException("Customer with the id" + cid + " not found!!!!");
		} else {
			Customers custom = customer.get();
			customerResponse.setFirstname(custom.getFirstname());
			customerResponse.setLastname(custom.getLastname());
			customerResponse.setMobile(custom.getMobile());
			customerResponse.setEmail(custom.getEmail());
			globalResponse.setData(customerResponse);
			globalResponse.setStatus(true);
			globalResponse.setMessage("User Found!!!!");

		}
		return globalResponse;
	}

	public GlobalResponse getPurchasedPolicies(Integer cid) {

		Optional<Customers> customer = customerRepository.findById(cid);
		if (!customer.isPresent()) {

			throw new NotFoundException("Customer with the id-" + cid + " not found!!!!");

		} else {

			List<Purchasedpolicies> purchasedpolicies = purchasedPoliciesRepository.findByCid(cid);
			List<CustomerPurchasedPoliciesResponse> custPurchasedPolicies = new ArrayList<CustomerPurchasedPoliciesResponse>();
			GlobalResponse globalResponse = new GlobalResponse();

			purchasedpolicies.stream().forEach(i -> {

				CustomerPurchasedPoliciesResponse customerPurchasedPolicies = new CustomerPurchasedPoliciesResponse();
				Optional<Policies> policies = policyRepository.findById(i.getPid());
				Policies policy = policies.get();

				customerPurchasedPolicies.setPolicyname(policy.getPolicyname());
				customerPurchasedPolicies.setStartdate(i.getStartdate());
				customerPurchasedPolicies.setAmount(policy.getAmount());
				customerPurchasedPolicies.setPid(policy.getPid());

				custPurchasedPolicies.add(customerPurchasedPolicies);

				globalResponse.setData(custPurchasedPolicies);
				globalResponse.setStatus(true);
				globalResponse.setMessage("Purchased Policies");
			});

			return globalResponse;
		}
	}

	public GlobalResponse getNewPolicies(Integer cid) {

		List<Policies> policies = policyRepository.findAll();
		List<Purchasedpolicies> purchasedpolicies = purchasedPoliciesRepository.findByCid(cid);
		GlobalResponse globalResponse = new GlobalResponse();

		List<Integer> listOfpurchasedpoliciesPids = new ArrayList<>();
		purchasedpolicies.forEach(purchasedpolicy -> {

			listOfpurchasedpoliciesPids.add(purchasedpolicy.getPid());

		});

		List<CustomerNewPoliciesResponse> customerNewPoliciesResponselist = new ArrayList<CustomerNewPoliciesResponse>();
		policies.stream().forEach(policy -> {
			if (!listOfpurchasedpoliciesPids.contains(policy.getPid())) {

				CustomerNewPoliciesResponse customerNewPoliciesResponse = new CustomerNewPoliciesResponse();
				customerNewPoliciesResponse.setPolicyname(policy.getPolicyname());
				customerNewPoliciesResponse.setDescription(policy.getDescription());
				customerNewPoliciesResponse.setAmount(policy.getAmount());
				customerNewPoliciesResponselist.add(customerNewPoliciesResponse);

			}
		});

		globalResponse.setData(customerNewPoliciesResponselist);
		globalResponse.setMessage("New Policies to purchase!!!!");
		globalResponse.setStatus(true);

		return globalResponse;

	}

	public GlobalResponse addNewPolicies(AddNewPolicyRequest request) {

		GlobalResponse globalResponse = new GlobalResponse();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);

		Purchasedpolicies purchasedpolicies = new Purchasedpolicies();
		purchasedpolicies.setPid(request.getPid());
		purchasedpolicies.setCid(request.getCid());
		purchasedpolicies.setStartdate(new Date());
		purchasedpolicies.setEnddate(cal.getTime());
		purchasedpolicies.setCreatedon(new Date());

		purchasedPoliciesRepository.save(purchasedpolicies);

		globalResponse.setData(null);
		globalResponse.setStatus(true);
		globalResponse.setMessage("Policy Added!!!");
		return globalResponse;
	}

}