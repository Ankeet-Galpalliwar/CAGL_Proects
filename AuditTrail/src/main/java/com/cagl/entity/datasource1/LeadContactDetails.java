package com.cagl.entity.datasource1;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeadContactDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
	private long contactNumber;
	private String name;
}
