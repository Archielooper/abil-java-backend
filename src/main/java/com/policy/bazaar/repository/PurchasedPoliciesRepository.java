package com.policy.bazaar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.policy.bazaar.Policy.Purchasedpolicies;

@Repository
public interface PurchasedPoliciesRepository extends JpaRepository<Purchasedpolicies, Integer> {

	List<Purchasedpolicies> findByCid(Integer cid);

}
