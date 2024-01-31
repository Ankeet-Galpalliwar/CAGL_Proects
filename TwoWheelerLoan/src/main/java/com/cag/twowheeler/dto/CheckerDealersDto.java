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
public class CheckerDealersDto {

	    private String  DealerID;
		private String DealerName;
		private String DealerManufacturerName;
		private String DealerMailID;
		private long DealerContactNumber;
		private String DealerAlternateContactNumber;
		private String DealerContactPersonName;
		private String contactPersonMobile;
		private String DealerPanNumber;
		private String DealerGstNumber;
		private String DealerBankName;
		private String DealerBankBranchName;
		private String DealerBankAccNumber;
		private String DealerIfsc;
		private String DealerAccountHolderName;
		private String DealerStatus;
		private String DealerActivationStatus;
		private String DealerPaniniCheck;
		private LocalDate DealerActivationData;
		private LocalDate DealerExpireData;
		private String dealerType;
		private String state;
		private List<String> district;
		private String city;
		private String pinCode;
		private String addressDetails;
}
