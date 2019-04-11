package com.policy.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.policy.bazaar.customers.model.Customers;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Integer>
{

	Customers findByEmail(String email);

}
