package com.cag.twowheeler.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cag.twowheeler.dto.CheckerDealersDto;
import com.cag.twowheeler.dto.CustomVariantInfo;
import com.cag.twowheeler.dto.InseartVehicle;
import com.cag.twowheeler.dto.VariantDetailsDto;
import com.cag.twowheeler.dto.VariantExcelDto;
import com.cag.twowheeler.dto.VehicalsAllData;
import com.cag.twowheeler.dto.VehicleVariantDto;
import com.cag.twowheeler.entity.ApiCallRecords;
import com.cag.twowheeler.entity.Branch;
import com.cag.twowheeler.entity.MainDealer;
import com.cag.twowheeler.entity.SubDealer;
import com.cag.twowheeler.entity.VehicalPrice;
import com.cag.twowheeler.entity.VehicalVariant;
import com.cag.twowheeler.repository.ApiCallRepository;
import com.cag.twowheeler.repository.BranchRepository;
import com.cag.twowheeler.repository.MainDealerRepository;
import com.cag.twowheeler.repository.SubDealerRepository;
import com.cag.twowheeler.repository.VehicalOemRepository;
import com.cag.twowheeler.repository.VehicalPriceRepository;
import com.cag.twowheeler.repository.VehicalVariantRepository;
import com.cag.twowheeler.responce.responce;
import com.cag.twowheeler.service.TwoWheelerService;

/**
 * @author Ankeet G.
 */
@RestController
@CrossOrigin(origins = "*")
//@RequestMapping("/api/cag1/twowheelerlone")
public class TwoWheelerController {

	@Autowired
	ApiCallRepository apirecords;
	
	@Autowired
	private TwoWheelerService service;

	@Autowired
	VehicalVariantRepository variantRepository;

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	VehicalOemRepository oemRepository;

	@Autowired
	VehicalPriceRepository priceRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	MainDealerRepository mainDealerRepository;

	@Autowired
	SubDealerRepository subDealerRepository;

	public final String STROAGE_PATH = "D:\\Twowheeler_Dealer_managment_Documents\\Vehicle_Variant_Images\\";

	/**
	 * @ This APi Use To Give All Vehicle Data(Sorted Base on Vehicle_Name )
	 */
	@GetMapping("/vehicaldata")
	public ResponseEntity<responce> getVehicalData() {
		List<VehicalsAllData> allVehicalData = service.getAllVehicalData();
		if (!allVehicalData.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("false").data(allVehicalData).message("All vehical Data").build());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(responce.builder().error("True").data(allVehicalData).message("vehical Data Not Exist").build());
	}

