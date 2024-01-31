package com.cag.twowheeler.dto;

import java.time.LocalDate;
import java.util.List;

import com.cag.twowheeler.entity.MainDealer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubDealerDetailsDto {

//	private String mainDealerID;
	private String subDealerID;
	private String subDealerName;
	private String subDealerManufacturerName;
	private String subDealerMailID;
	private long subDealerContactNumber;
	private String subDealerAlternateContactNumber;
	private String subDealerContactPersonName;
	private String contactPersonMobile;
	private String subDealerPanNumber;
	private String subDealerGstNumber;
	private String subDealerBankName;
	private String subDealerBankBranchName;
	private String subDealerBankAccNumber;
	private String subDealerIfsc;
	private String subDealerAccountHolderName;
	private String subDealerStatus;
	private String subDealerActivationStatus;
	private String paymentEligible;
	private String subDealerPaniniCheck;
	private LocalDate subDealerActivationData;
	private LocalDate subDealerExpireData;
	private String dealerType;
	private String state;
	private List<String> district;
	private String city;
	private String pinCode;
	private String addressDetails;//Area
	private String timeZone;

	private String updaterUserID;
	private LocalDate updaterDate;
	// Only use for while adding Sub Dealer
	private MainDealer mainDealer;

}
