package com.cag.twowheeler.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cag.twowheeler.dto.BranchDto;
import com.cag.twowheeler.dto.MainDealerDetailsDto;
import com.cag.twowheeler.dto.SubDealerDetailsDto;
import com.cag.twowheeler.dto.VehicleVariantDto;
import com.cag.twowheeler.entity.Branch;
import com.cag.twowheeler.entity.StateAbbreviation;
import com.cag.twowheeler.repository.BranchRepository;
import com.cag.twowheeler.repository.DistrictRepository;
import com.cag.twowheeler.repository.MainDealerRepository;
import com.cag.twowheeler.repository.StateAbbreviationRepository;
import com.cag.twowheeler.repository.VehicalOemRepository;
import com.cag.twowheeler.responce.responce;
import com.cag.twowheeler.service.TwoWheelerDealerService;

/**
 * @author Ankeet G.
 */
@RestController
@CrossOrigin(origins = "*")
public class TwoWheelerDealerController {

	@Autowired
	TwoWheelerDealerService dealerService;

	@Autowired
	StateAbbreviationRepository stateeAbbreviationRepository;

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	MainDealerRepository mainDealerRepository;

	@Autowired
	VehicalOemRepository oemRepository;

	/**
	 * 
	 * @API Give State For ADD NEw Dealers
	 */
	@GetMapping("/allstatesdropdown")
	public ResponseEntity<responce> allStateDropDown() {
		List<String> state = Arrays.asList("KARNATAKA", "MAHARASHTRA", "TAMIL NADU");

		List<String> allStates = new ArrayList<>();
		List<StateAbbreviation> allStatesData = stateeAbbreviationRepository.findAll().stream()
				.filter(e -> state.contains(e.getState())).collect(Collectors.toList());
		allStatesData.stream().forEach(e -> {
			allStates.add(e.getState());
		});
		return ResponseEntity.status(HttpStatus.OK).body(
				responce.builder().error("FALSE").data(allStates).message("All 35 Sate Fetch Sucessfully ").build());
	}

	/**
	 * 
	 * @API Give OEM For ADD NEW Dealers
	 */
	@GetMapping("/alloem")
	public ResponseEntity<responce> getAllOem() {

		List<String> oems = oemRepository.findAll().stream().map(oem -> oem.getVehicalOem()).distinct()
				.collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK)
				.body(responce.builder().error("FALSE").data(oems).message("All Oem Fetch Sucessfully ").build());

	}

	@GetMapping("/alldistrictdropdown")
	public ResponseEntity<responce> getAllDistrict() {

		return ResponseEntity.status(HttpStatus.OK)
				.body(responce
						.builder().error("FALSE").data(districtRepository.findAll().stream()
								.map(data -> data.getDistrict()).distinct().collect(Collectors.toList()))
						.message("All District Fetch Sucessfully ").build());

//		return ResponseEntity.status(HttpStatus.OK)
//				.body(responce.builder().error("FALSE").data(branchRepository.findAll().stream().map(district -> {
//					String[] split = district.getRegion().split(" ");// split method use to remove region Word
//					return split[0];
//				}).distinct().collect(Collectors.toList())).message("All District Fetch Sucessfully ").build());
	}

	/**
	 * @Get OEM For Filter..!
	 */
	@GetMapping("/getoems")
	public ResponseEntity<responce> getAvalialeOem() {
		List<String> allAvaliableOem = dealerService.getOem();

		return ResponseEntity.status(HttpStatus.OK)
				.body(responce.builder().error("FALSE").message("ALL Existing STATES").data(allAvaliableOem).build());

	}

	/**
	 * @api used for Existing State for filter..!
	 * 
	 */
	@GetMapping("/statedropdown")
	public ResponseEntity<responce> dropDownState(@RequestParam String oem) {
		List<String> states = dealerService.getStates(oem);
		if (!states.isEmpty())
			return ResponseEntity.status(HttpStatus.OK).body(
					responce.builder().error("false").data(states).message("All States Fetch Sucessfully").build());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(responce.builder().error("false").data("--NA--").message("State Not Preseant").build());
	}

	@GetMapping("/districtdropdown")
	public ResponseEntity<responce> dropDownDistrict(@RequestParam String state) {
		List<String> districts = dealerService.getDistricts(state);
		if (!districts.isEmpty())
			return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("false").data(districts)
					.message("All districts Fetch Sucessfully").build());	

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(responce.builder().error("false").data("--NA--").message("districts Not Preseant").build());
	}

