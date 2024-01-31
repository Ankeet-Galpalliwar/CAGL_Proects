package com.cag.twowheeler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchDto {
	
	private String branchID;

	private String branchName;

	private String region;

	private String area;

	private String state;

}
