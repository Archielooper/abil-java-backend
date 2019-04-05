package com.policy.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.policy.bazaar.Employee.Employees;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer> {

	public Employees findByEmail(String email);


}