//=====================================BRANCHES============================================================

	// ===================Branch Dropdown===========================================

	@GetMapping("/regiondropdown")
	public ResponseEntity<responce> getregion(@RequestParam String state) {

		List<Branch> branches = branchRepository.findAll();
		List<String> regions = new ArrayList<>();
		regions = branches.stream().filter(e -> e.getState().equalsIgnoreCase(state)).map(e -> e.getRegion()).distinct()
				.collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK)
				.body(responce.builder().error("False").data(regions).message(" ALL REGIONS").build());
	}

	@PostMapping("/areadropdown")
	public ResponseEntity<responce> getarea(@RequestParam String state, @RequestBody List<String> regions) {
		List<Branch> branches = branchRepository.findAll();
		List<String> area = new ArrayList<>();
		regions.stream().forEach(region -> {
			branches.stream().filter(
					branch -> branch.getRegion().equalsIgnoreCase(region) && branch.getState().equalsIgnoreCase(state))
					.forEach(branch -> area.add(branch.getArea()));
		});
		List<String> areas = area.stream().distinct().collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(
				responce.builder().error("False").data(areas).message(" ALL Area Base On Region and State").build());
	}

	@PostMapping("/addmainbranch")
	public ResponseEntity<responce> addMainBranches(@RequestParam String mainDealerID,
			@RequestBody List<BranchDto> branches) {
		String string = dealerService.addMainBranches(mainDealerID, branches);
		if (string.equalsIgnoreCase("Sucessfully Added"))
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("FALSE").message("SUCCESVFULLY ADD ..!").build());

		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(responce.builder().error("TRUE").message("NOT ADD ..!").build());

	}

	@DeleteMapping("/removemainbranch")
	public ResponseEntity<responce> removeMainBranches(@RequestParam String mainDealerID,
			@RequestBody List<BranchDto> branches) {
		Boolean responces = dealerService.removeMainBranches(mainDealerID, branches);
		if (responces)
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("FALSE").message("SUCCESVFULLY REMOVED ..!").data("REMOVE").build());

		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(responce.builder().error("TRUE").message("NOT REMOVED ..!").data("NOT REMOVE").build());

	}

	@GetMapping("/showmainbranches")
	public ResponseEntity<responce> showMainBranches(@RequestParam String mainDealerID) {
		return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("FALSE").message("SUCESSFULLY FETCH")
				.data(dealerService.showMainBranches(mainDealerID)).build());
	}

	@GetMapping("/showmainbrancheswithfilter")
	public ResponseEntity<responce> showMainBranchesWithfilter(@RequestParam String mainDealerID,
			@RequestParam String region, @RequestParam String area) {
		Set<BranchDto> branches = dealerService.showMainBranches(mainDealerID, region, area);
		if (!branches.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("FALSE").message("SUCESSFULLY FETCH").data(branches).build());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(responce.builder().error("TRUE").message("NOT FETCH").build());
	}

	@GetMapping("/showavaliablemainbranches")
	public ResponseEntity<responce> showAvaliableMainBranches(String mainDealerID) {
		return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("FALSE").message("SUCESSFULLY FETCH")
				.data(dealerService.showAvaliableMainBranches(mainDealerID)).build());

	}

	// ======================

	/**
	 * @API Use To Add All Branches Base on Main Dealer
	 */
	@PostMapping("/addallmainbranches")
	public ResponseEntity<responce> addAllMainBranches(@RequestParam String mainDealerID,
			@RequestParam String subDealerID) {
		String responces = dealerService.addAllMainBranches(mainDealerID, subDealerID);
		if (responces.equalsIgnoreCase("Sucessfully Added"))
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("FALSE").message("SUCCESVFULLY ADD ..!").build());

		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(responce.builder().error("TRUE").message("NOT ADD ..!").build());
	}

	@PostMapping("/addsubbranch")
	public ResponseEntity<responce> addSubBranches(@RequestParam String subDealerID,
			@RequestBody List<BranchDto> branches) {
		String responces = dealerService.addSubBranches(subDealerID, branches);
		if (responces.equalsIgnoreCase("Sucessfully Added"))
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("FALSE").message("SUCCESVFULLY ADD ..!").build());

		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(responce.builder().error("TRUE").message("NOT ADD ..!").build());
	}

	@DeleteMapping("/removesubbranch")
	public ResponseEntity<responce> removeSubBranches(@RequestParam String subDealerID,
			@RequestBody List<BranchDto> branches) {
		Boolean response = dealerService.removeSubBranches(subDealerID, branches);
		if (response)
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("False").message("REMOVED SUCESSFULLY").build());

		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(responce.builder().error("TRUE").message("NOT REMOVED").build());
	}

	@GetMapping("/showsubbranches")
	public ResponseEntity<responce> showSubBranches(@RequestParam String subDealerID) {
		return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("FALSE").message("SUCESSFULLY GET..!")
				.data(dealerService.showSubBranches(subDealerID)).build());
	}

	@GetMapping("/showsubbrancheswithfilter")
	public ResponseEntity<responce> showSubBranches(@RequestParam String subDealerID, @RequestParam String region,
			@RequestParam String area) {
		Set<BranchDto> branches = dealerService.showSubBranches(subDealerID, region, area);
		if (!branches.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("FALSE").message("SUCESSFULLY GET..!").data(branches).build());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(responce.builder().error("TRUE").message("NOT FOUND ..!").data("NOT FOUND").build());
	}

	@GetMapping("/showavaliablesubbranches")
	public ResponseEntity<responce> showAvaliableSubBranches(@RequestParam String subDealerID) {
		Set<BranchDto> showAvaliableSubBranches = dealerService.showAvaliableSubBranches(subDealerID);
		return ResponseEntity.status(HttpStatus.OK).body(
				responce.builder().error("FALSE").message("SUCESSFULLY GET..!").data(showAvaliableSubBranches).build());

	}

	// =========================DOWNLOAD EXCEL=============================

	/**
	 * @Generate Excel file API
	 */

	String district;
	int count;

	@GetMapping("/downloadexcel")
	public ResponseEntity<InputStreamResource> getExcel() throws IOException {

		List<MainDealerDetailsDto> exceldata = dealerService.getExcelFile();
		if (!exceldata.isEmpty()) {
			// create workbook and sheet
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("My Data");

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
			cell0.setCellValue("Area");

			XSSFCell cell = header.createCell(1);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("Manufacturer Name");

			XSSFCell cell2 = header.createCell(2);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("Dealer Name");

			XSSFCell cell3 = header.createCell(3);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("Dealer Type");

			XSSFCell cell4 = header.createCell(4);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("Sub-Dealer ID");

			XSSFCell cell5 = header.createCell(5);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("Main Dealer ID");

			XSSFCell cell6 = header.createCell(6);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("State");

			XSSFCell cell7 = header.createCell(7);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("District");

			XSSFCell cell8 = header.createCell(8);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("Town/City/Village");

			XSSFCell cell9 = header.createCell(9);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("PinCode");

			XSSFCell cell10 = header.createCell(10);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("Email-ID");

			XSSFCell cell11 = header.createCell(11);
			cell11.setCellStyle(cellStyle);
			cell11.setCellValue("Contact No:");

			XSSFCell cell12 = header.createCell(12);
			cell12.setCellStyle(cellStyle);
			cell12.setCellValue("Alternate Contact No:");

			XSSFCell cell13 = header.createCell(13);
			cell13.setCellStyle(cellStyle);
			cell13.setCellValue("Contact person Name");

			XSSFCell cell14 = header.createCell(14);
			cell14.setCellStyle(cellStyle);
			cell14.setCellValue("Mobile No:");

			XSSFCell cell15 = header.createCell(15);
			cell15.setCellStyle(cellStyle);
			cell15.setCellValue("Dealer PAN No:");

			XSSFCell cell16 = header.createCell(16);
			cell16.setCellStyle(cellStyle);
			cell16.setCellValue("Dealer GST No:");

			XSSFCell cell17 = header.createCell(17);
			cell17.setCellStyle(cellStyle);
			cell17.setCellValue("Dealer Bank Name");

			XSSFCell cell18 = header.createCell(18);
			cell18.setCellStyle(cellStyle);
			cell18.setCellValue("Branch Name");

			XSSFCell cell19 = header.createCell(19);
			cell19.setCellStyle(cellStyle);
			cell19.setCellValue("Bank A/c No:");

			XSSFCell cell20 = header.createCell(20);
			cell20.setCellStyle(cellStyle);
			cell20.setCellValue("IFSC Code");

			XSSFCell cell21 = header.createCell(21);
			cell21.setCellStyle(cellStyle);
			cell21.setCellValue("Main Dealer Account holder Name as per Bank A/c");

			XSSFCell cell22 = header.createCell(22);
			cell22.setCellStyle(cellStyle);
			cell22.setCellValue("Payment Eligible");

			XSSFCell cell23 = header.createCell(23);
			cell23.setCellStyle(cellStyle);
			cell23.setCellValue("Activation date");

			XSSFCell cell24 = header.createCell(24);
			cell24.setCellStyle(cellStyle);
			cell24.setCellValue("Expiry date");

			XSSFCell cell25 = header.createCell(25);
			cell25.setCellStyle(cellStyle);
			cell25.setCellValue("Updated User");

			XSSFCell cell26 = header.createCell(26);
			cell26.setCellStyle(cellStyle);
			cell26.setCellValue("Updated Date");

			XSSFCell cell27 = header.createCell(27);
			cell27.setCellStyle(cellStyle);
			cell27.setCellValue("Penny Check Status");

			XSSFCell cell28 = header.createCell(28);
			cell28.setCellStyle(cellStyle);
			cell28.setCellValue("TimeZone");

			// create data rows
			int rowNum = 1;
			for (MainDealerDetailsDto item : exceldata) {
				XSSFRow row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(item.getAddressDetails().trim());
				row.createCell(1).setCellValue(item.getMainDealerManufacturerName().trim());
				row.createCell(2).setCellValue(item.getMainDealerName().trim());
				row.createCell(3).setCellValue("MAIN");
				row.createCell(4).setCellValue("-----");
				row.createCell(5).setCellValue(item.getMainDealerID());
				// District Logic
				district = "";
				count = 1;
				item.getDistrict().stream().forEach(e -> {
					if (item.getDistrict().size() == count)
						district += e;
					else
						district += e + ',';
					count++;
				});
				row.createCell(6).setCellValue(item.getState().trim());
				row.createCell(7).setCellValue(district);
				row.createCell(8).setCellValue(item.getCity().trim());
				row.createCell(9).setCellValue(item.getPinCode());
				row.createCell(10).setCellValue(item.getMainDealerMailID());
				row.createCell(11).setCellValue(item.getMainDealerContactNumber());
				row.createCell(12).setCellValue(item.getMainDealerAlternateContactNumber());
				row.createCell(13).setCellValue(item.getMainDealerContactPersonName().trim());
				row.createCell(14).setCellValue(item.getContactPersonMobile());
				row.createCell(15).setCellValue(item.getMainDealerPanNumber());
				row.createCell(16).setCellValue(item.getMainDealerGstNumber());
				row.createCell(17).setCellValue(item.getMainDealerBankName().trim());
				row.createCell(18).setCellValue(item.getMainDealerBankBranchName().trim());
				row.createCell(19).setCellValue("" + item.getMainDealerBankAccNumber() + "");
				row.createCell(20).setCellValue(item.getMainDealerIfsc().trim());
				row.createCell(21).setCellValue(item.getMainDealerAccountHolderName().trim());
				row.createCell(22).setCellValue(item.getPaymentEligible());
				row.createCell(23).setCellValue(item.getMainDealerActivationData().toString().replace("-", ""));
				row.createCell(24).setCellValue(item.getMainDealerExpireData());
				row.createCell(25).setCellValue(item.getUpdaterUserID());
				if (item.getUpdaterDate() != null) {
					row.createCell(26).setCellValue(item.getUpdaterDate().toString().replace("-", ""));
				}
				row.createCell(27).setCellValue(item.getMainDealerPaniniCheck());
				row.createCell(28).setCellValue(item.getTimeZone());

				for (SubDealerDetailsDto subitem : item.getSubDealerDetailsDtos()) {
					XSSFRow subrow = sheet.createRow(rowNum++);
					subrow.createCell(0).setCellValue(subitem.getAddressDetails().trim());
					subrow.createCell(1).setCellValue(subitem.getSubDealerManufacturerName().trim());
					subrow.createCell(2).setCellValue(subitem.getSubDealerName().trim());
					subrow.createCell(3).setCellValue("SUB");
					subrow.createCell(4).setCellValue(subitem.getMainDealer().getMainDealerID());
					subrow.createCell(5).setCellValue(subitem.getSubDealerID());
					// District Logic
					district = "";
					count = 1;
					subitem.getDistrict().stream().forEach(e -> {
						if (subitem.getDistrict().size() == count)
							district += e;
						else
							district += e + ',';
						count++;
					});
					
					subrow.createCell(6).setCellValue(subitem.getState().trim());
					subrow.createCell(7).setCellValue(district);
					subrow.createCell(8).setCellValue(subitem.getCity().trim());
					subrow.createCell(9).setCellValue(subitem.getPinCode());
					subrow.createCell(10).setCellValue(subitem.getSubDealerMailID());
					subrow.createCell(11).setCellValue(subitem.getSubDealerContactNumber());
					subrow.createCell(12).setCellValue(subitem.getSubDealerAlternateContactNumber());
					subrow.createCell(13).setCellValue(subitem.getSubDealerContactPersonName().trim());
					subrow.createCell(14).setCellValue(subitem.getContactPersonMobile());
					subrow.createCell(15).setCellValue(subitem.getSubDealerPanNumber());
					subrow.createCell(16).setCellValue(subitem.getSubDealerGstNumber());
					subrow.createCell(17).setCellValue(subitem.getSubDealerBankName().trim());
					subrow.createCell(18).setCellValue(subitem.getSubDealerBankBranchName().trim());
					subrow.createCell(19).setCellValue("" + subitem.getSubDealerBankAccNumber() + "");
					subrow.createCell(20).setCellValue(subitem.getSubDealerIfsc());
					subrow.createCell(21).setCellValue(subitem.getSubDealerAccountHolderName().trim());
					subrow.createCell(22).setCellValue(subitem.getPaymentEligible());
					subrow.createCell(23)
							.setCellValue(subitem.getSubDealerActivationData().toString().replace("-", ""));
					subrow.createCell(24).setCellValue(subitem.getSubDealerExpireData().toString().replace("-", ""));
					subrow.createCell(25).setCellValue(subitem.getUpdaterUserID());
					if (subitem.getUpdaterDate() != null) {
						subrow.createCell(26).setCellValue(subitem.getUpdaterDate().toString().replace("-", ""));
					}
					subrow.createCell(27).setCellValue(subitem.getSubDealerPaniniCheck());
					subrow.createCell(28).setCellValue(item.getTimeZone().trim());
				}
			}
			// create Excel file
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			workBook.write(outputStream);
			byte[] byteArray = outputStream.toByteArray();
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
			InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
			// create ResponseEntity with Excel file
//			ByteArrayResource arrayResource = new ByteArrayResource(byteArray);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Dealers.xlsx")
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(inputStreamResource);
		}
		return null;
	}

	// ======================DEALERS APIS==============================

	/**
	 * @param mainDealerId
	 * @param subDealerDetailsDto
	 * @API is for Adding SubDealer
	 */
	@PostMapping("/addsubdealer")
	public ResponseEntity<responce> addSubDealer(Authentication authentication, @RequestParam String mainDealerID,
			@RequestBody SubDealerDetailsDto subDealerDetailsDto) {
		Boolean check = dealerService.addSubDealers(mainDealerID, subDealerDetailsDto, authentication.getName());
		if (check)
			return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("False").data("--NA--")
					.message("Sub Dealers Data Add Sucessfully").build());

		return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(responce.builder().error("True")
				.data("Please Mention Maindealer for Sub Dealer").message("Sub Dealers Data Not Added ").build());
	}

	/**
	 * @ API FOR ADDING MAIN DEALER
	 */
	@PostMapping("/addmaindealer")
	public ResponseEntity<responce> addMainDealer(Authentication authentication,
			@RequestBody MainDealerDetailsDto dealerDetailsDto) {
		String addMainDealer = dealerService.addMainDealer(dealerDetailsDto, authentication.getName());
		if (addMainDealer != null)
			return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("False").data(addMainDealer)
					.message("Main Dealers Data Add Sucessfully").build());

		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(responce.builder().error("True").data("--NA--").message("Main Dealers Data Not Add ").build());
	}

	/**
	 * @param mainDealerId
	 * @Get All SubDealer Base on MainDealer
	 */
	@GetMapping("/getsubdealers")
	public ResponseEntity<responce> getSubDealers(@RequestParam String mainDealerID) {
		List<SubDealerDetailsDto> allSubDealerDetails = dealerService.getSubDealerDetails(mainDealerID);
		if (allSubDealerDetails.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(responce.builder().error("True").data("--NA--").message("No Sub Dealer Present").build());

		return ResponseEntity.status(HttpStatus.OK)
				.body(responce.builder().error("False")
						.data(allSubDealerDetails.stream()
								.sorted(Comparator.comparing(SubDealerDetailsDto::getSubDealerActivationData))
								.collect(Collectors.toList()))
						.message("Sub Dealer Fetch sucessfully").build());
	}

	@GetMapping("/getmaindealers") // In parameter "All" Or "DealerID"
	public ResponseEntity<responce> getMainDealers(@RequestParam String flag) {
		List<MainDealerDetailsDto> dealersDetails = dealerService.getMainDealersDetails(flag);
		if (dealersDetails.size() != 0)
			// To sort Base On Activation using Stream API
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("False")
							.data(dealersDetails.stream()
									.sorted(Comparator.comparing(MainDealerDetailsDto::getMainDealerActivationData))
									.collect(Collectors.toList()))
							.message("all dealers data").build());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responce.builder().data("NO Dealers Data Found")
				.error("True").message("No Sub Dealer Present").build());
	}

