package com.cagl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KendraWiseSummary {
	
	
	private String BRANCH_ID;
	private String Kendra_id;
	private String NAME;
	private String MEETING_DAY;
	private String MEETING_FREQ;
	private String MEETING_TIME_FROM;
	private String MEETING_TIME_TO;
	private String MEETING_DATE;
	private String KENDRA_MANAGER;
	private String KM_NAME;
	private String kendra_act_date;
	private String report_date;
	private String no_of_active_mem;
	private String noofactbrrwr;
	private String Portfolio;
	private String par;
	private String Par_per;
	private String no_of_group;
	private String num_of_active_acc;
	private String no_par_customer;
	private String highriskno_par_customer;
	private String highrisknoofloans;
	private String highrisk_portfolio;

}
