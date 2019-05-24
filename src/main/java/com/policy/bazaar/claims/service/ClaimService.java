package com.policy.bazaar.claims.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.policy.bazaar.claims.model.Claims;
import com.policy.bazaar.claims.request.AddClaimRequest;
import com.policy.bazaar.claims.request.ChangeClaimRequest;
import com.policy.bazaar.claims.response.ClaimsStatusResponse;
import com.policy.bazaar.claims.response.ViewPoliciesResponse;
import com.policy.bazaar.customers.model.Customers;
import com.policy.bazaar.globalresponse.GlobalPaginationResponse;
import com.policy.bazaar.globalresponse.GlobalResponse;
import com.policy.bazaar.policy.model.Policies;
import com.policy.bazaar.policy.model.Purchasedpolicies;
import com.policy.bazaar.repository.ClaimsRepository;
import com.policy.bazaar.repository.CustomerRepository;
import com.policy.bazaar.repository.PoliciesRepository;
import com.policy.bazaar.repository.PurchasedPoliciesRepository;

@Service
public class ClaimService {

	@Autowired
	ClaimsRepository claimsRepository;

	@Autowired
	PoliciesRepository policyRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PurchasedPoliciesRepository purchasedPolicies;

	@Autowired
	GlobalPaginationResponse globalPaginationResponse;

	public GlobalResponse addClaims(AddClaimRequest addrequest) {

		GlobalResponse globalResponse = new GlobalResponse();
		Claims claims = new Claims();
		claims.setCid(addrequest.getCid());
		claims.setPid(addrequest.getPid());
		claims.setAmount(addrequest.getAmount());
		claims.setStatus(0);
		claims.setStartdate(new Date());
		claims.setLastupdatedon(new Date());

		Purchasedpolicies policy = purchasedPolicies.findByPid(addrequest.getPid());

		policy.setStatus(3);
		purchasedPolicies.save(policy);
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

		claim.setStatus(changerequest.getStatus());
		claim.setLastupdatedon(new Date());
		claimsRepository.save(claim);
		globalResponse.setData(null);
		globalResponse.setMessage("Status Changed!!!");
		globalResponse.setStatus(true);

		return globalResponse;
	}

	public GlobalResponse viewAllClaims(Short page) {
		Page<Claims> claims = claimsRepository.findAll(PageRequest.of(page - 1, 10, Sort.by("claimid").descending()));
        long count = claims.stream().count();
		GlobalResponse globalResponse = new GlobalResponse();
		List<ViewPoliciesResponse> listvPoliciesResponses = new ArrayList<ViewPoliciesResponse>();

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
			viewPoliciesResponse.setStatus(i.getStatus());
			listvPoliciesResponses.add(viewPoliciesResponse);

		});
        
		globalPaginationResponse.setList(listvPoliciesResponses);
		globalPaginationResponse.setCount(count);
		
		globalResponse.setData(globalPaginationResponse);
		globalResponse.setMessage("All claims!!!");
		globalResponse.setStatus(true);
		return globalResponse;
	}

	public GlobalResponse getClaimById(Integer cid, Short page) {

		Page<Claims> claims = claimsRepository.findByCid(cid,
				PageRequest.of(page - 1, 10, Sort.by("claimid").descending()));
		long count = claims.stream().count();
		GlobalResponse globalResponse = new GlobalResponse();
		List<ClaimsStatusResponse> claimsStatusList = new ArrayList<ClaimsStatusResponse>();

		claims.stream().forEach((claim) -> {
			ClaimsStatusResponse claimsStatusResponse = new ClaimsStatusResponse();
			Policies policy = policyRepository.findById(claim.getPid()).get();
			claimsStatusResponse.setClaimid(claim.getClaimid());
			claimsStatusResponse.setPolicyname(policy.getPolicyname());
			claimsStatusResponse.setAmount(claim.getAmount());
			claimsStatusResponse.setAdddate(claim.getStartdate());
			claimsStatusResponse.setStatus(claim.getStatus());

			claimsStatusList.add(claimsStatusResponse);
		});

		globalPaginationResponse.setList(claimsStatusList);
		globalPaginationResponse.setCount(count);
		globalResponse.setData(globalPaginationResponse);
		globalResponse.setMessage("My claims!!!");
		globalResponse.setStatus(true);
		return globalResponse;

	}

}
