package com.cagl.service;

import com.cagl.dto.AuditEnrollmentDto;
import com.cagl.dto.AuditTrailDto;

public interface AuditService {

	 AuditEnrollmentDto addAuditEnrollment(AuditEnrollmentDto auditEnrollmentDto);

	AuditTrailDto addAuditTrail(AuditTrailDto auditTrailDto);

	AuditEnrollmentDto editAuditEnrollment(AuditEnrollmentDto auditEnrollmentDto);

	AuditTrailDto editAuditTrail(AuditTrailDto auditTrailDto);

}
