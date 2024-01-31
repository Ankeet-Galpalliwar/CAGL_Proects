package com.cag.twowheeler.entity;

import java.nio.file.Path;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@ToString    //If We Use StackOverflow Exception Get 
@Builder
public class Documents {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private String DocumentID;
	private String fileName;
	private String fileType; 
	//@Lob
	private String dataPath;
	
	private long fileLength;
	
	private String documentType;
	
	@ManyToOne
	@JoinColumn(name = "MainDealerID")
	private MainDealer mainDealer;
	
	@ManyToOne
	@JoinColumn(name = "SubDealerID")
	private SubDealer subDealer;

}