//	@CrossOrigin(origins = "http://localhost:9666")
	@PutMapping("/editmaindealer")
	public ResponseEntity<responce> editMainDealer(Authentication authentication, @RequestParam String mainDealerID,
			@RequestBody MainDealerDetailsDto mainDetailsDto) {
		Boolean check = dealerService.editMainDealer(mainDealerID, mainDetailsDto, authentication.getName());
		if (check)
			return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("False").data("--NA--")
					.message("Main Dealers Data Edit Sucessfully").build());

		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(responce.builder().error("True").data("--NA--").message("Main Dealers Data Not Edit ").build());
	}

	@PutMapping("/editsubdealer")
	public ResponseEntity<responce> editSubDealer(Authentication authentication, @RequestParam String subDealerID,
			@RequestBody SubDealerDetailsDto subDetailsDto) {
		Boolean check = dealerService.editSubDealer(subDealerID, subDetailsDto, authentication.getName());

		if (check) {
			return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("False").data("--NA--")
					.message("Sub Dealers Data Edit Sucessfully").build());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(
					responce.builder().error("True").data("--NA--").message("Sub Dealers Data Not Edit ").build());
		}

	}

	// ==============================VARIANTS========================================================

	/**
	 * @ Variants to be Add
	 */
	@GetMapping("/mainvariants")
	public ResponseEntity<responce> getMainVariants(@RequestParam String mainDealerID) {
		return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("false")
				.data(dealerService.mainVariants(mainDealerID).stream()
						.sorted(Comparator.comparing(VehicleVariantDto::getVariantID)).collect(Collectors.toList()))
				.message("Avaliable Variants..! ").build());
	}

	/**
	 * @ Variants They Have
	 */
	@GetMapping("/avaliablemainvariants")
	public ResponseEntity<responce> getAvaliableMainVariants(@RequestParam String mainDealerID) {
		return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("FALSE")
				.message("All Avaliable Varieants")
				.data(dealerService.avaliableMainVariants(mainDealerID).stream()
						.sorted(Comparator.comparing(VehicleVariantDto::getVariantID)).collect(Collectors.toList()))
				.build());
	}

	/**
	 * @Api is use Adding Variant for MainDealer
	 */
	@PostMapping("/addmainvariant")
	public ResponseEntity<responce> addMainVariants(@RequestParam String MainDealerID,
			@RequestBody ArrayList<VehicleVariantDto> variants) {

		boolean check = dealerService.addVeriantsMian(MainDealerID, variants);
		if (check)
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("False").data("--NA--").message("sucessfull ").build());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(responce.builder().error("True").data("--NA--").message("Not added").build());
	}

	@DeleteMapping("/removemainvariant")
	public ResponseEntity<responce> removeMainVariants(@RequestParam String mainDealerID,
			@RequestBody ArrayList<VehicleVariantDto> variants) {

		Boolean response = dealerService.removeMainVariant(mainDealerID, variants);
		if (response)
			return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("FALSE")
					.message("VARIANT REMOVED SUCCESVFULLY ..!").data("REMOVED").build());
		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(responce.builder().error("TRUE").message("VARIANT NOT REMOVED ..!").data("NOT REMOVED").build());
	}
