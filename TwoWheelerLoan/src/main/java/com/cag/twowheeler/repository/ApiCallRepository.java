package com.cag.twowheeler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cag.twowheeler.entity.ApiCallRecords;

@Repository
public interface ApiCallRepository extends JpaRepository<ApiCallRecords, Long> {

}
