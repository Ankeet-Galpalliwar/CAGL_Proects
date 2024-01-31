package com.cag.twowheeler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cag.twowheeler.entity.SubDealer;

@Repository
public interface SubDealerRepository extends JpaRepository<SubDealer, String> {

	@Query(value = "SELECT sub_dealerid FROM sub_dealer WHERE sub_dealerid LIKE :dealerId", nativeQuery = true)
	public List<String> getSimilarDealerId(@Param("dealerId") String dealerId);

	@Query(value = "SELECT * FROM two_wheeler_lone_database_updated.sub_dealer where expiry_date = :date", nativeQuery = true)
	public List<SubDealer> getData(@Param("date") String date);

	//method use for Exception
	public SubDealer findByBankAccNumber(String subDealerBankAccNumber);

	public SubDealer findByPanNumber(String subDealerPanNumber);

	public SubDealer findByGstNumber(String subDealerGstNumber);
}
