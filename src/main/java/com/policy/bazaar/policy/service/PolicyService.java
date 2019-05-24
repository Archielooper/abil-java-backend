package com.policy.bazaar.policy.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.policy.bazaar.common.exception.NotFoundException;
import com.policy.bazaar.globalresponse.GlobalPaginationResponse;
import com.policy.bazaar.globalresponse.GlobalResponse;
import com.policy.bazaar.policy.model.Policies;
import com.policy.bazaar.policy.request.CreatePolicyRequest;
import com.policy.bazaar.policy.request.UpdatePolicyRequest;
import com.policy.bazaar.policy.response.GetPoliciesResponse;
import com.policy.bazaar.repository.PoliciesRepository;

@Service
public class PolicyService {

	@Autowired
	PoliciesRepository policyRepository;
	
	@Autowired
	GlobalPaginationResponse globalPaginationResponse;

	public GlobalResponse createPolicy(CreatePolicyRequest policyDetails) {

		Policies policies = new Policies();

		GlobalResponse response = new GlobalResponse();

		policies.setPolicyname(policyDetails.getPolicyname());
		policies.setDescription(policyDetails.getDescription());
		policies.setAmount(policyDetails.getAmount());
		policies.setCreatedon(new Date(System.currentTimeMillis()));
		policies.setTenure(policyDetails.getTenure());
		policyRepository.save(policies);

		response.setData(null);
		response.setStatus(true);
		response.setMessage("Policy Added");

		return response;
	}

	public GlobalResponse getPolicies(Short page) {

		Page<Policies> policies = policyRepository.findAll(PageRequest.of(page - 1, 5, Sort.by("createdon").descending()));
		long count = policyRepository.count();
		
		List<GetPoliciesResponse> getPolicyList = new ArrayList<GetPoliciesResponse>();
		GlobalResponse globalResponse = new GlobalResponse();
		policies.stream().forEach((i) -> {

			GetPoliciesResponse getPoliciesResponse = new GetPoliciesResponse();
			getPoliciesResponse.setPid(i.getPid());
			getPoliciesResponse.setPolicyname(i.getPolicyname());
			getPoliciesResponse.setAmount(i.getAmount());
			getPoliciesResponse.setDescription(i.getDescription());
			getPoliciesResponse.setTenure(i.getTenure());
			getPolicyList.add(getPoliciesResponse);
			
			globalPaginationResponse.setList(getPolicyList);
			globalPaginationResponse.setCount(count);

			globalResponse.setData(globalPaginationResponse);
			globalResponse.setStatus(true);
			globalResponse.setMessage("Authorized!!!!");

		});

		return globalResponse;
	}

	public GlobalResponse updatePolicy(Integer pid, UpdatePolicyRequest updatePolicyRequest) {

		Optional<Policies> policies = policyRepository.findById(pid);
		GlobalResponse globalResponse = new GlobalResponse();

		if (!policies.isPresent()) {

			globalResponse.setData(null);
			globalResponse.setStatus(false);
			globalResponse.setMessage("Policy with the id- " + pid + " not found!!!");

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
