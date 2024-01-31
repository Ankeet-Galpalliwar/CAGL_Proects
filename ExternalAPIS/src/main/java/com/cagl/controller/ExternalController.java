package com.cagl.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cagl.dto.ComboCbReport;
import com.cagl.dto.CustomerDto;
import com.cagl.dto.Responce;
import com.cagl.dto.kendraDto;
import com.cagl.dto.responce.ComboCbReportResponce;
import com.cagl.entity.Kendra;
import com.cagl.entity.customer_eligible;
import com.cagl.repository.Customer_eligibleRepository;
import com.cagl.repository.KendraRepository;

/**
 * 
 * @author Ankeet G.
 *
 */

@RestController
public class ExternalController {

	@Autowired
	private KendraRepository kendraRepository;

	@Autowired
	private Customer_eligibleRepository customer_eligibleRepository;

	@GetMapping("/getkendras")
	public ResponseEntity<Responce> getKendras(@RequestParam String branchID) {
		List<kendraDto> kendrasDto = new ArrayList<>();
		try {
			List<Kendra> Kendras = kendraRepository.getKendrasByBranchID(branchID);
			Kendras.stream().forEach(e -> {
				Kendra kendra = kendraRepository.getKendraId(e.getKendraId());
				kendrasDto.add(
						kendraDto.builder().kendraId(kendra.getKendraId()).kendraName(kendra.getKendraName()).build());
			});
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Responce.builder().Error(Boolean.TRUE)
					.message("Catch Execute...!\n ID Invalid...!").data("---NA---").build());
		}
		if (!kendrasDto.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().Error(Boolean.FALSE).message("all kendras").data(kendrasDto).build());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Responce.builder().Error(Boolean.TRUE).message("kendras Not Present").data("---NA---").build());
	}

	@RequestMapping("/getcustomers")
	public ResponseEntity<Responce> getcustomers(@RequestParam String kendraId) {
		List<CustomerDto> customerDtos = new ArrayList<>();
		try {
			List<customer_eligible> customers = customer_eligibleRepository.getCustomerByKendraID(kendraId);
			customers.stream().forEach(data -> {
				customerDtos.add(CustomerDto.builder().customerID(data.getCustomerID())
						.customerName(data.getCustomerName()).gender(data.getGender()).build());
			});
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Responce.builder().Error(Boolean.TRUE)
					.message("Catch Execute...!\n ID Invalid...!").data("---NA---").build());
		}
		if (!customerDtos.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().Error(Boolean.FALSE).message("all Customers").data(customerDtos).build());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Responce.builder().Error(Boolean.TRUE).message("Customer Not Present").data("---NA---").build());
	}

	@PostMapping("/ComboCbReport")
	public ResponseEntity<Responce> comboCbReport(@RequestBody ComboCbReport cbReport) {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://172.16.101.6:8130/CAGLComboReport/ComboCbReport/makeRequest";
		// Create an HttpEntity with the request object and headers
		HttpEntity<ComboCbReport> requestEntity = new HttpEntity<>(cbReport);

		// Send the POST request to the third-party API
		ResponseEntity<ComboCbReportResponce> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST,
				requestEntity, ComboCbReportResponce.class);

		return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().Error(Boolean.FALSE)
				.data(responseEntity.getBody()).message("Voter Auth Responce").build());
	}
	

}
