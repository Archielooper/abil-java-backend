package com.policy.bazaar.Policy;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.bazaar.globalresponse.GlobalResponse;
import com.policy.bazaar.repository.PoliciesRepository;

@Service
public class PolicyService {
	
	@Autowired
	PoliciesRepository policyRepository;
	
	
	public GlobalResponse createPolicy(CreatePolicyRequest policyDetails) {

		Policies policies = new Policies();

		GlobalResponse response = new GlobalResponse();

		policies.setPolicyname(policyDetails.getPolicyname());
		policies.setDescription(policyDetails.getDescription());
		policies.setAmount(policyDetails.getAmount());
		policies.setCreatedon(new Date(System.currentTimeMillis()));
		policyRepository.save(policies);

		response.setData(null);
		response.setStatus(true);
		response.setMessage("Policy Added");

		return response;
	}
	
	public GlobalResponse getPolicies() {

		List<Policies> policies = policyRepository.findAll();
		List<GetPoliciesResponse> getPoList = new ArrayList<GetPoliciesResponse>();
		GlobalResponse globalResponse = new GlobalResponse();
		policies.stream().forEach((i) -> {
			

			GetPoliciesResponse getPoliciesResponse = new GetPoliciesResponse();
			getPoliciesResponse.setPid(i.getPid());
			getPoliciesResponse.setPolicyname(i.getPolicyname());
			getPoliciesResponse.setAmount(i.getAmount());
			getPoliciesResponse.setDescription(i.getDescription());
			getPoList.add(getPoliciesResponse);

			globalResponse.setData(getPoList);
			globalResponse.setStatus(true);
			globalResponse.setMessage("Authorized!!!!");

		});

		return globalResponse;
	}


}
