package com.cag.twowheeler.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "vehicalTypeid")

public class VehicalVariant {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "variantId")
	private String vehicalTypeid;

	private String vehicalvariantName;

	@ManyToOne // (cascade = CascadeType.ALL)//,fetch = FetchType.EAGER)
	@JoinColumn(name = "Vehicaloemid")
//	@JsonBackReference
	private VehicalOem oem;

	@OneToMany(mappedBy = "type")
	private List<VehicalPrice> vehical_Price;

	private String vehicleModal;

	@Column(name = "FilePath")
	private String filePath;

	@Column(name = "FileName")
	private String fileName;

}
