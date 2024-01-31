package com.cag.twowheeler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cag.twowheeler.entity.MainDealer;

@Repository
public interface MainDealerRepository extends JpaRepository<MainDealer, String> {

	@Query(value = "SELECT main_dealerid FROM main_dealer WHERE main_dealerid LIKE :dealerId", nativeQuery = true)
	public List<String> getSimilarDealerId(@Param("dealerId") String dealerId);

	public List<MainDealer> findByState(String state);

	public List<MainDealer> findByDistrict(String district);
	
	public List<MainDealer> findByStateAndDistrict(String state,String district);

	@Query(value="SELECT * FROM two_wheeler_lone_database_updated.main_dealer where expiry_date = :date", nativeQuery = true)
	public List<MainDealer> getData(@Param("date") String date);

	
	//Method USe to throw Error
	
	public MainDealer findByBankAccNumber(String mainDealerBankAccNumber);

	public MainDealer findByPanNumber(String mainDealerPanNumber);

	public MainDealer findByGstNumber(String mainDealerPanNumber);
}
