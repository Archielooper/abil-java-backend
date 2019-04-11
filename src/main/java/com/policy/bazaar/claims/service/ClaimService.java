package com.policy.bazaar.claims.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.bazaar.claims.model.Claims;
import com.policy.bazaar.claims.request.AddClaimRequest;
import com.policy.bazaar.claims.request.ChangeClaimRequest;
import com.policy.bazaar.claims.response.ViewPoliciesResponse;
import com.policy.bazaar.customers.model.Customers;
import com.policy.bazaar.globalresponse.GlobalResponse;
import com.policy.bazaar.policy.model.Policies;
import com.policy.bazaar.repository.ClaimsRepository;
import com.policy.bazaar.repository.CustomerRepository;
import com.policy.bazaar.repository.PoliciesRepository;

@Service
public class ClaimService {

	@Autowired
	ClaimsRepository claimsRepository;

	@Autowired
	PoliciesRepository policyRepository;

	@Autowired
	CustomerRepository customerRepository;

	public GlobalResponse addClaims(AddClaimRequest addrequest) {

		GlobalResponse globalResponse = new GlobalResponse();
		Claims claims = new Claims();
		claims.setCid(addrequest.getCid());
		claims.setPid(addrequest.getPid());
		claims.setAmount(addrequest.getAmount());
		claims.setEmpid(addrequest.getEmpid());
		claims.setStatus(1);
		claims.setStartdate(new Date());
		claims.setLastupdatedon(new Date());

		claimsRepository.save(claims);

		globalResponse.setData(null);
		globalResponse.setMessage("Claim Added!!!");
		globalResponse.setStatus(true);

		return globalResponse;
	}

	public GlobalResponse updateClaims(ChangeClaimRequest changerequest) {

		GlobalResponse globalResponse = new GlobalResponse();
		Optional<Claims> claims = claimsRepository.findById(changerequest.getClaimid());
		Claims claim = claims.get();
		if (claim.getStatus() != 1) {

			globalResponse.setData(null);
			globalResponse.setMessage("Not Allowed!!!");
			globalResponse.setStatus(false);

		} else {

			claim.setStatus(changerequest.getStatus());
			claimsRepository.save(claim);
			globalResponse.setData(null);
			globalResponse.setMessage("Status Changed!!!");
			globalResponse.setStatus(true);
		}

		return globalResponse;
	}

	public GlobalResponse viewAllClaims() {

		GlobalResponse globalResponse = new GlobalResponse();
		List<ViewPoliciesResponse> listvPoliciesResponses = new ArrayList<ViewPoliciesResponse>(); 

		List<Claims> claims = claimsRepository.findAll();

		claims.stream().forEach((i) -> {

			ViewPoliciesResponse viewPoliciesResponse = new ViewPoliciesResponse();

			Optional<Customers> customers = customerRepository.findById(i.getCid());
			Optional<Policies> policies = policyRepository.findById(i.getPid());

			Customers customer = customers.get();
			Policies policy = policies.get();

			viewPoliciesResponse.setCustomerFirstName(customer.getFirstname());
			viewPoliciesResponse.setCustomerLastName(customer.getLastname());
			viewPoliciesResponse.setPolicyName(policy.getPolicyname());
			viewPoliciesResponse.setClaimid(i.getClaimid());
			viewPoliciesResponse.setAmount(i.getAmount());
			listvPoliciesResponses.add(viewPoliciesResponse);
        
		});
		
		globalResponse.setData(listvPoliciesResponses);
        globalResponse.setMessage("All claims!!!");
        globalResponse.setStatus(true);
		return globalResponse;
	}

}
