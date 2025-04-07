package com.cag.twowheeler.dto.external;

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
@AllArgsConstructor
@NoArgsConstructor
public class DealerVehicleData {

	private String dealersID;
	private String oem;
	private String dalersName;
	private  String dealeraddress;
	private String phoneNumber;
//	private String vehicalPriceID;
//	private double vehicalOnRoadPrice;
//	private double vehicalMaxLoanAmount;
//	private double ExshowroomPrice;
//	private String variantName;
//	private String model;

}
