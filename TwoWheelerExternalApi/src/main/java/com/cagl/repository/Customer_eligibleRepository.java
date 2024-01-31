package com.cagl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cagl.entity.customer_eligible;

//import com.cagl.entity.customer_eligible;


@Repository
public interface Customer_eligibleRepository extends JpaRepository<customer_eligible,Integer>{
	
	@Query(value = "SELECT * FROM customer_eligible where kendra_id= :KendraID", nativeQuery = true)
	List<customer_eligible> getCustomerByKendraID(@Param("KendraID")String KendraID);

	

}
