package com.cag.twowheeler.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cag.twowheeler.dto.AllVehicleOem;
import com.cag.twowheeler.dto.CustomVariantInfo;
import com.cag.twowheeler.dto.InseartVehicle;
import com.cag.twowheeler.dto.VariantExcelDto;
import com.cag.twowheeler.dto.VehicalsAllData;
import com.cag.twowheeler.dto.VehiclePriceDetail;

public interface TwoWheelerService {
	

	
	List<VehicalsAllData> getAllVehicalData();

	Boolean editVehicleData(String id,VehicalsAllData data,String updaterUserName);

	String deleteData(int id);

	String addVehicle(InseartVehicle vehicle,String user);

	List<String> getAllStates();

	List<String> getOembaseOnState(String state);

	List<String> getModelsBaseOnOem(String oem);
	
//	List<CustomVariantInfo> getVariantsBymodalAndState(String modal, String state);
	
	List<CustomVariantInfo> getVariantBymodalAndState(String modal, String state);

	List<String> getVarieantDropDown(String vehicleModel);

	List<VariantExcelDto> getVariantExcel(String base);

}
