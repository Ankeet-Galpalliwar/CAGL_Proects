package com.cag.twowheeler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cag.twowheeler.entity.StateAbbreviation;

@Repository
public interface StateAbbreviationRepository extends JpaRepository<StateAbbreviation, String>{

}
