package com.cagl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cagl.Responce.Responce;
import com.cagl.Responce.Voterauthresponce;
import com.cagl.Responce.smsResponce;
import com.cagl.dto.AuditEnrollmentDto;
import com.cagl.dto.AuditTrailDto;
import com.cagl.request.smsRequest;
import com.cagl.request.voterauth;
import com.cagl.service.AuditService;

/**
 * @author Ankeet G.
 */
@RestController
@CrossOrigin(origins = "*")
public class AuditController {
	@Autowired
	AuditService auditService;
	
	@PostMapping("/auditenrollment")
	public ResponseEntity<Responce> insertAuditEnrollment(@RequestHeader("Authorization") String Authorization,
			@RequestBody AuditEnrollmentDto auditEnrollmentDto) {
		if (Authorization.equals("Q2FnbCQyMDIz")) {
			AuditEnrollmentDto addAuditEnrollment = auditService.addAuditEnrollment(auditEnrollmentDto);
			return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().error(Boolean.FALSE)
					.message("Data Add or Edit Sucessfully").Data(addAuditEnrollment).build());
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Responce.builder().error(Boolean.TRUE)
					.message("Data Not Added or Edited {INVALID USER..!}").Data(null).build());
		}
	}
	
	
	

	@PostMapping("/audittrail")
	public ResponseEntity<Responce> insertAuditTrail(@RequestHeader("Authorization") String headerValue,
			@RequestBody AuditTrailDto auditTrailDto) {
		if (headerValue.equals("Q2FnbCQyMDIz")) {
			AuditTrailDto addAuditTrail = auditService.addAuditTrail(auditTrailDto);
			return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().error(Boolean.FALSE)
					.message("Data Add or Edit Sucessfully").Data(addAuditTrail).build());
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Responce.builder().error(Boolean.TRUE)
					.message("Data Not Added or Edited {INVALID USER..!}").Data(null).build());
		}
	}
	
	
	
	
	@PostMapping("requestotp")
	public ResponseEntity<Responce> sms(@RequestBody smsRequest requestData){
		RestTemplate restTemplate = new RestTemplate();

//		String apiUrl = "https://caglinterface.grameenkoota.in/cga_sms/v1/SMS";
		String apiUrl = "http://172.16.8.119:4444/cga_sms/v1/SMS";

		// Set request headers
		HttpHeaders headers = new HttpHeaders();	
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("key","175d725e-73b5-11ec-9c5d-0abea36e3286");

		// Create an HttpEntity with the request object and headers
		HttpEntity<smsRequest> requestEntity = new HttpEntity<>(requestData,headers);

		// Send the POST request to the third-party API
		ResponseEntity<smsResponce> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity,
				smsResponce.class);

		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.FALSE).Data(responseEntity.getBody()).message("Voter Auth Responce").build());
	}

	   
	@PostMapping("voterauth")
	public ResponseEntity<Responce> voterauth(@RequestBody com.cagl.request.voterauth requestData) {
		RestTemplate restTemplate = new RestTemplate();

//		String apiUrl1 = "https://caglinterface.grameenkoota.in/DL/digivoterauth/v1/voterauth";
		String apiUrl1 = "http://172.16.8.119:4444/DL/digivoterauth/v1/voterauth";

		// Set request headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		

		// Create an HttpEntity with the request object and headers
		HttpEntity<voterauth> requestEntity = new HttpEntity<>(requestData,headers);

		// Send the POST request to the third-party API
		ResponseEntity<Voterauthresponce> responseEntity = restTemplate.exchange(apiUrl1, HttpMethod.POST, requestEntity,
				Voterauthresponce.class);
		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.FALSE).Data(responseEntity.getBody()).message("Voter Auth Responce").build());
	}

	// non use API
//=============================================================================================================
	@GetMapping("/editauditenrollment")
	public ResponseEntity<Responce> editAuditEnrollment(@RequestBody AuditEnrollmentDto auditEnrollmentDto) {
		AuditEnrollmentDto editAuditEnrollment = auditService.editAuditEnrollment(auditEnrollmentDto);
		if (editAuditEnrollment != null)
			return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().error(Boolean.FALSE)
					.message("Data Modified sucessfully").Data(editAuditEnrollment).build());
		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(Responce.builder().error(Boolean.TRUE).message("Data Not Modified").Data(null).build());

	}

	@GetMapping("/editaudittrail")
	public ResponseEntity<Responce> editAuditTrail(@RequestBody AuditTrailDto auditTrailDto) {
		AuditTrailDto addAuditTrail = auditService.editAuditTrail(auditTrailDto);
		if (addAuditTrail != null)
			return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().error(Boolean.FALSE)
					.message("Data Modified sucessfully").Data(addAuditTrail).build());

		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(Responce.builder().error(Boolean.FALSE).message("Data Not Modified").Data(null).build());
	}
	
	
	

}
