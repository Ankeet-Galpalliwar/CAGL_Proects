package com.cag.twowheeler.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class DealerBranchData {

	private String dealersID;
	private String oem;
	private String dalersName;
	private String dealeraddress;
	private String phoneNumber;
	private String branchID;
	private String branchName;
	private String region;
	private String area;
	private String state;

}
