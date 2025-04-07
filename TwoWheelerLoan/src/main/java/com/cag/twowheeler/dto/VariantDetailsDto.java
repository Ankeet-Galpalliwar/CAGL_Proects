package com.cag.twowheeler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class VariantDetailsDto {
	
	private String variantID;

	private double vehicalOnRoadPrice;

	private double vehicalMaxLoanAmount;

	private double ExshowroomPrice;

	private String state;

	private String variantName;

	private String model;

	private String oem;

}
