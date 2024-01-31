package com.cag.twowheeler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cag.twowheeler.entity.VehicalOem;

@Repository
public interface VehicalOemRepository extends JpaRepository<VehicalOem, Integer> {
	VehicalOem findByVehicalOem(String name);

	@Query(value = "SELECT variant_id FROM two_wheeler_loan.vehical_variant where variant_id like %:contain%", nativeQuery = true)
	List<String> getSimilarVariantID(@Param("contain") String contain);

}
