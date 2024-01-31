package com.cag.twowheeler.service.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
//import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cag.twowheeler.dto.CustomVariantInfo;
import com.cag.twowheeler.dto.InseartVehicle;
import com.cag.twowheeler.dto.VariantExcelDto;
import com.cag.twowheeler.dto.VehicalsAllData;
import com.cag.twowheeler.entity.MainDealer;
import com.cag.twowheeler.entity.SubDealer;
import com.cag.twowheeler.entity.VehicalPrice;
import com.cag.twowheeler.entity.VehicalVariant;
import com.cag.twowheeler.repository.MainDealerRepository;
import com.cag.twowheeler.repository.StateAbbreviationRepository;
import com.cag.twowheeler.repository.VehicalOemRepository;
import com.cag.twowheeler.repository.VehicalPriceRepository;
import com.cag.twowheeler.repository.VehicalVariantRepository;
import com.cag.twowheeler.service.TwoWheelerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TwoWheelerServiceImpl implements TwoWheelerService {

	@Autowired
	private VehicalPriceRepository priceRepository;
	@Autowired
	private VehicalOemRepository oemRepositoryy;
	@Autowired
	private VehicalVariantRepository variantRepository;
	@Autowired
	private StateAbbreviationRepository abbreviationRepository;
	@Autowired
	private MainDealerRepository mainDealerRepository;

	@Override
	public List<VehicalsAllData> getAllVehicalData() {
		List<VehicalPrice> getAllvehicalData = priceRepository.findAll();
		List<VehicalsAllData> vehicalsAllDatas = new ArrayList<>();
		getAllvehicalData.stream().forEach(e -> {
			VehicalsAllData vehical = new VehicalsAllData();
			vehical.setVehicleId(e.getVehicalPriceID());
			vehical.setUniqueVehicleID(e.getUniqueVehicleID());
			vehical.setVehicleMaxLoanAmount(e.getVehicalMaxLoanAmount());
			vehical.setVehicalOnRoadPrice(e.getVehicalOnRoadPrice());
			vehical.setExShowroomPrice(e.getExshowroomPrice());
			vehical.setPriceActivationDate(e.getPriceActivationDate());
			vehical.setPriceExpireDate(e.getPriceExpireDate());
			vehical.setStatus(e.getStatus());
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
		List<VehicalsAllData> allVehicalData = vehicalsAllDatas.stream().sorted(sorted).collect(Collectors.toList());
		return allVehicalData;
	}

	@Transactional
	@Override
	public Boolean editVehicleData(String id, VehicalsAllData data, String updaterUserName) {
		Optional<VehicalPrice> vehicle = priceRepository.findById(id.trim());
		if (vehicle.isPresent()) {
			VehicalPrice v1 = vehicle.get();
			VehicalVariant ExistVariantType = v1.getType();
			if (!data.getVehicleVariant().trim().equals(v1.getVariantName().trim())) {
				// MOdifying Other place variant name
				priceRepository.modifyVariantName(data.getVehicleVariant().trim(), v1.getVariantName());
				// modifying variant Object
				ExistVariantType.setVehicalvariantName(data.getVehicleVariant());
//				ExistVariantType.setFileName(data.getVehicleVariant() + ".jpg");
//				ExistVariantType.setFilePath("D:\\Twowheeler_Dealer_managment_Documents\\Vehicle_Variant_Images\\"
//						+ data.getVehicleVariant() + ".jpg");
				ExistVariantType = variantRepository.save(ExistVariantType);

			}
			VehicalPrice build = VehicalPrice.builder().ExshowroomPrice(data.getExShowroomPrice())
					.model(data.getVehicleModel()).oem(data.getVehicalOem())
					.priceActivationDate(data.getPriceActivationDate()).priceExpireDate(LocalDate.of(2099, 12, 31))
					.state(v1.getState()).status("UPDATED")
					.timeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()))
					.type(ExistVariantType).uniqueVehicleID(Integer.parseInt(id.substring(6)) + "")
					.updatedDate(LocalDate.now() + "").updaterUserID(updaterUserName)
					.variantName(data.getVehicleVariant().trim()).vehicalMaxLoanAmount(data.getVehicleMaxLoanAmount())
					.vehicalOnRoadPrice(data.getVehicalOnRoadPrice()).vehicalPriceID(id).build();

			BeanUtils.copyProperties(build, v1);
			priceRepository.save(v1);
			return true;
		}

		return false;
	}

	@Transactional
	@Override
	public String addVehicle(InseartVehicle insertVehicle, String user) {
		VehicalVariant Vehicle = variantRepository.findByVehicalvariantName(insertVehicle.getVehicleVariant());
		if (Vehicle == null) {

			String variantID = null;
			List<String> similarVariantID = new ArrayList<>();
			similarVariantID = oemRepositoryy.getSimilarVariantID(insertVehicle.getVehicleOem().substring(0, 3));
			if (!similarVariantID.isEmpty()) {
				List<Integer> idCount = similarVariantID.stream().map(data -> Integer.parseInt(data.substring(4)))
						.sorted().collect(Collectors.toList());
				int num = idCount.get(idCount.size() - 1) + 1;
				String count = "";
				if (num < 10)
					count += "0" + num;
				else
					count += "" + num;

				variantID = insertVehicle.getVehicleOem().substring(0, 3) + "-" + count;
			} else {
				variantID = insertVehicle.getVehicleOem().substring(0, 3) + "-01";
			}

			VehicalVariant newVariant = VehicalVariant.builder().fileName(insertVehicle.getVehicleVariant() + ".jpeg")
					.filePath("D:\\Twowheeler_Dealer_managment_Documents\\Vehicle_Variant_Images\\"
							+ insertVehicle.getVehicleVariant() + ".jpeg")
					.oem(oemRepositoryy.findByVehicalOem(insertVehicle.getVehicleOem().trim().toUpperCase()))
					.vehicalTypeid(variantID).vehicalvariantName(insertVehicle.getVehicleVariant())
					.vehicleModal(insertVehicle.getVehicleModel()).build();

			VehicalVariant save = variantRepository.save(newVariant);
			// Assigning Data IF not is not Exist...!
			Vehicle = save;

		}
		List<VehicalPrice> priceVariants = new ArrayList<>();
		priceVariants = priceRepository.findByVariantName(insertVehicle.getVehicleVariant());
		if (priceVariants.stream().anyMatch(e -> e.getState().equalsIgnoreCase(insertVehicle.getVehicleState()))) {
			return "Vehical Alrady Exist with Given State";
		} else {
			String variantPriceID = null;
			List<String> similarVariantpriceId = new ArrayList<>();
			similarVariantpriceId = priceRepository.getSimilarVariantpriceId(insertVehicle.getVehicleOem().trim(),
					insertVehicle.getVehicleState().trim());
			List<Integer> priceIdCount = similarVariantpriceId.stream().map(data -> Integer.parseInt(data.substring(6)))
					.sorted().collect(Collectors.toList());

			if (!priceIdCount.isEmpty()) {
				int num = priceIdCount.get(priceIdCount.size() - 1) + 1;
				String count = "";
				if (num < 10)
					count += "0" + num;
				else
					count += "" + num;
				variantPriceID = abbreviationRepository.findById(insertVehicle.getVehicleState()).get()
						.getStateAbbreviation() + insertVehicle.getVehicleOem().trim().toUpperCase().substring(0, 3)
						+ "-" + count;
			} else {
				variantPriceID = abbreviationRepository.findById(insertVehicle.getVehicleState()).get()
						.getStateAbbreviation() + insertVehicle.getVehicleOem().trim().toUpperCase().substring(0, 3)
						+ "-01";
			}

			VehicalPrice build = VehicalPrice.builder().ExshowroomPrice(insertVehicle.getExShowRoomPrice())
					.model(insertVehicle.getVehicleModel()).oem(insertVehicle.getVehicleOem())
					.priceActivationDate(insertVehicle.getPriceActivationDate())
					.priceExpireDate(LocalDate.of(2099, 12, 31)).state(insertVehicle.getVehicleState())
					.status("PENDING")
					.timeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()))
					.type(Vehicle).uniqueVehicleID(Integer.parseInt(variantPriceID.substring(6)) + "")
					.updatedDate(LocalDate.now() + "").updaterUserID(user)
					.variantName(insertVehicle.getVehicleVariant())
					.vehicalMaxLoanAmount(insertVehicle.getVehicleMaxLoanAmount())
					.vehicalOnRoadPrice(insertVehicle.getVehicalOnRoadPrice()).vehicalPriceID(variantPriceID).build();

			priceRepository.save(build);

			return "New Vehicle Data Add Successfully";

		}
	}

	@Override
	public String deleteData(int id) {
		// TODO Auto-generated method stub
		return "Logic Not preseant";
	}

	@Override
	public List<String> getAllStates() {
		return priceRepository.findAll().stream().map(data -> data.getState()).distinct().collect(Collectors.toList());
	}

	@Override
	public List<String> getOembaseOnState(String state) {
		return priceRepository.findAll().stream().filter(data -> data.getState().equalsIgnoreCase(state))
				.map(data -> data.getOem()).distinct().collect(Collectors.toList());
	}

	@Override
	public List<String> getModelsBaseOnOem(String oem) {
		return priceRepository.findAll().stream().filter(data -> (data.getOem().equalsIgnoreCase(oem.trim())))
				.map(data -> data.getModel()).distinct().collect(Collectors.toList());
	}

	@Override
	public List<String> getVarieantDropDown(String vehicleModel) {
		List<VehicalVariant> variants = variantRepository.findByVehicleModal(vehicleModel.trim().toUpperCase());
		if (variants != null)
			return variants.stream().map(e -> e.getVehicalvariantName()).distinct().collect(Collectors.toList());
		return new ArrayList<>();
	}

	@Override
	public List<CustomVariantInfo> getVariantBymodalAndState(String modal, String state) {

		List<VehicalPrice> variants = priceRepository.findByModelAndState(modal.trim().toUpperCase(),
				state.trim().toUpperCase());

		List<CustomVariantInfo> list = new ArrayList<CustomVariantInfo>();
		variants.stream().forEach(data -> list.add(CustomVariantInfo.builder().file_name(data.getType().getFileName())
				.file_path("http://caglcampaignleads.grameenkoota.in:8080/TwoWheelerLoan/viewvariantimage?variantID="
						+ data.getType().getVehicalTypeid())
				.max_loan_amount(data.getVehicalMaxLoanAmount()).on_road_price(data.getVehicalOnRoadPrice())
				.vehicalvariant_id(null).vehicalvariant_name(data.getVariantName()).vehicle_model_name(data.getModel())
				.build()));
		return list;

	}

	@Override
	public List<VariantExcelDto> getVariantExcel(String based) {
		List<VariantExcelDto> excelData = new ArrayList<VariantExcelDto>();
		List<MainDealer> dealer = mainDealerRepository.findAll();
		if (dealer.size() != 0) {

			dealer.stream().forEach(maindata -> {
				List<VehicalPrice> mainVehicleVeriants = maindata.getVeriants();
				if (mainVehicleVeriants.size() != 0) {

					mainVehicleVeriants.stream().forEach(e -> {
						excelData.add(VariantExcelDto.builder().dealerID(maindata.getMainDealerID())
								.exShowroomPrice(e.getExshowroomPrice()).dealerType(maindata.getDelearType())
								.modelname(e.getModel()).oem(e.getOem()).onRoadPrice(e.getVehicalOnRoadPrice())
								.priceActivationDate(e.getPriceActivationDate()).priceExpireDate(e.getPriceExpireDate())
//									.uniqueVehicleID(Integer.parseInt(e.getType().getVehicalTypeid().substring(4))+"")
								.uniqueVehicleID(e.getUniqueVehicleID()).updatedDate(e.getUpdatedDate())
								.updaterUser(e.getUpdaterUserID()).variantName(e.getVariantName())
								.vehicleVeriantID(e.getVehicalPriceID()).timeZone(e.getTimeZone()).build());
					});

				}

				List<SubDealer> subDealer = maindata.getSubDealer();
				if (subDealer.size() != 0) {
					subDealer.stream().forEach(subdata -> {
						List<VehicalPrice> subVehicleVeriants = subdata.getVehicleVeriants();

						if (subVehicleVeriants.size() != 0) {
							subVehicleVeriants.stream().forEach(e -> {
								excelData.add(VariantExcelDto.builder().dealerID(subdata.getSubDealerID())
										.exShowroomPrice(e.getExshowroomPrice()).dealerType(subdata.getDelearType())
										.modelname(e.getModel()).oem(e.getOem()).onRoadPrice(e.getVehicalOnRoadPrice())
										.uniqueVehicleID(e.getUniqueVehicleID()).updatedDate(e.getUpdatedDate())
										.updaterUser(e.getUpdaterUserID())
										.priceActivationDate(e.getPriceActivationDate())
										.priceExpireDate(e.getPriceExpireDate()).variantName(e.getVariantName())
										.vehicleVeriantID(e.getVehicalPriceID()).timeZone(e.getTimeZone()).build());
							});

						}
					});
				}
			});
		}
		return excelData;

	}
}
