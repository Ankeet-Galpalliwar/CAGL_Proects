package com.cag.twowheeler.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cag.twowheeler.dto.BranchExcelDto;
import com.cag.twowheeler.service.BranchService;

@RestController
@CrossOrigin(origins = "*")
public class BranchController {

	@Autowired
	BranchService branchService;

	@GetMapping("/getbranchexcel")
	public ResponseEntity<InputStreamResource> getBranchExcel() throws IOException {

		List<BranchExcelDto> branchMappingExcel = branchService.branchMappingExcel();
		// create workbook and sheet
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet("Branch Mapping");

		// custom text
		Font font = workBook.createFont();
		font.setFontName("Arial");
		font.setBold(false);
		font.setColor(IndexedColors.WHITE.getIndex());

		CellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// create header row

		XSSFRow header = sheet.createRow(0);

		XSSFCell cell0 = header.createCell(0);
		cell0.setCellStyle(cellStyle);
		cell0.setCellValue("BranchID");

		XSSFCell cell = header.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("Branch Name");

		XSSFCell cell2 = header.createCell(2);
		cell2.setCellStyle(cellStyle);
		cell2.setCellValue("Branch State");

		XSSFCell cell3 = header.createCell(3);
		cell3.setCellStyle(cellStyle);
		cell3.setCellValue("Dealear ID");

		// create data rows
		int rowNum = 1;
		for (BranchExcelDto item : branchMappingExcel) {
			XSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(item.getBranchID().trim());
			row.createCell(1).setCellValue(item.getBranchName().trim());
			row.createCell(2).setCellValue(item.getState().trim());
			row.createCell(3).setCellValue(item.getDealerID().trim());

		}

		// create Excel file
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		workBook.write(outputStream);
		byte[] byteArray = outputStream.toByteArray();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
		InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
		// create ResponseEntity with Excel file
//					ByteArrayResource arrayResource = new ByteArrayResource(byteArray);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Dealers.xlsx")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(inputStreamResource);

	}

}
