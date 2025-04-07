package com.cag.twowheeler.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cag.twowheeler.dto.CheckerDealersDto;
import com.cag.twowheeler.dto.MainDealerDetailsDto;
import com.cag.twowheeler.dto.SubDealerDetailsDto;
import com.cag.twowheeler.entity.ApiCallRecords;
import com.cag.twowheeler.entity.MainDealer;
import com.cag.twowheeler.entity.SubDealer;
import com.cag.twowheeler.exceptation.AlreadyExist;
import com.cag.twowheeler.repository.ApiCallRepository;
import com.cag.twowheeler.repository.MainDealerRepository;
import com.cag.twowheeler.repository.SubDealerRepository;
import com.cag.twowheeler.responce.responce;

/**
 * @author Ankeet G.
 */
@RestController
@RequestMapping("/checker")
@CrossOrigin(origins = "*")
public class CheckerController {

	@Autowired
	MainDealerRepository mainDealerRepository;

	@Autowired
	SubDealerRepository subDealerRepository;

	String district = "";
	int count = 1;

	@GetMapping("/allcheckerdealers")
	public ResponseEntity<responce> getCheckerAllDealers() {

		List<CheckerDealersDto> checkerDealers = new ArrayList<>();

		List<SubDealer> allSubDealerData = subDealerRepository.findAll();
		List<CheckerDealersDto> subDealer = allSubDealerData.stream()
				.filter(e -> (e.getStatus().equalsIgnoreCase("PENDING") || e.getStatus().equalsIgnoreCase("UPDATED")))
				.map(e -> {
					String[] split = e.getDistrict().split(",");
					List<String> district = Arrays.asList(split);
					return CheckerDealersDto.builder().DealerAccountHolderName(e.getAccountHolderName())
							.DealerActivationStatus(e.getActivatioinStatus()).DealerStatus(e.getStatus())
							.DealerActivationData(e.getActivationData()).DealerExpireData(e.getExpiryDate())
							.DealerAlternateContactNumber(e.getAlternateContactNumber())
							.DealerBankAccNumber(e.getBankAccNumber()).DealerBankBranchName(e.getBankBranchName())
							.DealerBankName(e.getBankName()).DealerContactNumber(e.getContactNumber())
							.DealerContactPersonName(e.getContactPersonName()).DealerGstNumber(e.getGstNumber())
							.DealerIfsc(e.getIfsc()).DealerMailID(e.getMailID())
							.DealerManufacturerName(e.getManufacturerName()).DealerName(e.getDealerName())
							.DealerPanNumber(e.getPanNumber()).DealerPaniniCheck(e.getPaniniCheck())
							.DealerID(e.getSubDealerID()).addressDetails(e.getArea()).city(e.getCity())
							.contactPersonMobile(e.getContactPersonMobile()).state(e.getState()).district(district)
							.pinCode(e.getPinCode()).dealerType(e.getDelearType()).build();
				}).collect(Collectors.toList());

		List<CheckerDealersDto> mainDealer = mainDealerRepository.findAll().stream()
				.filter(e -> e.getStatus().equalsIgnoreCase("PENDING") || e.getStatus().equalsIgnoreCase("UPDATED"))
				.map(e -> {
					String[] split = e.getDistrict().split(",");
					List<String> district = Arrays.asList(split);
					return CheckerDealersDto.builder().DealerAccountHolderName(e.getAccountHolderName())
							.DealerActivationStatus(e.getActivatioinStatus()).DealerStatus(e.getStatus())
							.DealerActivationData(e.getActivationData()).DealerExpireData(e.getExpiryDate())
							.DealerAlternateContactNumber(e.getAlternateContactNumber())
							.DealerBankAccNumber(e.getBankAccNumber()).DealerBankBranchName(e.getBankBranchName())
							.DealerBankName(e.getBankName()).DealerContactNumber(e.getContactNumber())
							.DealerContactPersonName(e.getContactPersonName()).DealerGstNumber(e.getGstNumber())
							.DealerIfsc(e.getIfsc()).DealerMailID(e.getMailID())
							.DealerManufacturerName(e.getManufacturerName()).DealerName(e.getDealerName())
							.DealerPanNumber(e.getPanNumber()).DealerPaniniCheck(e.getPaniniCheck())
							.DealerID(e.getMainDealerID()).addressDetails(e.getArea()).city(e.getCity())
							.contactPersonMobile(e.getContactPersonMobile()).district(district).state(e.getState())
							.pinCode(e.getPinCode()).dealerType(e.getDelearType()).build();
				}).collect(Collectors.toList());
		checkerDealers.addAll(subDealer);
		checkerDealers.addAll(mainDealer);
		if (checkerDealers.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(responce.builder().error("True").data("--NA--").message("No  Dealer Present").build());
		return ResponseEntity.status(HttpStatus.OK)
				.body(responce.builder().error("False").data(checkerDealers).message(" Dealer Data Fetch..!").build());
	}
	@Autowired
	ApiCallRepository apirecords;

	
	@PostMapping("/updatestatusoredit")
	public ResponseEntity<responce> modifyStatus(Authentication authentication, @RequestParam String dealerID,
			@RequestBody CheckerDealersDto checkerDealersDto) {
		

		apirecords.save(ApiCallRecords.builder().apiname("updatestatusoredit")
				.timeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()))
				.msg(authentication.getName()+"="+dealerID).build());

