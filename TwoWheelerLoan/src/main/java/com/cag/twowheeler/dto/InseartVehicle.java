package com.cag.twowheeler.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
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
