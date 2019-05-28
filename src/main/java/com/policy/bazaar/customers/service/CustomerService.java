package com.policy.bazaar.customers.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.policy.bazaar.claims.model.Claims;
import com.policy.bazaar.common.exception.PolicyBazaarServiceException;
import com.policy.bazaar.communication.MessageService;
import com.policy.bazaar.customers.model.Customers;
import com.policy.bazaar.customers.request.AddNewPolicyRequest;
import com.policy.bazaar.customers.request.CustomerSignInRequest;
import com.policy.bazaar.customers.request.CustomerSignUpRequest;
import com.policy.bazaar.customers.request.UpdateCustomerPasswordRequest;
import com.policy.bazaar.customers.request.UpdateCustomerProfileRequest;
import com.policy.bazaar.customers.response.CustomerNewPoliciesResponse;
import com.policy.bazaar.customers.response.CustomerProfileResponse;
import com.policy.bazaar.customers.response.CustomerPurchasedPoliciesResponse;
import com.policy.bazaar.customers.response.GetAllCountResponse;
import com.policy.bazaar.encryption.AES256Encryption;
import com.policy.bazaar.globalresponse.GlobalPaginationResponse;
import com.policy.bazaar.globalresponse.GlobalResponse;
import com.policy.bazaar.policy.model.Policies;
import com.policy.bazaar.policy.model.Purchasedpolicies;
import com.policy.bazaar.repository.ClaimsRepository;
import com.policy.bazaar.repository.CustomerRepository;
import com.policy.bazaar.repository.PoliciesRepository;
import com.policy.bazaar.repository.PurchasedPoliciesRepository;
import com.policy.bazaar.security.JwtCustomerToken;