//=============================

	/**
	 * @ Variants to be Add
	 */
	@GetMapping("/subvariants")
	public ResponseEntity<responce> getSubVariants(@RequestParam String subDealerID) {
		return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("False")
				.data(dealerService.subVariants(subDealerID).stream()
						.sorted(Comparator.comparing(VehicleVariantDto::getVariantID)).collect(Collectors.toList()))
				.message("Variant Are Avaliable..!").build());
	}

	/**
	 * @ Variants They Have
	 */
	@GetMapping("/avaliablesubvariants")
	public ResponseEntity<responce> avaliableSubVariants(@RequestParam String subDealerID) {
		return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("FALSE")
				.message("All Avaliables Variants")
				.data(dealerService.avaliableSubVariants(subDealerID).stream()
						.sorted(Comparator.comparing(VehicleVariantDto::getVariantID)).collect(Collectors.toList()))
				.build());
	}

	/**
	 * @Api is use Adding Variant for SubDealer
	 */
	@PostMapping("/addsubvariant")
	public ResponseEntity<responce> addSubVariants(@RequestParam String subDealerID,
			@RequestBody ArrayList<VehicleVariantDto> variants) {

		boolean check = dealerService.addVariantSub(subDealerID, variants);
		if (check)
			return ResponseEntity.status(HttpStatus.OK).body(
					responce.builder().error("False").data("--NA--").message("Sucessfully Variant Added").build());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(responce.builder().error("True").data("--NA--").message("Variant Not Added").build());
	}

	@DeleteMapping("/removesubvariant")
	public ResponseEntity<responce> removeSubVariants(@RequestParam String subDealerID,
			@RequestBody ArrayList<VehicleVariantDto> variants) {
		Boolean response = dealerService.removeSubVariant(subDealerID, variants);
		if (response)
			return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("FALSE")
					.message("VARIANT REMOVED SUCCESVFULLY ..!").data("REMOVED").build());
		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(responce.builder().error("TRUE").message("VARIANT NOT REMOVED ..!").data("NOT REMOVED").build());

	}

}
