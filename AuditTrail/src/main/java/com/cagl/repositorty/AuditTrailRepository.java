package com.cagl.repositorty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cagl.entity.AuditTrail;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, String> {

}
