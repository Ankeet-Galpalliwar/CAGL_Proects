package com.cagl.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cagl.Responce.Responce;

@RestController
@CrossOrigin(origins = "*")
public class PDFController {

	

	@GetMapping("/getbranchreport-url")
	public ResponseEntity<Responce> getPdf(@RequestHeader("Authorization") String Authorization,@RequestParam int LanguageID, @RequestParam String receiptID) {

		if (!Authorization.equals("Q2FnbCQyMDIz")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Responce.builder().error(Boolean.TRUE).Data(null).message("UNAUTHORIZED").build());
		}
		
		Map<Integer, String> LanguageIDs = new HashMap<>();
		LanguageIDs.put(1, "Kannada");
		LanguageIDs.put(2, "Tamil");
		LanguageIDs.put(3, "Hindi");
		LanguageIDs.put(4, "Marati");
		LanguageIDs.put(5, "English");
		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.FALSE).Data("https://reports.grameenkoota.in/Branch_Report/frameset?__report=LCS_"
						+ LanguageIDs.get(LanguageID) + ".rptdesign&__format=pdf&lid=" + receiptID + "").message(LanguageIDs.get(LanguageID)+" Branch_ReportPDF-URl:"+receiptID).build());

	}
}
