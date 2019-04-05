package com.policy.bazaar.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.policy.bazaar.Employee.Employeepasswords;

@Repository
public interface EmployeePasswordRepository extends JpaRepository<Employeepasswords, Integer> 

{

	 Optional<Employeepasswords> findByUuid(UUID uuid);
   
}
