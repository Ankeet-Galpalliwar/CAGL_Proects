package com.cagl.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cagl.dto.AuditEnrollmentDto;
import com.cagl.dto.AuditTrailDto;
import com.cagl.entity.datasource1.AuditEnrollment;
import com.cagl.entity.datasource1.AuditTrail;
import com.cagl.repositorty.datasource1.AuditEnrollmentRepository;
import com.cagl.repositorty.datasource1.AuditTrailRepository;
import com.cagl.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

	private final AuditEnrollmentRepository auditEnrollmentRepository;
	private final AuditTrailRepository auditTrailRepository;

	@Autowired
	public AuditServiceImpl(AuditEnrollmentRepository auditEnrollmentRepository,
			AuditTrailRepository auditTrailRepository) {
		this.auditEnrollmentRepository = auditEnrollmentRepository;
		this.auditTrailRepository = auditTrailRepository;
	}

	public AuditServiceImpl() {
		super();
		this.auditEnrollmentRepository = null;
		this.auditTrailRepository = null;
	}

	@Override
	public AuditEnrollmentDto addAuditEnrollment(AuditEnrollmentDto auditEnrollmentDto) {
		AuditEnrollment auditEnrollment = new AuditEnrollment();
		BeanUtils.copyProperties(auditEnrollmentDto, auditEnrollment);
		auditEnrollment.setTime_zone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
		AuditEnrollment responseData = auditEnrollmentRepository.save(auditEnrollment);
		auditEnrollmentDto.setTime_zone(responseData.getTime_zone());
		if (responseData != null)
			return auditEnrollmentDto;
		return null;
	}

	@Override
	public AuditTrailDto addAuditTrail(AuditTrailDto auditTrailDto) {
		AuditTrail auditTrail = new AuditTrail();
		BeanUtils.copyProperties(auditTrailDto, auditTrail);
		auditTrail.setTime_zone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
		AuditTrail responseData = auditTrailRepository.save(auditTrail);
		auditTrailDto.setTime_zone(responseData.getTime_zone());
		if (responseData != null)
			return auditTrailDto;
		return null;
	}

	@Override
	public AuditEnrollmentDto editAuditEnrollment(AuditEnrollmentDto auditEnrollmentDto) {
		Optional<AuditEnrollment> data = auditEnrollmentRepository.findById(auditEnrollmentDto.getMemberID());
		if (data.isPresent()) {
			AuditEnrollment auditEnrollment = data.get();
			BeanUtils.copyProperties(auditEnrollmentDto, auditEnrollment);
			auditEnrollment
					.setTime_zone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
			auditEnrollmentRepository.save(auditEnrollment);
			return auditEnrollmentDto;
		}
		return null;
	}

	@Override
	public AuditTrailDto editAuditTrail(AuditTrailDto auditTrailDto) {
		Optional<AuditTrail> data = auditTrailRepository.findById(auditTrailDto.getMemberID());
		if (data.isPresent()) {
			AuditTrail auditTrail = data.get();
			String memberID = auditTrail.getMemberID();
			BeanUtils.copyProperties(auditTrailDto, auditTrail);
			auditTrail.setMemberID(memberID);
			auditTrail.setTime_zone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
			auditTrailRepository.save(auditTrail);
			return auditTrailDto;
		}
		return null;
	}

}
