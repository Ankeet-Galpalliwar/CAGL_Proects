package com.cagl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cagl.Entity.DigiAgilCustomer;

@Repository
public interface TwlEligbilityRepository extends JpaRepository<DigiAgilCustomer, String> {

}
