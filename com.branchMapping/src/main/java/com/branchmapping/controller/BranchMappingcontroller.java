package com.branchmapping.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.branchmapping.repository.BranchMappingRepo;

@RestController
public class BranchMappingcontroller {

	@Autowired
	BranchMappingRepo branchMappingRepo;
	int rno = 1;

	@GetMapping("/getbranchMapping")
	public ResponseEntity<InputStreamResource> getBranchMapping() throws IOException {
		// Create Excel Structure
		SXSSFWorkbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = workBook.createSheet("My Data");

		SXSSFRow header = sheet.createRow(0);
		SXSSFCell cell1 = header.createCell(0);
		cell1.setCellValue("Branch_ID");

		SXSSFCell cell2 = header.createCell(1);
		cell2.setCellValue("DealerID");

		rno = 1;
		branchMappingRepo.findAll().stream().forEach(e -> {
			Arrays.asList(e.getDealersIDS().split(",")).stream().forEach(dealers -> {
				SXSSFRow row = sheet.createRow(rno++);
				row.createCell(0).setCellValue(e.getBranchID());
				row.createCell(1).setCellValue(dealers);
				System.out.println(e.getBranchID()+" "+dealers);
			});
		});
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workBook.write(outputStream);
		workBook.dispose();
		byte[] byteArray = outputStream.toByteArray();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
		InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Data.xlsx")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(inputStreamResource);
	}
}