	/**
	 * @ Update API
	 */
	@PutMapping("/editdata")
	public ResponseEntity<responce> editvehicleData(Authentication authentication, @RequestParam String variantID,
			@RequestBody VehicalsAllData data) {
		
		apirecords.save(ApiCallRecords.builder().apiname("editvehicleData")
				.timeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()))
				.msg(authentication.getName()+"=>"+variantID ).build());
		
		Boolean responces = service.editVehicleData(variantID, data, authentication.getName());
		if (responces)
			return ResponseEntity.status(HttpStatus.OK).body(
					responce.builder().error("False").data("Updated...!").message("Data Updated sucessfully").build());

		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
				.body(responce.builder().error("True").message("Data Not Updated \n Vehicle Not Exist").build());
	}

	/**
	 * @ Vehicle_State filter DropDown
	 */
	@GetMapping("/vehicleallstate")
	public ResponseEntity<responce> allStates() {
//		List<String> state=Arrays.asList("KARNATAKA","MAHARASHTRA","TAMIL NADU","KERALA");
//		.stream().filter(e-> state.contains(e)).collect(Collectors.toList());
		List<String> States = service.getAllStates();
		if (!States.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("False").message("All Vehical_States").data(States).build());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(responce.builder().error("True").message("Vehical_State Not Found").build());
	}

	/**
	 * @ Vehicle_OEm filter DropDown
	 */
	@GetMapping("/vehiclealloem")
	public ResponseEntity<responce> allOemBaseOnstate(@RequestParam String state) {
		List<String> oems = service.getOembaseOnState(state);
		if (!oems.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("False").message("All Oems Base on State...!").data(oems).build());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(responce.builder().error("True").message("Vehical_Oems Not Found").build());
	}

	/**
	 * @ Vehicle_Model filter DropDown
	 */
	@GetMapping("/vehicleModels")
	public ResponseEntity<responce> allModelBaseOnOem(@RequestParam String oem) {
		List<String> models = service.getModelsBaseOnOem(oem);
		if (!models.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("False").message("All models Base on Oem!").data(models).build());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(responce.builder().error("True").message("Vehical_Models Not Found").build());
	}

	/**
	 * @ Base on OEM and state Get All a Variants.
	 * 
	 * @param oem
	 * @return
	 */

	@GetMapping("/vehiclevariants")
	public ResponseEntity<responce> allvariants(@RequestParam String oem, @RequestParam String state) {

		List<VehicalPrice> priceDate = priceRepository.findByStateAndOem(state, oem);

		if (!priceDate.isEmpty() & priceDate != null)
			return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("False")
					.message("All Variants Base on [" + oem + "] Oem!")
					.data(priceDate.stream()
							.map(e -> VariantDetailsDto.builder().ExshowroomPrice(e.getExshowroomPrice())
									.model(e.getModel()).oem(e.getOem()).state(e.getState())
									.variantName(e.getVariantName()).vehicalMaxLoanAmount(e.getVehicalMaxLoanAmount())
									.vehicalOnRoadPrice(e.getVehicalOnRoadPrice()).build())
							.distinct().collect(Collectors.toList()))
					.build());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(responce.builder().error("True").message("Vehical_Variants Not Found").build());
	}

	/**
	 * DropDown APIS
	 */
	@GetMapping("/vehiclevariantdropdown")
	public ResponseEntity<responce> getVarieantDropDown(@RequestParam String vehicleModel) {
		List<String> varieantDropDown = service.getVarieantDropDown(vehicleModel);
		if (varieantDropDown.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(responce.builder().error("True").message("Vehical_variant Not Found").build());

		return ResponseEntity.status(HttpStatus.OK).body(
				responce.builder().error("False").message("All varients Base on Oem!").data(varieantDropDown).build());

	}

	/**
	 * @Vehicle_AllOEM Adding vehicle DropDown
	 * @Vehicle_AllState Adding vehicle DropDown WE are Using Existing API
	 */

	/**
	 * @Vehicle Delete API
	 */
	@DeleteMapping("/deletedata")
	public ResponseEntity<responce> deleteData(int id) {
		String msg = service.deleteData(id);
		return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("Read Message").message(msg).build());
	}

	/*
	 * New Vehicle Data Add
	 */
	@PostMapping("/insertvehicle")
	public ResponseEntity<responce> insertVehical(@RequestBody InseartVehicle vehicle, Authentication authentication) {
		
		apirecords.save(ApiCallRecords.builder().apiname("insertvehicle")
				.timeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()))
				.msg(authentication.getName()+"=>"+vehicle.toString()).build());
		
		String message = service.addVehicle(vehicle, authentication.getName());
		if (message.equals("Vehical Alrady Exist with Given State"))
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("True").message(message).data("--").build());
		else {

			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("false").message(message).data("--").build());
		}

	}

	// UPLOAD IMAGE FOR VARIANT ..!

	@GetMapping("/allvariants")
	public ResponseEntity<responce> AllgetVariantImages() {
		List<VehicalVariant> allvariant = variantRepository.findAll();

		List<VehicleVariantDto> Data = allvariant.stream()
				.map(variant -> VehicleVariantDto.builder().variantID(variant.getVehicalTypeid())
						.variantName(variant.getVehicalvariantName()).oem(variant.getOem().getVehicalOem())
						.fileName(variant.getFileName()).filePath(variant.getFilePath()).build())
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(responce.builder().error("FALSE").data(Data).message("Variant Data").build());
	}

	@PostMapping("/uploadVariantimage")
	public ResponseEntity<responce> uploadImage(@RequestBody MultipartFile file, String variantID)
			throws IllegalStateException, IOException {
		VehicalVariant vehicalVariant = variantRepository.findById(variantID).get();
		vehicalVariant.setFilePath(STROAGE_PATH + file.getOriginalFilename());
		vehicalVariant.setFileName(file.getOriginalFilename());
		file.transferTo(new File(STROAGE_PATH + file.getOriginalFilename()));
		variantRepository.save(vehicalVariant);
		return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("FALSE")
				.data(STROAGE_PATH + file.getOriginalFilename()).message("Image Uploaded").build());
	}

	@GetMapping("/viewvariantimage")
	public ResponseEntity<Resource> viewVariantImage(String variantID) {
		VehicalVariant vehicalVariant = variantRepository.findById(variantID).get();

		HttpHeaders headers = new HttpHeaders();

		File file = new File(vehicalVariant.getFilePath());
		Resource resource = new FileSystemResource(file);

		headers.setContentDisposition(ContentDisposition.inline().filename(vehicalVariant.getFileName()).build());
		headers.setContentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM));

		return ResponseEntity.ok().headers(headers).contentLength(file.length()).body(resource);
	}

	/**
	 * @API use for get All variants Base on State And model
	 */
	@GetMapping("/getspecificvariants")
	public ResponseEntity<responce> getVariantsbymodelandstate(@RequestParam String model, @RequestParam String state) {
		List<CustomVariantInfo> variants = new ArrayList<>();
		variants = service.getVariantBymodalAndState(model, state);
		if (!variants.isEmpty())
			return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("False").data(variants)
					.message("Sucessfully fetch Variants Base ON State And Vehiclemodel").build());
		return ResponseEntity.status(HttpStatus.OK)
				.body(responce.builder().error("True").data(null).message("No Variant Present...!").build());
	}

	@GetMapping("/getbranchstate")
	public ResponseEntity<responce> getBranchState(@RequestParam String branchID) {
		Optional<Branch> data = branchRepository.findById(branchID);
		if (data.isPresent()) {
			String state = data.get().getState();
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("false").data(state).message("State exist...!").build());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(responce.builder().error("True").data(null).message("state not exist...!").build());
		}
	}

