package com.cag.twowheeler.dto;

import java.time.LocalDate;

import javax.annotation.Generated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InseartVehicle {

	private String vehicleOem;
	private String vehicleModel;
	private String vehicleVariant;
	private String vehicleState;
	private String status;
	private double vehicleMaxLoanAmount;
	private double exShowRoomPrice;
	private double vehicalOnRoadPrice; 
	private LocalDate priceActivationDate;
	private LocalDate priceExpireDate;
	
}
