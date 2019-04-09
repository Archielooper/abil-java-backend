package com.policy.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.policy.bazaar.policy.Policies;

@Repository
public interface PoliciesRepository extends JpaRepository<Policies, Integer>
{

}
