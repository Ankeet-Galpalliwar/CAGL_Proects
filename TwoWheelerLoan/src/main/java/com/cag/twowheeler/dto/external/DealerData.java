package com.cag.twowheeler.dto.external;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DealerData {
	@Id
	private String id;
	private String dealersID;
	private String oem;
	private String dalersName;
	private String dealeraddress;
	private String phoneNumber;
	// ----
	private String branchID;
	private String branchName;
	private String region;
	private String area;
	private String state;
	// ----
	private String vehicalPriceID;
	private double vehicalOnRoadPrice;
	private double vehicalMaxLoanAmount;
	private double ExshowroomPrice;
	private String variantName;
	private String model;

}
