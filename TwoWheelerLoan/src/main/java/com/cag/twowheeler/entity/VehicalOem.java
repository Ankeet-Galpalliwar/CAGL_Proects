package com.cag.twowheeler.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "vehicalOemid")
public class VehicalOem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vehicalOemid;
	
	private String vehicalOem;
	
	@ToString.Exclude
	@OneToMany(mappedBy ="oem",cascade = CascadeType.ALL)//,fetch = FetchType.LAZY
	@JsonIgnore
	@JsonManagedReference
	private List<VehicalVariant> vehicalType;
	
//	@ToString.Exclude
//	@OneToMany(mappedBy = "oem", cascade = CascadeType.ALL)
//	@JsonIgnore
//	@JsonManagedReference
//	private List<VehicalPrice> vehicalPrice;


}
