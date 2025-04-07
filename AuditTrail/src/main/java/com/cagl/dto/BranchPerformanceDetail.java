package com.cagl.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchPerformanceDetail {
	

	
	private String branch_id;
	private String branch_opening_date;
	private String no_of_active_mem;
	private String noofactbrrwr;
	private String waitingbrrwr_zero_os;
	private String Portfolio;
	private String Par_per;
	private String no_of_kendra;
	private String no_of_group;
	private BigDecimal ld_amount_all;
	private double PAR_all;
	private double Branch_Vintage;
	
	

}
