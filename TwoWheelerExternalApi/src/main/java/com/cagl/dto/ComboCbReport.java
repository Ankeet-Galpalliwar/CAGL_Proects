package com.cagl.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComboCbReport {
	
	
	@JsonProperty("address")
	private List<address> address;
	
	@JsonProperty("document")
	private List<document> document;
	
	@JsonProperty("depType")
	private String depType;
	@JsonProperty("depName")
	private String depName;
	@JsonProperty("categoryId")
	private String categoryId;
	@JsonProperty("productCode")
	private String productCode;
	@JsonProperty("dob")
	private String dob;
	@JsonProperty("durationOfAgreement")
	private String durationOfAgreement;
	@JsonProperty("bankProductId")
	private String bankProductId;
	@JsonProperty("custName")
	private String custName;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("phone")
	private String phone;
	@JsonProperty("loanType")
	private String loanType;
	@JsonProperty("appId")
	private String appId;
	@JsonProperty("losIndicator")
	private String losIndicator;
	@JsonProperty("losIndex")
	private String losIndex;
	@JsonProperty("slNo")
	private String slNo;
	@JsonProperty("loanAmount")
	private String loanAmount;
	@JsonProperty("email")
	private String email;
	@JsonProperty("maritalStatus")
	private String maritalStatus;
	@JsonProperty("kendra")
	private String kendra;
	@JsonProperty("branch")
	private String branch;
	@JsonProperty("stateBranch")
	private String stateBranch;
	@JsonProperty("custId")
	private String custId;
	@JsonProperty("source")
	private String source;
	@JsonProperty("enquiryType")
	private String enquiryType;
	@JsonProperty("loanId")
	private String loanId;

}
