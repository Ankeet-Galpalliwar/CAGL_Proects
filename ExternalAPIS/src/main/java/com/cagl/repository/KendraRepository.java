package com.cagl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cagl.entity.Kendra;

public interface KendraRepository extends JpaRepository<Kendra, String>{

	@Query(value = "SELECT * FROM Kendra  where branch_id= :branchID", nativeQuery = true)
	List<Kendra> getKendrasByBranchID(@Param("branchID")String branchID);

	@Query("SELECT c FROM Kendra c where c.kendraId=?1")
	Kendra getKendraId(String kendraName);
}
