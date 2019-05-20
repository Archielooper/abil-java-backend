package com.policy.bazaar.customers.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policy.bazaar.customers.request.AddNewPolicyRequest;
import com.policy.bazaar.customers.request.CustomerSignInRequest;
import com.policy.bazaar.customers.request.CustomerSignUpRequest;
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

	@GetMapping("/getpurchasedpolicies/{cid}")
	public GlobalResponse getPurchasedPolicies(@PathVariable Integer cid) {
		return customerService.getPurchasedPolicies(cid);

	}

	@GetMapping("/getnewpolicies/{cid}")
	public GlobalResponse getNewPolicies(@PathVariable Integer cid) {

		return customerService.getNewPolicies(cid);

	}

	@PostMapping("/addpolicies")
	public GlobalResponse addNewPolicies(@RequestBody AddNewPolicyRequest request) {
		return customerService.addNewPolicies(request);

	}

	@GetMapping("/getallcount/{cid}")
	public GlobalResponse getClaimsCount(@PathVariable Integer cid) {
		return customerService.getClaimsCount(cid);
	}

}
