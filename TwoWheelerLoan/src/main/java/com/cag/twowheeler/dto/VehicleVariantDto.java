package com.cag.twowheeler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleVariantDto  {
	
	private String variantID;
    private	String variantName;
    private String oem;
    private String filePath;
    private String fileName;


}
