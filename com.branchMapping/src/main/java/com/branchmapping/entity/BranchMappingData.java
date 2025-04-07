package com.branchmapping.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class BranchMappingData {
	@Id
	private String branchID;
	private String dealersIDS;
}
