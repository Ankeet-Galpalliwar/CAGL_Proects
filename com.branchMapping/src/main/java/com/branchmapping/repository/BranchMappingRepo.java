package com.branchmapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.branchmapping.entity.BranchMappingData;

@Repository
public interface BranchMappingRepo extends JpaRepository<BranchMappingData, String> {

}
