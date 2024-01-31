package com.cag.twowheeler.dto;

import java.time.LocalDate;
import java.util.List;

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
public class MainDealerDetailsDto {

    private String mainDealerID;
	private String mainDealerName;
	private String mainDealerManufacturerName;
	private String mainDealerMailID;
	private long mainDealerContactNumber;
	private String mainDealerAlternateContactNumber;
	private String mainDealerContactPersonName;
	private String contactPersonMobile;
	private String mainDealerPanNumber;
	private String mainDealerGstNumber;
	private String mainDealerBankName;
	private String mainDealerBankBranchName;
	private String mainDealerBankAccNumber;
	private String mainDealerIfsc;
	private String mainDealerAccountHolderName;
	private String mainDealerStatus;
	private String mainDealerActivationStatus;
	private String paymentEligible;
	private String mainDealerPaniniCheck;
	private LocalDate mainDealerActivationData;
	private LocalDate mainDealerExpireData;
	private String dealerType;
	private String state;
	private List<String> district;
	private String city;
	private String pinCode;
	private String addressDetails;//Area
	private String timeZone;
	private String updaterUserID;
	private LocalDate updaterDate;
	private List<SubDealerDetailsDto> subDealerDetailsDtos;

}
