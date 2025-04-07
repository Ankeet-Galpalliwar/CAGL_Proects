package com.cagl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RFperformance {
	
	private String branchId;
	private String numberOfCustomer;
	private String numberOfActiveBorrower;
	private String portfolioCr;
	private String activeLoans;
	private String numberOfBorrowerInPar;
	private String overallParOsCr;
	private String numberOfHrMember;
	private String hrAmountCr;
	private String overallParPer;
	

}
