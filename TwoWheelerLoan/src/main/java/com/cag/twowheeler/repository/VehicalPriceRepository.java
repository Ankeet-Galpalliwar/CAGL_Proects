package com.cag.twowheeler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cag.twowheeler.entity.VehicalPrice;

@Repository
public interface VehicalPriceRepository extends JpaRepository<VehicalPrice, String> {

	@Query(value = "SELECT vehical_priceid FROM two_wheeler_loan.vehical_price WHERE oem=:oem and state=:state", nativeQuery = true)
	public List<String> getSimilarVariantpriceId(@Param("oem") String oem, @Param("state") String state);

	VehicalPrice findByVehicalPriceID(String string);

	List<VehicalPrice> findByModelAndState(String model, String state);

	List<VehicalPrice> findByStateAndOem(String state, String oem);

	List<VehicalPrice> findByVariantName(String variantNamae);
	

	@Modifying
	@Query(value = "update vehical_price set variant=:newName where variant=:oldName", nativeQuery = true)
	int modifyVariantName(@Param("newName") String newName, @Param("oldName") String oldName);

//	public List<VehicalPrice> findByStateAndOem(String state, String oem);

}
