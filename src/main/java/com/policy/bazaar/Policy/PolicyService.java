package com.policy.bazaar.policy;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.bazaar.exceptions.NotFoundException;
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

	public GlobalResponse updatePolicy(Integer pid, UpdatePolicyRequest updatePolicyRequest) {

		Optional<Policies> policies = policyRepository.findById(pid);
		GlobalResponse globalResponse = new GlobalResponse();

		if (!policies.isPresent()) {

			throw new NotFoundException("Policy with the id-" + pid + " not found!!!");

		} else {

			Policies policy = policies.get();
			policy.setPolicyname(updatePolicyRequest.getPolicyname());
			policy.setDescription(updatePolicyRequest.getDescription());
			policy.setAmount(updatePolicyRequest.getAmount());
			policy.setLastupdatedon(new Date(System.currentTimeMillis()));
			policyRepository.save(policy);

			globalResponse.setData(null);
			globalResponse.setStatus(true);
			globalResponse.setMessage("Policy Updated Successfully!!!!");

		}

		return globalResponse;
	}

	public GlobalResponse deletePolicy(Integer pid) {

		Optional<Policies> policies = policyRepository.findById(pid);
		GlobalResponse globalResponse = new GlobalResponse();

		if (!policies.isPresent()) {

			throw new NotFoundException("Policy with the id-" + pid + " not found!!!");

		} else {

			policyRepository.deleteById(pid);
			globalResponse.setData(null);
			globalResponse.setStatus(true);
			globalResponse.setMessage("Policy Deleted Successfully!!!!");

		}

		return globalResponse;
	}

}
