package com.policy.bazaar.policy.controller;

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
import com.policy.bazaar.policy.request.CreatePolicyRequest;
import com.policy.bazaar.policy.request.UpdatePolicyRequest;
import com.policy.bazaar.policy.service.PolicyService;

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
	
	@PutMapping("/update/{pid}")
	public GlobalResponse updatePolicy(@PathVariable Integer pid, @RequestBody UpdatePolicyRequest updatePolicyRequest) {
		
		return policyService.updatePolicy(pid, updatePolicyRequest);
		
	}
	
	@DeleteMapping("/delete/{pid}")
	public GlobalResponse deletePolicy(@PathVariable Integer pid) {
		
		return policyService.deletePolicy(pid);
	}

}
