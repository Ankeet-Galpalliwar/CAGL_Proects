package com.cag.twowheeler.controller;

public class Dummy {

	
//	if (!allVehicle.isEmpty()) {
//		collect = allVehicle.stream().filter(
//				e -> e.getVehicalvariantName().equalsIgnoreCase(vehicle.getVehicleVariant().trim().toUpperCase()))
//				.collect(Collectors.toList());
//	}
//
//	if (!collect.isEmpty()) {
//		boolean anyMatch = collect.get(0).getVehical_Price().stream()
//				.anyMatch(e -> e.getState().equalsIgnoreCase(vehicle.getVehicleState().trim().toUpperCase()));
//		if (anyMatch)
//
//			throw new AlreadyExist("Vehical Alrady Exist with Given State", "000");
//		else {
//			VehicalPrice newVehicalData = new VehicalPrice();
//
//			/**
//			 * @ ID creation logic for Vehicle Price By State [EX:->MHHON01]
//			 */
//			Optional<StateAbbreviation> optionalData = abbreviationRepository
//					.findById(vehicle.getVehicleState().trim().toUpperCase());
//			String vehiclePriceID = optionalData.get().getStateAbbreviation() + collect.get(0).getVehicalTypeid();
//			// Seating Vehicle TypeID
//			newVehicalData.setVehicalPriceID(vehiclePriceID);
//			newVehicalData
//					.setUniqueVehicleID("" + Integer.parseInt(collect.get(0).getVehicalTypeid().substring(4)));
//			newVehicalData.setState(vehicle.getVehicleState().trim().toUpperCase());
//			newVehicalData.setOem(collect.get(0).getOem());// store OEM Object
//			newVehicalData.setVehicalMaxLoanAmount(vehicle.getVehicleMaxLoanAmount());
//			newVehicalData.setExshowroomPrice(vehicle.getExShowRoomPrice());
//			newVehicalData.setPriceActivationDate(vehicle.getPriceActivationDate());
//			newVehicalData.setPriceExpireDate(LocalDate.of(2099, 12, 31));
//			newVehicalData
//					.setTimeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
//			newVehicalData.setStatus("PENDING");
//			newVehicalData.setUpdatedDate(LocalDate.now() + "");
//			newVehicalData.setUpdaterUserID(user);
//			newVehicalData.setVehicalOnRoadPrice(vehicle.getVehicalOnRoadPrice());
//			newVehicalData.setType(collect.get(0)); // Type-> Vehicle variant Object Stored
//			priceRepository.save(newVehicalData);
//
//			System.err.println(newVehicalData + "===========================================================");
//
//			return "Existing Vehicle Data Add Successfully With Other State";
//		}
//
//	} else {
//		VehicalOem oem = oemRepositoryy.findByVehicalOem(vehicle.getVehicleOem().trim().toUpperCase());
//		VehicalVariant newVehicle = new VehicalVariant();
//
//		/**
//		 * @ ID creation logic for Vehicle Variant
//		 */
////		String VehicleVariantID = oem.getVehicalOem().substring(0, 3) + "-"+"__";
//		String updatedID = "";
//		List<String> similarVariantId = new ArrayList<>();
//
//		try {
//			similarVariantId = variantRepository.getSimilarVariantId(oem.getVehicalOemid());
//		} catch (Exception e) {
//			// TODO: handle exception
//
//		}
//		if (!similarVariantId.isEmpty()) {
//			System.err.println(similarVariantId + "===========\n\n\n=======");
//			List<Integer> Ids = similarVariantId.stream().map(data-> Integer.parseInt(data.substring(4))).sorted().collect(Collectors.toList());
//			log.info("===============================" + Ids);
//			int variantId = Ids.get(Ids.size() - 1);
//			variantId++;
//			String str = "";
//			if (variantId < 10) {
//				str = "0" + variantId;
//			} else {
//				str = "" + variantId;
//			}
//			String updatedValue = str;
//			updatedID += oem.getVehicalOem().substring(0, 3) + "-" + updatedValue;
//		} else {
//			updatedID += oem.getVehicalOem().substring(0, 3) + "-01";
//		}
//		log.info("===============================" + updatedID);
//		newVehicle.setVehicalTypeid(updatedID);
//		newVehicle.setUniqueVehicleID("" + Integer.parseInt(updatedID.substring(4)));
//		newVehicle.setOem(oem);
//		newVehicle.setVehicalvariantName(vehicle.getVehicleVariant().trim().toUpperCase());
//		VehicleModal vehicleModal = null;
//		try {
//			vehicleModal = vehicleModalRepository
//					.findByVehicleModelName(vehicle.getVehicleModel().trim().toUpperCase());
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		if (vehicleModal != null)
//			newVehicle.setVehicleModal(vehicleModal);
//		else {
//			// create new Vehicle Model
//			VehicleModal newVehicleModal = new VehicleModal();
//
//			/**
//			 * @ ID creation logic for VehicleModal
//			 */
////			String id = oem.getVehicalOem().substring(0, 3) + "-M" + "__";
//			String incrementalID = "";
//			List<String> similarModalId = new ArrayList<>();
//			try {
//				similarModalId = vehicleModalRepository.getSimilarModalId().stream()
//						.filter(e -> e.contains(oem.getVehicalOem().substring(0, 3))).sorted()
//						.collect(Collectors.toList());
//
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			if (!similarModalId.isEmpty()) {
//				List<Integer> sortedIds = similarModalId.stream().map(data->Integer.parseInt(data.substring(5))).sorted().collect(Collectors.toList());
//				int num = sortedIds.get(sortedIds.size() - 1);
//				num = num + 1;
//				String arr = "";
//				if (num < 10) {
//					arr = "0" + num;
//				} else {
//					arr = "" + num;
//				}
//				incrementalID += oem.getVehicalOem().substring(0, 3) + "-M" + arr;
//			} else {
//				incrementalID += oem.getVehicalOem().substring(0, 3) + "-M01";
//			}
//
//			newVehicleModal.setVehicleModelId(incrementalID);
//			newVehicleModal.setVehicleModelName(vehicle.getVehicleModel().trim().toUpperCase());
//			vehicleModalRepository.save(newVehicleModal);
//			// get VehicalModel updated Object for mapping
//			VehicleModal getNewvehicleModal = vehicleModalRepository
//					.findByVehicleModelName(vehicle.getVehicleModel().trim().toUpperCase());
//			newVehicle.setVehicleModal(getNewvehicleModal);
//		}
//		variantRepository.save(newVehicle);
//
//		VehicalPrice newVehicalPrice = new VehicalPrice();
//		Optional<StateAbbreviation> optionalData = abbreviationRepository
//				.findById(vehicle.getVehicleState().trim().toUpperCase());
//		VehicalVariant vehicalVariant = variantRepository.findAll().stream().filter(
//				e -> e.getVehicalvariantName().equalsIgnoreCase(vehicle.getVehicleVariant().trim().toUpperCase()))
//				.collect(Collectors.toList()).get(0);
//
//		/**
//		 * @ ID creation logic for Vehicle price by state
//		 */
//		String priceVariantId = optionalData.get().getStateAbbreviation() + vehicalVariant.getVehicalTypeid();
//		newVehicalPrice.setVehicalPriceID(priceVariantId);
//		newVehicalPrice.setUniqueVehicleID("" + Integer.parseInt(vehicalVariant.getVehicalTypeid().substring(4)));
//		newVehicalPrice.setState(vehicle.getVehicleState().trim().toUpperCase());
//		newVehicalPrice.setOem(oem);
//		newVehicalPrice.setVehicalMaxLoanAmount(vehicle.getVehicleMaxLoanAmount());
//		newVehicalPrice.setExshowroomPrice(vehicle.getExShowRoomPrice());
//		newVehicalPrice.setPriceActivationDate(vehicle.getPriceActivationDate());
//		newVehicalPrice.setPriceExpireDate(LocalDate.of(2099, 12, 31));
//		newVehicalPrice.setTimeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
//		newVehicalPrice.setStatus("PENDING");
//		newVehicalPrice.setVehicalOnRoadPrice(vehicle.getVehicalOnRoadPrice());
//		newVehicalPrice.setType(vehicalVariant);
//		newVehicalPrice.setUpdatedDate(LocalDate.now() + "");
//		newVehicalPrice.setUpdaterUserID(user);
//
//		priceRepository.save(newVehicalPrice);
//		System.err.println(newVehicalPrice + "======================================================");

//	return "New Vehicle Data Add Successfully";
//
	
	
	
}