//  ==========================EXCEL DOWNLOAD====================================

	@GetMapping("/downloadvariantsexcel")
	public ResponseEntity<InputStreamResource> getVariantsExcel(@RequestParam String base) throws IOException {
		List<VariantExcelDto> excelData = service.getVariantExcel(base);
		if (!excelData.isEmpty()) {
			// create workbook and sheet
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("variant_Mappiing_Excel");

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
			XSSFCell cell = header.createCell(0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("Dealer ID");

			XSSFCell cell2 = header.createCell(1);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("Manufatcurer/OEM Name");

			XSSFCell cell3 = header.createCell(2);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("variant ID");

			XSSFCell cell4 = header.createCell(3);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("Variant");

			XSSFCell cell5 = header.createCell(4);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("Unique vehicle ID");

			XSSFCell cell6 = header.createCell(5);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("On-Road Vehicle Price");

			XSSFCell cell7 = header.createCell(6);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("Ex-Showroom Price");

			XSSFCell cell8 = header.createCell(7);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("Price Activation date");

			XSSFCell cell9 = header.createCell(8);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("Price Expiry date");

			XSSFCell cell10 = header.createCell(9);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("Updated User");

			XSSFCell cell11 = header.createCell(10);
			cell11.setCellStyle(cellStyle);
			cell11.setCellValue("Updated Date");

			XSSFCell cell12 = header.createCell(11);
			cell12.setCellStyle(cellStyle);
			cell12.setCellValue("Time Zone");

			// create data rows
			int rowNum = 1;
			for (VariantExcelDto data : excelData) {
				XSSFRow row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(data.getDealerID().trim());
				row.createCell(1).setCellValue(data.getOem().trim());
				row.createCell(2).setCellValue(data.getVehicleVeriantID().trim());
				row.createCell(3).setCellValue(data.getVariantName().trim());
				row.createCell(4).setCellValue(data.getUniqueVehicleID());
				row.createCell(5).setCellValue(data.getOnRoadPrice());
				row.createCell(6).setCellValue(data.getExShowroomPrice());
				row.createCell(7).setCellValue(data.getPriceActivationDate().toString().replace("-", ""));
				row.createCell(8).setCellValue(data.getPriceExpireDate().toString().replace("-", ""));
				row.createCell(9).setCellValue(data.getUpdaterUser());
				if (data.getUpdatedDate() != null) {
					row.createCell(10).setCellValue(data.getUpdatedDate().toString().replace("-", ""));
				}
				row.createCell(11).setCellValue(data.getTimeZone().trim());
			}
			// create Excel file
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			workBook.write(outputStream);
			byte[] byteArray = outputStream.toByteArray();
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
			InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
			// create ResponseEntity with Excel file
//			    ByteArrayResource arrayResource = new ByteArrayResource(byteArray);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Dealers.xlsx")
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(inputStreamResource);
		}
		return null;
	}

	// ==============Amoga Apis=======================

	@GetMapping("/manufacturebybranchid")
	public ResponseEntity<responce> getOemByBranchID(@RequestParam String branchID) {
		Optional<Branch> branchOptional = branchRepository.findById(branchID);
		if (branchOptional.isPresent()) {
			Branch branch = branchOptional.get();
			List<String> queryForList = new ArrayList<>();
			queryForList = jdbcTemplate.queryForList(
					"SELECT distinct oem FROM vehical_price where state='" + branch.getState() + "'", String.class);
			return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("false").data(queryForList)
					.message(branch.getState() + " all Manufacture").build());
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(responce.builder().error("True").data(null).message("Manufacture not exist").build());
	}

	@GetMapping("/veriantinfo")
	public ResponseEntity<responce> getvariantsDetails(@RequestParam String branchID,
			@RequestParam String manufacture) {
		Optional<Branch> branchOptional = branchRepository.findById(branchID);
		if (branchOptional.isPresent()) {
			Branch branch = branchOptional.get();
			List<VehicalPrice> variantData = priceRepository.findByStateAndOem(branch.getState(), manufacture);
			if (!variantData.isEmpty() & variantData != null) {
				List<VariantDetailsDto> vData = variantData.stream().map(e -> {
					return VariantDetailsDto.builder().variantID(e.getVehicalPriceID())
							.ExshowroomPrice(e.getExshowroomPrice()).model(e.getModel()).oem(e.getOem())
							.state(e.getState()).variantName(e.getVariantName())
							.vehicalMaxLoanAmount(e.getVehicalMaxLoanAmount())
							.vehicalOnRoadPrice(e.getVehicalOnRoadPrice()).build();
				}).collect(Collectors.toList());
				return ResponseEntity.status(HttpStatus.OK)
						.body(responce.builder().error("False").data(vData).message("All Variant Details").build());
			}
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(responce.builder().error("True").data(null).message("Variant details not exist").build());
	}

	@GetMapping("/dealersinfo")
	public ResponseEntity<responce> getDealerInfo(@RequestParam String variantID, @RequestParam String branchID) {
		List<String> mainDealersID = jdbcTemplate.queryForList(
				"SELECT distinct main_dealerid FROM maindealers_branches where glbrancht24id='" + branchID + "'",
				String.class);
		List<String> subDealersID = jdbcTemplate.queryForList(
				"SELECT distinct sub_dealerid FROM subdealers_branches where glbrancht24id='" + branchID + "'",
				String.class);

		List<String> MV = jdbcTemplate.queryForList(
				"SELECT distinct main_dealerid FROM maindealers__vehicles where vehicle_veriant_id='" + variantID + "'",
				String.class);
		List<String> SV = jdbcTemplate.queryForList(
				"SELECT distinct sub_dealerid FROM subdealers__vehicles where vehicle_veriant_id='" + variantID + "'",
				String.class);
		
	
		
		
		List<CheckerDealersDto> Dealer = new ArrayList<>();
		if (!mainDealersID.isEmpty() & mainDealersID != null) {

			mainDealersID.stream().forEach(e -> {
				MainDealer mainDealer = mainDealerRepository.findById(e).get();
				
				if (MV.contains(e)) {
					Dealer.add(CheckerDealersDto.builder().addressDetails(mainDealer.getCity())
							.city(mainDealer.getArea()).contactPersonMobile(mainDealer.getContactPersonMobile())
							.DealerAccountHolderName(mainDealer.getAccountHolderName())
							.DealerAlternateContactNumber(mainDealer.getAlternateContactNumber())
							.DealerBankAccNumber(mainDealer.getBankAccNumber())
							.DealerBankBranchName(mainDealer.getBankBranchName())
							.DealerBankName(mainDealer.getBankName()).DealerContactNumber(mainDealer.getContactNumber())
							.DealerContactPersonName(mainDealer.getContactPersonName())
							.DealerGstNumber(mainDealer.getGstNumber()).DealerID(mainDealer.getMainDealerID())
							.DealerIfsc(mainDealer.getIfsc()).DealerMailID(mainDealer.getMailID())
							.DealerManufacturerName(mainDealer.getManufacturerName())
							.DealerName(mainDealer.getDealerName()).DealerPanNumber(mainDealer.getPanNumber())
							.dealerType(mainDealer.getDelearType()).pinCode(mainDealer.getPinCode())
							.state(mainDealer.getState()).build());
				}
			});

		}
		
		if (!subDealersID.isEmpty() & subDealersID != null) {

			subDealersID.stream().forEach(e -> {
				SubDealer subDealer = subDealerRepository.findById(e).get();
				if (SV.contains(e)) {
					Dealer.add(CheckerDealersDto.builder().addressDetails(subDealer.getCity()).city(subDealer.getArea())
							.contactPersonMobile(subDealer.getContactPersonMobile())
							.DealerAccountHolderName(subDealer.getAccountHolderName())
							.DealerAlternateContactNumber(subDealer.getAlternateContactNumber())
							.DealerBankAccNumber(subDealer.getBankAccNumber())
							.DealerBankBranchName(subDealer.getBankBranchName()).DealerBankName(subDealer.getBankName())
							.DealerContactNumber(subDealer.getContactNumber())
							.DealerContactPersonName(subDealer.getContactPersonName())
							.DealerGstNumber(subDealer.getGstNumber()).DealerID(subDealer.getSubDealerID())
							.DealerIfsc(subDealer.getIfsc()).DealerMailID(subDealer.getMailID())
							.DealerManufacturerName(subDealer.getManufacturerName())
							.DealerName(subDealer.getDealerName()).DealerPanNumber(subDealer.getPanNumber())
							.dealerType(subDealer.getDelearType()).pinCode(subDealer.getPinCode())
							.state(subDealer.getState()).build());
				}
			});
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(responce.builder().error("False").data(Dealer).message("Dealer details base on "+variantID+"-"+branchID).build());

	}

}
