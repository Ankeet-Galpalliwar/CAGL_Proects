package com.cag.twowheeler.controller;

/**
 * @author Ankeet G.
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cag.twowheeler.dto.VehicalsAllData;
import com.cag.twowheeler.entity.VehicalPrice;
import com.cag.twowheeler.repository.MainDealerRepository;
import com.cag.twowheeler.repository.SubDealerRepository;
import com.cag.twowheeler.repository.VehicalPriceRepository;
import com.cag.twowheeler.responce.responce;

@RestController
@CrossOrigin(origins = "*")
public class CheckerControllerVehicleManagement {

	@Autowired
	private VehicalPriceRepository priceRepository;

	@Autowired
	private MainDealerRepository mainDealerRepository;

	@Autowired
	private SubDealerRepository subDealerRepository;

	@GetMapping("/getCheckerVehicleData")
	public ResponseEntity<responce> getAllUpdatedVehicleData() {
		List<VehicalPrice> getAllvehicalData = priceRepository.findAll();
		List<VehicalsAllData> vehicalsAllDatas = new ArrayList<>();
		getAllvehicalData.stream().forEach(e -> {
			VehicalsAllData vehical = new VehicalsAllData();
			vehical.setVehicleId(e.getVehicalPriceID());
			vehical.setVehicleMaxLoanAmount(e.getVehicalMaxLoanAmount());
			vehical.setExShowroomPrice(e.getExshowroomPrice());
			vehical.setPriceActivationDate(e.getPriceActivationDate());
			vehical.setPriceExpireDate(e.getPriceExpireDate());
			vehical.setStatus(e.getStatus());
			vehical.setVehicalOnRoadPrice(e.getVehicalOnRoadPrice());
			vehical.setVehicleVariant(e.getVariantName());
			vehical.setVehicleModel(e.getModel());
			vehical.setVehicalOem(e.getOem());
			vehical.setVehicalState(e.getState());
			vehical.setUpdatedDate(e.getUpdatedDate());
			vehical.setUpdaterUserID(e.getUpdaterUserID());
			vehicalsAllDatas.add(vehical);
		});
		// sorting Logic
		Comparator<VehicalsAllData> sorted = new Comparator<VehicalsAllData>() {
			@Override
			public int compare(VehicalsAllData o1, VehicalsAllData o2) {
				return o1.getVehicleId().compareTo(o2.getVehicleId());
			}
		};
		// sorted list base on Vehicle Name
		List<VehicalsAllData> allVehicalData = vehicalsAllDatas.stream()
				.filter(e -> e.getStatus().equalsIgnoreCase("PENDING") || e.getStatus().equalsIgnoreCase("UPDATED"))
				.sorted(sorted).collect(Collectors.toList());
		if (allVehicalData.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(responce.builder().error("True").data("--NA--").message("Data Not Exist").build());

		return ResponseEntity.status(HttpStatus.OK)
				.body(responce.builder().error("False").data(allVehicalData).message("Data Fetch..!").build());

	}

	@PostMapping("/editOrApproveCheckerData")
	public ResponseEntity<responce> editOrApprovedVehicleData(Authentication authentication, @RequestParam String id,
			@RequestBody VehicalsAllData data) {
		Optional<VehicalPrice> vehicle = priceRepository.findById(id.trim());
		if (vehicle.isPresent()) {
			VehicalPrice v1 = vehicle.get();
			v1.setTimeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
			v1.setVehicalOnRoadPrice(data.getVehicalOnRoadPrice());
			v1.setExshowroomPrice(data.getExShowroomPrice());
			v1.setPriceActivationDate(data.getPriceActivationDate());
			v1.setPriceExpireDate(data.getPriceExpireDate());
			v1.setStatus(data.getStatus());
			v1.setVehicalMaxLoanAmount(data.getVehicleMaxLoanAmount());
			v1.setVariantName(data.getVehicleVariant().trim());
			v1.setUpdatedDate(LocalDate.now() + "");
			v1.setUpdaterUserID(v1.getUpdaterUserID());
			v1.setModel(data.getVehicleModel());
			VehicalPrice save = priceRepository.save(v1);
			if (save != null & data.getStatus().equalsIgnoreCase("APPROVED")) {
				mainDealerRepository.findAll().stream()
						.filter(e -> e.getState().equalsIgnoreCase(vehicle.get().getState())
								& e.getManufacturerName().equalsIgnoreCase(vehicle.get().getOem()))
						.forEach(main -> {
							List<VehicalPrice> veriants = main.getVeriants();
							if (!veriants.contains(vehicle.get())) {
								veriants.add(vehicle.get());
							}
							mainDealerRepository.save(main);
						});
				subDealerRepository.findAll().stream()
						.filter(e -> e.getState().equalsIgnoreCase(vehicle.get().getState())
								& e.getManufacturerName().equalsIgnoreCase(vehicle.get().getOem()))
						.forEach(sub -> {
							List<VehicalPrice> Veriants = sub.getVehicleVeriants();
							if (!Veriants.contains(vehicle.get())) {
								Veriants.add(vehicle.get());
							}
							subDealerRepository.save(sub);
						});

			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().error("False").data(data).message("Data updated!").build());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(responce.builder().error("True").data("--NA--").message("Data Not Updated").build());
	}
}
