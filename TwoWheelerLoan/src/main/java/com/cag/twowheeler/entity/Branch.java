package com.cag.twowheeler.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Branch {

	@Id
	@Column(name = "BranchID")
	private String branchID;

	@Column(name = "BranchName")
	private String branchName;

	@Column(name = "Region")
	private String region;

	@Column(name = "Area")
	private String area;

	@Column(name = "State")
	private String state;

	

	@ManyToMany(mappedBy = "mainBranches")
	private List<MainDealer> mainDealers;

//	@ManyToOne (cascade = CascadeType.ALL)
//	@JoinColumn(name = "MainDealerID")
//	private MainDealer mainDealer;
	
	@ManyToMany(mappedBy = "subBranches")
	private List<SubDealer> subDealers;
	
//	@ManyToOne
//	@JoinColumn(name = "SubDealerID")
//	private SubDealer subDealer;
}
