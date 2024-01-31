package com.cag.twowheeler.dto;

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
public class CustomVariantInfo {

	private String vehicle_model_name;
	private String vehicalvariant_id;
	private String vehicalvariant_name;
	private Double on_road_price;
	private Double max_loan_amount;
	private String file_path;
	private String file_name;

}
