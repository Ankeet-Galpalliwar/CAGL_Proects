package com.cagl.repositorty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cagl.entity.AuditEnrollment;

@Repository
public interface AuditEnrollmentRepository extends JpaRepository<AuditEnrollment, String> {

}