@Service
@Transactional
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PurchasedPoliciesRepository purchasedPoliciesRepository;

	@Autowired
	PoliciesRepository policyRepository;

	@Autowired
	MessageService messageService;

	@Autowired
	ClaimsRepository claimsRepository;

	@Autowired
	GlobalPaginationResponse globalPaginationResponse;

	private static String UPLOADED_FOLDER = "/home/archit/Archit/Back End Projects/PolicyApp/src/main/webapp/images/images";

	public GlobalResponse signup(CustomerSignUpRequest customerRequest) {

		Customers customers = new Customers();
		GlobalResponse response = new GlobalResponse();
		Customers findByEmail = customerRepository.findByEmail(customerRequest.getEmail());
		try {
			if (findByEmail == null) {
				String encryptString = AES256Encryption.encrypt(customerRequest.getPassword(),
						AES256Encryption.secretKey);
				customers.setFirstname(customerRequest.getFirstname());
				customers.setLastname(customerRequest.getLastname());
				customers.setEmail(customerRequest.getEmail());
				customers.setMobile(customerRequest.getMobile());
				customers.setPassword(encryptString);
				customers.setLastupdatedon(new Date());
				customers.setCreatedon(new Date());
				customers.setStatus(1);
				customerRepository.save(customers);

				messageService.sendSms(customers);

				response.setData(null);
				response.setMessage("Successfully Registered");
				response.setStatus(true);
			} else {

				response.setData(null);
				response.setMessage("Customer with the email-" + customerRequest.getEmail() + "already registered!!!!");
				response.setStatus(false);

			}
		} catch (Exception e) {

			throw new PolicyBazaarServiceException("Unknown Error", e);
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
				response.setData(JwtCustomerToken.createJWT(10000000L, customer));
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

			globalResponse.setData(null);
			globalResponse.setMessage("Customer with the id-" + cid + " not found!!!!");
			globalResponse.setStatus(false);
		} else {

			Customers custom = customer.get();
			customerResponse.setFirstname(custom.getFirstname());
			customerResponse.setLastname(custom.getLastname());
			customerResponse.setMobile(custom.getMobile());
			customerResponse.setEmail(custom.getEmail());
			customerResponse.setImageurl(custom.getImageurl());

			if (custom.getAddress().equals(null)) {
				customerResponse.setAddress("Please enter your complete address...");
			} else {
				customerResponse.setAddress(custom.getAddress());
			}
			globalResponse.setData(customerResponse);
			globalResponse.setStatus(true);
			globalResponse.setMessage("User Found!!!!");

		}
		return globalResponse;
	}

	public GlobalResponse getPurchasedPolicies(Integer cid, Short page) {

		GlobalResponse globalResponse = new GlobalResponse();

		Optional<Customers> customer = customerRepository.findById(cid);
		if (!customer.isPresent()) {

			globalResponse.setData(null);
			globalResponse.setMessage("Customer with the id-" + cid + " not found!!!!");
			globalResponse.setStatus(false);

		} else {

			Page<Purchasedpolicies> purchasedpolicies = purchasedPoliciesRepository.findByCid(cid,
					PageRequest.of(page - 1, 10, Sort.by("createdon").descending()));
			List<CustomerPurchasedPoliciesResponse> custPurchasedPolicies = new ArrayList<CustomerPurchasedPoliciesResponse>();

			long count = purchasedpolicies.stream().count();

			purchasedpolicies.stream().forEach(i -> {

				CustomerPurchasedPoliciesResponse customerPurchasedPolicies = new CustomerPurchasedPoliciesResponse();
				Optional<Policies> policies = policyRepository.findById(i.getPid());
				Policies policy = policies.get();

				customerPurchasedPolicies.setPolicyname(policy.getPolicyname());
				customerPurchasedPolicies.setStartdate(i.getStartdate());
				customerPurchasedPolicies.setAmount(policy.getAmount());
				customerPurchasedPolicies.setPid(policy.getPid());
				customerPurchasedPolicies.setStatus(i.getStatus());

				custPurchasedPolicies.add(customerPurchasedPolicies);

			});
			globalPaginationResponse.setList(custPurchasedPolicies);
			globalPaginationResponse.setCount(count);

			globalResponse.setData(globalPaginationResponse);
			globalResponse.setStatus(true);
			globalResponse.setMessage("Purchased Policies");

		}
		return globalResponse;
	}

	public GlobalResponse getNewPolicies(Integer cid, Short page) {
		Optional<Customers> customers = customerRepository.findById(cid);
		GlobalResponse globalResponse = new GlobalResponse();
		if (!customers.isPresent()) {
			globalResponse.setData(null);
			globalResponse.setMessage("Customer with the id-" + cid + " not found!!!!");
			globalResponse.setStatus(false);
		} else {
			List<Policies> policies = policyRepository.findAll();
			Page<Purchasedpolicies> purchasedpolicies = purchasedPoliciesRepository.findByCid(cid,
					PageRequest.of(page - 1, 10, Sort.by("createdon").descending()));
			long count = purchasedpolicies.stream().count();
			List<Integer> listOfpurchasedpoliciesPids = new ArrayList<>();
			purchasedpolicies.forEach(purchasedpolicy -> {

				listOfpurchasedpoliciesPids.add(purchasedpolicy.getPid());

			});

			List<CustomerNewPoliciesResponse> customerNewPoliciesResponselist = new ArrayList<CustomerNewPoliciesResponse>();
			policies.stream().forEach(policy -> {
				if (!listOfpurchasedpoliciesPids.contains(policy.getPid())) {

					CustomerNewPoliciesResponse customerNewPoliciesResponse = new CustomerNewPoliciesResponse();
					customerNewPoliciesResponse.setPid(policy.getPid());
					customerNewPoliciesResponse.setPolicyname(policy.getPolicyname());
					customerNewPoliciesResponse.setDescription(policy.getDescription());
					customerNewPoliciesResponse.setAmount(policy.getAmount());
					customerNewPoliciesResponselist.add(customerNewPoliciesResponse);

				}
			});
			globalPaginationResponse.setList(customerNewPoliciesResponselist);
			globalPaginationResponse.setCount(count);
			globalResponse.setData(globalPaginationResponse);
			globalResponse.setMessage("New Policies to purchase!!!!");
			globalResponse.setStatus(true);
		}
		return globalResponse;

	}

	public GlobalResponse addNewPolicies(AddNewPolicyRequest request) {

		GlobalResponse globalResponse = new GlobalResponse();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);

		Purchasedpolicies purchasedpolicies = new Purchasedpolicies();
		purchasedpolicies.setPid(request.getPid());
		purchasedpolicies.setCid(request.getCid());
		purchasedpolicies.setStatus(0);
		purchasedpolicies.setStartdate(new Date());
		purchasedpolicies.setEnddate(cal.getTime());
		purchasedpolicies.setCreatedon(new Date());

		purchasedPoliciesRepository.save(purchasedpolicies);

		globalResponse.setData(null);
		globalResponse.setStatus(true);
		globalResponse.setMessage("Policy Added!!!");
		return globalResponse;
	}

	public GlobalResponse getClaimsCount(Integer cid) {
		GlobalResponse globalResponse = new GlobalResponse();
		List<Claims> claims = claimsRepository.findByCid(cid);
		List<Purchasedpolicies> purchasedpolicies = purchasedPoliciesRepository.findByCid(cid);
		long totalPoliciesCount = purchasedpolicies.stream().count();
		long totalClaimsCount = claims.stream().count();

		long approvedClaimscount = claims.stream().filter(claim -> claim.getStatus() == 1).count();
		long approvedPoliciesCount = purchasedpolicies.stream()
				.filter(purchasedpolicy -> purchasedpolicy.getStatus() == 1 || purchasedpolicy.getStatus() == 3)
				.count();

		GetAllCountResponse getClaimsCountResponse = new GetAllCountResponse();

		getClaimsCountResponse.setApprovedPolicesCount(approvedPoliciesCount);
		getClaimsCountResponse.setTotalPoliciesCount(totalPoliciesCount);
		getClaimsCountResponse.setTotalClaimsCount(totalClaimsCount);
		getClaimsCountResponse.setApprovedClaimsCount(approvedClaimscount);
		globalResponse.setData(getClaimsCountResponse);
		globalResponse.setMessage("My Total count!!!");
		globalResponse.setStatus(true);
		return globalResponse;
	}

	public GlobalResponse updateCustomerPassword(Integer cid, UpdateCustomerPasswordRequest updatePasswordRequest) {

		GlobalResponse globalResponse = new GlobalResponse();
		Customers customer = customerRepository.findByCid(cid);

		if (customer == null) {
			globalResponse.setData(null);
			globalResponse.setMessage("Not Found!!!!!");
			globalResponse.setStatus(false);
		} else {
			String encryptedOldPassword = AES256Encryption
					.encrypt(updatePasswordRequest.getOldPassword(), AES256Encryption.secretKey).intern();

			String password = customer.getPassword().intern();
			if (password == encryptedOldPassword) {
				String encryptedNewPassword = AES256Encryption.encrypt(updatePasswordRequest.getNewPassword(),
						AES256Encryption.secretKey);

				customer.setPassword(encryptedNewPassword);
				customer.setLastupdatedon(new Date());
				customerRepository.save(customer);
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

	public GlobalResponse updateCustomerProfile(Integer cid, UpdateCustomerProfileRequest updateProfileRequest) {

		GlobalResponse globalResponse = new GlobalResponse();
		Customers customer = customerRepository.findByCid(cid);

		if (customer == null) {
			globalResponse.setData(null);
			globalResponse.setMessage("Not Found!!!!!");
			globalResponse.setStatus(false);
		} else {
			customer.setFirstname(updateProfileRequest.getFirstname());
			customer.setLastname(updateProfileRequest.getLastname());
			customer.setMobile(updateProfileRequest.getMobile());
			customer.setAddress(updateProfileRequest.getAddress());
			customerRepository.save(customer);
			globalResponse.setData(null);
			globalResponse.setMessage("Profile Updated Successfully!!!!!!!");
			globalResponse.setStatus(true);
		}

		return globalResponse;
	}

	public GlobalResponse uploadImage(MultipartFile file, Integer cid) throws IOException {
		GlobalResponse globalResponse = new GlobalResponse();

		File convertFile = new File(UPLOADED_FOLDER + file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();

		Customers customer = customerRepository.findById(cid).get();

		customer.setImageurl("images" + file.getOriginalFilename());

		customerRepository.save(customer);
		globalResponse.setData(null);
		globalResponse.setMessage("Image Uploaded Successfully");
		globalResponse.setStatus(true);

		return globalResponse;
	}

}