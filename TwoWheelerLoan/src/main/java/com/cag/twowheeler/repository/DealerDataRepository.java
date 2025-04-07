package com.cag.twowheeler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cag.twowheeler.dto.external.DealerData;

public interface DealerDataRepository extends JpaRepository<DealerData, Long>{

}
