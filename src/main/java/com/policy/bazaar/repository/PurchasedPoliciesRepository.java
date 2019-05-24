package com.policy.bazaar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.policy.bazaar.policy.model.Purchasedpolicies;

@Repository
public interface PurchasedPoliciesRepository extends JpaRepository<Purchasedpolicies, Integer> {

	Page<Purchasedpolicies> findByCid(Integer cid, Pageable pageable);
	List<Purchasedpolicies> findByCid(Integer cid);
	List<Purchasedpolicies> findAll();
	Purchasedpolicies findByPid(Integer pid);
	
}