		String userName = authentication.getName();
		if (dealerID.substring(11).equalsIgnoreCase("A01")) {
			MainDealer mainDealer = mainDealerRepository.findById(dealerID).get();
			String mainDealerID = mainDealer.getMainDealerID();

//			if (mainDealer.getBankAccNumber() != checkerDealersDto.getDealerBankAccNumber()) {
//				if (mainDealerRepository.findByBankAccNumber(checkerDealersDto.getDealerBankAccNumber()) != null)
//					throw new AlreadyExist("Bank_Account Number", "ERROR_CODE_000");
//			}
//			if (!mainDealer.getPanNumber().equalsIgnoreCase(checkerDealersDto.getDealerPanNumber())) {
//				if (mainDealerRepository.findByPanNumber(checkerDealersDto.getDealerPanNumber()) != null)
//					throw new AlreadyExist("PanCard_Number", "ERROR_CODE_000");
//			}
//			if (!mainDealer.getGstNumber().equalsIgnoreCase(checkerDealersDto.getDealerGstNumber())) {
//				if (mainDealerRepository.findByGstNumber(checkerDealersDto.getDealerGstNumber()) != null)
//					throw new AlreadyExist("Gst_Number", "ERROR_CODE_000");
//			}

			// District Logic
			district = "";
			count = 1;
			checkerDealersDto.getDistrict().stream().forEach(e -> {
				if (checkerDealersDto.getDistrict().size() == count)
					district += e;
				else
					district += e + ',';
				count++;
			});
			MainDealer newMainDealer = MainDealer.builder().status(checkerDealersDto.getDealerStatus()).timeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()))
					.updatedDate(mainDealer.getUpdatedDate()).updaterUserID(mainDealer.getUpdaterUserID())
					.checkerDate(LocalDate.now()).checkerUserID(userName).makerDate(mainDealer.getMakerDate())
					.makerUserID(mainDealer.getMakerUserID()).delearType("MAIN").idStatus("OK").paymentEligible("YES")
					.activatioinStatus(checkerDealersDto.getDealerActivationStatus())
					.accountHolderName(checkerDealersDto.getDealerAccountHolderName())
					.activationData(checkerDealersDto.getDealerActivationData()).expiryDate(mainDealer.getExpiryDate())
					.alternateContactNumber(checkerDealersDto.getDealerAlternateContactNumber())
					.bankAccNumber(checkerDealersDto.getDealerBankAccNumber())
					.bankBranchName(checkerDealersDto.getDealerBankBranchName())
					.bankName(checkerDealersDto.getDealerBankName())
					.contactNumber(checkerDealersDto.getDealerContactNumber())
					.contactPersonName(checkerDealersDto.getDealerContactPersonName())
					.dealerName(checkerDealersDto.getDealerName()).gstNumber(checkerDealersDto.getDealerGstNumber())
					.ifsc(checkerDealersDto.getDealerIfsc()).mailID(checkerDealersDto.getDealerMailID())
					.manufacturerName(checkerDealersDto.getDealerManufacturerName())
					.panNumber(checkerDealersDto.getDealerPanNumber())
					.paniniCheck(checkerDealersDto.getDealerPaniniCheck()).Area(checkerDealersDto.getAddressDetails())
					.state(checkerDealersDto.getState()).district(district).city(checkerDealersDto.getCity())
					.pinCode(checkerDealersDto.getPinCode()).mainBranches(mainDealer.getMainBranches()).Veriants(mainDealer.getVeriants())
					.contactPersonMobile(checkerDealersDto.getContactPersonMobile()).delearType("MAIN").build();
			BeanUtils.copyProperties(newMainDealer, mainDealer); // Hear Id updated to Zero (---v)
			mainDealer.setMainDealerID(mainDealerID); // Seating original Id {Because it will Be zero }

			mainDealerRepository.save(mainDealer);
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("FALSE").message("Status Updated or Data Updated").build());
		} else {// using get method to avoid optional value
			SubDealer subDealer = subDealerRepository.findById(dealerID).get();
			String subDealerID = subDealer.getSubDealerID();

//			if (subDealer.getBankAccNumber() != checkerDealersDto.getDealerBankAccNumber()) {
//				if (subDealerRepository.findByBankAccNumber(checkerDealersDto.getDealerBankAccNumber()) != null)
//					throw new AlreadyExist("Bank_Account Number", "ERROR_CODE_000");
//			}
//			if (!subDealer.getPanNumber().equalsIgnoreCase(checkerDealersDto.getDealerPanNumber())) {
//				if (subDealerRepository.findByPanNumber(checkerDealersDto.getDealerPanNumber()) != null)
//					throw new AlreadyExist("Pan_Card Number", "ERROR_CODE_000");
//			}
//			if (!subDealer.getGstNumber().equalsIgnoreCase(checkerDealersDto.getDealerGstNumber())) {
//				if (subDealerRepository.findByGstNumber(checkerDealersDto.getDealerGstNumber()) != null)
//					throw new AlreadyExist("Gst_Number", "ERROR_CODE_000");
//			}
			MainDealer mainDealer = subDealer.getMainDealers();

			// district Logic
			district = "";
			count = 1;
			checkerDealersDto.getDistrict().stream().forEach(e -> {
				if (checkerDealersDto.getDistrict().size() == count)
					district += e;
				else
					district += e + ',';
				count++;
			});

			SubDealer newSubDealer = SubDealer.builder().status(checkerDealersDto.getDealerStatus()).timeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()))
					.updatedDate(subDealer.getUpdatedDate()).updaterUserID(subDealer.getUpdaterUserID())
					.checkerDate(LocalDate.now()).checkerUserID(userName).makerDate(subDealer.getMakerDate())
					.makerUserID(subDealer.getMakerUserID()).delearType("SUB").idStatus("OK").paymentEligible("NO")
					.activatioinStatus(checkerDealersDto.getDealerActivationStatus())
					.accountHolderName(checkerDealersDto.getDealerAccountHolderName())
					.activationData(checkerDealersDto.getDealerActivationData()).expiryDate(subDealer.getExpiryDate())
					.alternateContactNumber(checkerDealersDto.getDealerAlternateContactNumber())
					.bankAccNumber(checkerDealersDto.getDealerBankAccNumber())
					.bankBranchName(checkerDealersDto.getDealerBankBranchName())
					.bankName(checkerDealersDto.getDealerBankName())
					.contactNumber(checkerDealersDto.getDealerContactNumber())
					.contactPersonName(checkerDealersDto.getDealerContactPersonName())
					.dealerName(checkerDealersDto.getDealerName()).gstNumber(checkerDealersDto.getDealerGstNumber())
					.ifsc(checkerDealersDto.getDealerIfsc()).mailID(checkerDealersDto.getDealerMailID())
					.manufacturerName(checkerDealersDto.getDealerManufacturerName())
					.panNumber(checkerDealersDto.getDealerPanNumber()).subBranches(subDealer.getSubBranches()).vehicleVeriants(subDealer.getVehicleVeriants())
					.paniniCheck(checkerDealersDto.getDealerPaniniCheck()).mainDealers(mainDealer)
					.contactPersonMobile(checkerDealersDto.getContactPersonMobile()).state(checkerDealersDto.getState())
					.city(checkerDealersDto.getCity()).district(district).pinCode(checkerDealersDto.getPinCode())
					.Area(checkerDealersDto.getAddressDetails()).delearType("SUB").build();
			BeanUtils.copyProperties(newSubDealer, subDealer);// Hear Id updated to null
			subDealer.setSubDealerID(subDealerID);
			subDealerRepository.save(subDealer);

			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("FALSE").message("Status Updated or Data Updated").build());
		}
	}

}
