package com.policy.bazaar.claims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policy.bazaar.claims.request.AddClaimRequest;
import com.policy.bazaar.claims.request.ChangeClaimRequest;
import com.policy.bazaar.claims.service.ClaimService;
import com.policy.bazaar.globalresponse.GlobalResponse;

@RestController
@RequestMapping("/claims")
public class ClaimsController {
         
	@Autowired
	ClaimService claimservice;
	
	@PostMapping("/add")
	public GlobalResponse addClaims(@RequestBody AddClaimRequest addrequest) {
		
		return claimservice.addClaims(addrequest);
	}
	
	@PutMapping("/update")
	public GlobalResponse updateClaims(@RequestBody ChangeClaimRequest changerequest) {
		
		return claimservice.updateClaims(changerequest);
	}
	
	@GetMapping("/viewAll")
	public GlobalResponse viewAllClaims() {
		
		return claimservice.viewAllClaims();
	}
	
}
