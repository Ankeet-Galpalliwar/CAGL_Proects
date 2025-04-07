package com.cagl.repositorty.datasource1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cagl.entity.datasource1.AuditEnrollment;

@Repository
public interface AuditEnrollmentRepository extends JpaRepository<AuditEnrollment, String> {

}
