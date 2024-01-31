package com.cagl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cagl.entity.Branch;

public interface branchRepository extends JpaRepository<Branch, String> {
	
	@Query(value = "SELECT * FROM branch where branch_id=:branchID",nativeQuery = true)
	Branch getAllBranch(@Param("branchID") String branchID);
}
