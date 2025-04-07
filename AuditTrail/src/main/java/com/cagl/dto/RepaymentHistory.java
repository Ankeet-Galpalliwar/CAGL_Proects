package com.cagl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentHistory {

	
	private String member_id;
	private String coll_date;
	private String total_due;
	private String tot_collected;
	private String attendance;
	private String kendra_id;
	private String coll_type;
	private String id;
}
