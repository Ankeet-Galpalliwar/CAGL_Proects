package com.cag.twowheeler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchExcelDto {
	
	private String branchID;
	private String branchName;
	private String state;
	private String DealerID;

}
