package com.cag.twowheeler.entity;

import java.time.LocalDate;
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "vehicalPriceiD")
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicalPrice {

	@Id
	private String vehicalPriceID;

	private String uniqueVehicleID;

	@Column(name = "OnRoadPrice")
	private double vehicalOnRoadPrice;

	@Column(name = "MaxLoanAmount")
	private double vehicalMaxLoanAmount;

	@Column(name = "ExShowroomPrice")
	private double ExshowroomPrice;

	private String state;

	private String status;

	@Column(name = "UpdaterUserID")
	private String updaterUserID;
	
	@Column(name = "UpdatedDate")
	private String updatedDate;
	
	@Column(name ="TimeZone")
	private String timeZone;
	
	@Column(name = "priceActivationDate")
	private LocalDate priceActivationDate;
	
	@Column(name = "priceExpireDate")
	private LocalDate priceExpireDate;
		
	@Column(name = "variant")
	private String variantName;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "oem")
	private String oem;



	@ToString.Exclude
	@ManyToOne//(cascade = CascadeType.ALL) // ,fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicalvariant_Id")
	private VehicalVariant type;

//	@ToString.Exclude
//	@ManyToOne // (cascade = CascadeType.ALL)//,fetch = FetchType.EAGER)
//	@JoinColumn(name = "Vehical_oemid")
////	@JsonBackReference
//	private VehicalOem oem;
	@ToString.Exclude
	@ManyToMany(mappedBy = "vehicleVeriants")
	private List<SubDealer> subDealers;
	@ToString.Exclude
	@ManyToMany(mappedBy = "Veriants")
	private List<MainDealer> mainDealers;
}
