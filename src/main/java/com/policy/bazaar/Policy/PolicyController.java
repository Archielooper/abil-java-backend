package com.policy.bazaar.Policy;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policy.bazaar.globalresponse.GlobalResponse;

@RestController
@RequestMapping("/policy")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PolicyController {
	
	@Autowired
	PolicyService policyService; 
	
	@PostMapping("/createPolicy")
	public GlobalResponse createPolicy(@Valid @RequestBody CreatePolicyRequest policyDetails) {

		return policyService.createPolicy(policyDetails);
	}
	
	@GetMapping("/getPolicies")
	public GlobalResponse getPolicies() {
		return policyService.getPolicies();

	}

}
