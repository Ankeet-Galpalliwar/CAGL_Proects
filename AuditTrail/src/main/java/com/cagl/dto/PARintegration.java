package com.cagl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PARintegration {
	
	private String report_date;
	private String account_id;
	private String CUSTOMER_ID;
	private String CUST_NAME;
	private String KENDRA_ID;
	private String KENDRA_NAME;
	private String BRANCH_ID;
	private String BRANCH;
	private String AREA;
	private String DIVISION;
	private String OD_PRINCIPAL;
	private String OD_interest;
	private String unpaid_principal;
	private String LD_ID;
	private String category;
	private String MAT_DATE;
	private String FUNDER_ID;
	private String FUND_NAME;
	private String customer_status;
	private String ln_value_date;
	private String DAYS_IN_ARREARs;
	private String meeting_day;
	private String kendra_manager;
	private String km_name;
	private String product_name;
	private String freq;
	private String installment_amount;
	private String arrived_dpd;
	private String OTS_ELIGIBLE;
	private String arrstatus;

}
