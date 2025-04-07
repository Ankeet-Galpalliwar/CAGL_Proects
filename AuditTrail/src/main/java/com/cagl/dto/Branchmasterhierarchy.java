package com.cagl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branchmasterhierarchy {
	
	
	private String office_id;
	private String Branch_name;
	private String AreaName;
	private String Region_Name;
	private String zone;
	private String type;
	private String state;
	private String branch_opening_date;
	private String branch_status;
	private String Branch_Vintage;
	private String area_id;
	private String region_id;
	private String state_id;

}
