package com.cagl.repositorty.datasource1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cagl.entity.datasource1.LeadContactDetails;

@Repository
public interface LeadContactDetailsRepository extends JpaRepository<LeadContactDetails, Long> {

}
