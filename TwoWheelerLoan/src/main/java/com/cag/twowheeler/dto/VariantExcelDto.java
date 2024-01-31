package com.cag.twowheeler.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VariantExcelDto {
	
	private String uniqueVehicleID;
	private String vehicleVeriantID;
	private String dealerID;
	private String oem;
	private String dealerType;
	private String modelname;
	private String variantName;
	private double onRoadPrice;
	private double exShowroomPrice;
	private String updaterUser;
	private String updatedDate;
	private LocalDate priceActivationDate;
	private LocalDate priceExpireDate;
	private String timeZone;
	
	

}
