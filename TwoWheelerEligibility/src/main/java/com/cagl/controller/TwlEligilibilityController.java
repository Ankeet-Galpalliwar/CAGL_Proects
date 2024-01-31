package com.cagl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cagl.Service.TwlEligilibilityService;
import com.cagl.dto.DigiAgilCustomerDto;
import com.cagl.response.Response;

@RestController
public class TwlEligilibilityController {

	@Autowired
	TwlEligilibilityService eligilibilityService;

	@PostMapping("insertcustomer")
	public ResponseEntity<Response> insertData(@RequestBody DigiAgilCustomerDto digiAgilCustomerDto) {
		DigiAgilCustomerDto insertCustomerData = eligilibilityService.insertCustomerData(digiAgilCustomerDto);
		if (insertCustomerData != null)
			return ResponseEntity.status(HttpStatus.OK).body(Response.builder().error(Boolean.FALSE)
					.message("Customer Data Inseart").data(insertCustomerData).build());
		return ResponseEntity.status(HttpStatus.OK)
				.body(Response.builder().error(Boolean.TRUE).message("Data Not Insearted").data("---NA---").build());
	}
}
