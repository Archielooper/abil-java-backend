package com.policy.bazaar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.policy.bazaar.claims.model.Claims;

@Repository
public interface ClaimsRepository extends JpaRepository<Claims, Integer> {

	Page<Claims> findByCid(Integer cid, Pageable page);
	List<Claims> findByCid(Integer cid);

}
