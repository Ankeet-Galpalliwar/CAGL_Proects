package com.cag.twowheeler.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentDto {

	private String DocumentID;
	
	private String documentType;
	
	private String fileName;
	
	private String fileType;

	private long fileLength;

}
