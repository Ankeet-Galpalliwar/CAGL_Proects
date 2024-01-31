package com.cagl.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class DigiAgilCustomerDto {
	private String customer_id;
	private String loan_id;
	private int request_id;
	private float Familyincome;
	private float applied_loan_emi;
	private float eligible_emi;
	private int CAGL_Vintage;
	private String first_loan_disb_date;
	private int CAGL_portfolio;
	private Date request_date;
	private String Applied_loan_amount;
	private int CAGL_CUST_OVERDUE;
	private int CAGL_CUST_DPD;
	private int NO_Of_ENQUIRIES;
	private int PRODUCT_ID;
	private String Branch_ID;

}
