package com.policy.bazaar.customers.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.policy.bazaar.customers.request.AddNewPolicyRequest;
import com.policy.bazaar.customers.request.CustomerSignInRequest;
import com.policy.bazaar.customers.request.CustomerSignUpRequest;
import com.policy.bazaar.customers.request.UpdateCustomerPasswordRequest;
import com.policy.bazaar.customers.request.UpdateCustomerProfileRequest;
import com.policy.bazaar.customers.service.CustomerService;
import com.policy.bazaar.globalresponse.GlobalResponse;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping("/signup")
	public GlobalResponse signup(@RequestBody CustomerSignUpRequest customerrequest) {

		GlobalResponse globalResponse = new GlobalResponse();

		try {
			globalResponse = customerService.signup(customerrequest);
		} catch (Exception e) {

			globalResponse.setStatus(Boolean.FALSE);
			globalResponse.setMessage(e.getMessage());
		}

		return globalResponse;

	}

	@PostMapping("/signin")
	public GlobalResponse signin(@RequestBody CustomerSignInRequest customerrequest) {

		return customerService.signin(customerrequest);
	}

	@GetMapping("/getProfile/{cid}")
	public GlobalResponse getProfile(HttpServletRequest request, @PathVariable Integer cid) {

		return customerService.getProfile(cid);

	}

	@GetMapping("/getpurchasedpolicies/{cid}/{page}")
	public GlobalResponse getPurchasedPolicies(@PathVariable Integer cid,@PathVariable Short page) {
		return customerService.getPurchasedPolicies(cid,page);

	}

	@GetMapping("/getnewpolicies/{cid}/{page}")
	public GlobalResponse getNewPolicies(@PathVariable Integer cid , @PathVariable Short page) {

		return customerService.getNewPolicies(cid,page);

	}

	@PostMapping("/addpolicies")
	public GlobalResponse addNewPolicies(@RequestBody AddNewPolicyRequest request) {
		return customerService.addNewPolicies(request);

	}

	@GetMapping("/getallcount/{cid}")
	public GlobalResponse getClaimsCount(@PathVariable Integer cid) {
		return customerService.getClaimsCount(cid);
	}
	
	@PutMapping("/updatepassword/{cid}")
	public GlobalResponse updatePassword(@PathVariable Integer cid ,@RequestBody UpdateCustomerPasswordRequest updatePasswordRequest) {
		return customerService.updateCustomerPassword(cid,updatePasswordRequest);
		
	}
	
	@PutMapping("/updatecustomerprofile/{cid}")
	public GlobalResponse updateProfile(@PathVariable Integer cid, @RequestBody UpdateCustomerProfileRequest updateProfileRequest) {
		return customerService.updateCustomerProfile(cid,updateProfileRequest);
	}
	
	@PostMapping( value ="/upload/{cid}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public GlobalResponse uploadImage(@RequestParam("image") MultipartFile file , @PathVariable Integer cid) throws IOException
	{
		return customerService.uploadImage(file , cid);
		
	}

}
