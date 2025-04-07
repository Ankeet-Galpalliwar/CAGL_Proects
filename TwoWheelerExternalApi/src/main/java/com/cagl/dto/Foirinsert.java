package com.cagl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Foirinsert {

	@JsonProperty("customer_id")
	private String customer_id;

	@JsonProperty("loan_id")
	private String loan_id;

	@JsonProperty("request_id")
	private long request_id;

	@JsonProperty("familyincome")
	private double familyincome;

	@JsonProperty("applied_loan_emi")
	private double applied_loan_emi;

	@JsonProperty("eligible_emi")
	private double eligible_emi;

	@JsonProperty("cagl_Vintage")
	private long cagl_Vintage;

	@JsonProperty("first_loan_disb_date")
	private String first_loan_disb_date;

	@JsonProperty("cagl_portfolio")
	private long cagl_portfolio;

	@JsonProperty("request_date")
	private String request_date;

	@JsonProperty("applied_loan_amount")
	private String applied_loan_amount;

	@JsonProperty("cagl_CUST_DPD")
	private long cagl_CUST_DPD;

	@JsonProperty("no_Of_ENQUIRIES")
	private long no_Of_ENQUIRIES;

	@JsonProperty("product")
	private String product;

	@JsonProperty("branch_ID")
	private String branch_ID;

}
