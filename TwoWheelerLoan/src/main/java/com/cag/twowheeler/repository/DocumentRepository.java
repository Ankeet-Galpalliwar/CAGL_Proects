package com.cag.twowheeler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cag.twowheeler.entity.Documents;
import com.cag.twowheeler.entity.MainDealer;
@Repository
public interface DocumentRepository extends JpaRepository<Documents,String>{

	@Query(value = "SELECT * FROM two_wheeler_lone_database.documents", nativeQuery = true)
	public List<Documents> getalldocument();
	
	//get existing Document type method 
	public List<Documents> findByMainDealer(MainDealer dealer);

}
