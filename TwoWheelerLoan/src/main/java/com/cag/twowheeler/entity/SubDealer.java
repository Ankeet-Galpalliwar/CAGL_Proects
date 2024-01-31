package com.cag.twowheeler.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubDealer {
	@Id	
	private String subDealerID;
	
	@Column(name = "IDStatus")
	private String idStatus;
	
	@Column(name = "DealerName")
	private String dealerName;
	
	@Column(name = "ManufacturerName")
	private String manufacturerName;
	
	@Column(name = "MailID")
	private String mailID;
	
	@Column(name = "ContactNo")
	private long contactNumber;
	
	@Column(name = "AlternateContactNo")
	private String alternateContactNumber;
	
	@Column(name = "ContactPersonName")
	private String contactPersonName;          
	
	@Column(name = "ContactPersonMobile")
	private String contactPersonMobile;         
	
	@Column(name="PanNumber")
	private String panNumber;
	
	@Column(name = "GstNumber")
	private String gstNumber;
	
	@Column(name = "BankName")
	private String bankName;
	
	@Column(name = "BranchName")
	private String bankBranchName;
	
	@Column(name = "BankACNo")
	private String bankAccNumber;
	
	@Column(name = "IFSCCode")
	private String ifsc;
	
	@Column(name = "DealerAccountHolderName")
	private String accountHolderName;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "ActivationStatus")
	private String activatioinStatus; 
	
	@Column(name = "paymentEligible")
	private String paymentEligible; 

	
	@Column(name = "PaniniCheck")
	private String paniniCheck;          //change name
	
	@Column(name = "ActivationDate")
	private LocalDate activationData;
	
	@Column(name = "ExpiryDate")
	private LocalDate expiryDate;				 
	
	@Column(name = "MakerUserID")
	private String makerUserID;
	
	@Column(name = "MakerDate")
	private LocalDate makerDate;
	
	@Column(name ="TimeZone")
	private String timeZone;
	
	@Column(name = "UpdaterUserID")
	private String updaterUserID; 

	@Column(name = "UpdatedDate")
	private LocalDate updatedDate; 
	
	@Column(name = "CheckerID")
	private String checkerUserID;
	
	@Column(name = "CheckerDate")
	private LocalDate checkerDate;		
	
	@Column(name = "State")                
	private String state;
	
	@Column(name = "District")
	private String district;
	
//	@Column(name = "Region")
//	private String region;
//	                             NO NEED WE GIVE SEPRATE IN BRANCH
//	@Column(name = "Area")
//	private String area;
	
	@Column(name = "TownCityVillage")
	private String city;
	
	@Column(name="PinCode")
	private String pinCode;
	
	@Column(name = "Area")
	private String Area;
	
	@Column(name = "DealerType")
	private String delearType;
	
	@ManyToOne
	@JoinColumn(name = "MainDealerId")
	private MainDealer mainDealers;
	

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "subdealers__vehicles",
	     joinColumns = @JoinColumn(name="subDealerID"),
	     inverseJoinColumns = @JoinColumn(name="vehicleVeriantId")
	)
	private List<VehicalPrice> vehicleVeriants;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "subdealers_branches", 
	joinColumns = @JoinColumn(name="subDealerID"),
	inverseJoinColumns = @JoinColumn(name="GLBranchT24ID")
	)
	private List<Branch> subBranches;
	
	
//	@OneToMany(mappedBy = "subDealer", cascade = CascadeType.ALL)
//	private List<Branch> subBranches;
	
	
	

}
