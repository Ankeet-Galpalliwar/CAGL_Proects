package com.cag.twowheele.rough;

public class Rough {
	/*
	@GetMapping("getDealervehicleDetails")
	public ResponseEntity<responce> getDealerWithVehicle(@RequestParam String branchID) {
		List<DealerVehicleData> dealervehicleData = new ArrayList<>();
		mainDealerRepository.findAll().stream().filter(e->{
			return e.getMainBranches().stream().anyMatch(branch->branch.getBranchID().equalsIgnoreCase(branchID));
			}).forEach(mainData -> {
			List<VehicalPrice> veriants = mainData.getVeriants();
			veriants.stream().forEach(v -> {
				dealervehicleData.add(DealerVehicleData.builder().dalersName(mainData.getDealerName())
						.dealeraddress(mainData.getCity()).dealersID(mainData.getMainDealerID())
						.ExshowroomPrice(v.getExshowroomPrice()).model(v.getModel()).oem(v.getOem())
						.phoneNumber(mainData.getContactNumber() + "").variantName(v.getVariantName())
						.vehicalMaxLoanAmount(v.getVehicalMaxLoanAmount()).vehicalOnRoadPrice(v.getVehicalOnRoadPrice())
						.vehicalPriceID(v.getVehicalPriceID()).build());
			});
			
			List<SubDealer> subDealer = mainData.getSubDealer();
			subDealer.stream().filter(e->{
				return e.getSubBranches().stream().anyMatch(branch->branch.getBranchID().equalsIgnoreCase(branchID));
			}).forEach(subdata -> {
				subdata.getVehicleVeriants().stream().forEach(v -> {
					dealervehicleData.add(DealerVehicleData.builder().dalersName(subdata.getDealerName())
							.dealeraddress(subdata.getCity()).dealersID(subdata.getSubDealerID())
							.ExshowroomPrice(v.getExshowroomPrice()).model(v.getModel()).oem(v.getOem())
							.phoneNumber(subdata.getContactNumber() + "").variantName(v.getVariantName())
							.vehicalMaxLoanAmount(v.getVehicalMaxLoanAmount())
							.vehicalOnRoadPrice(v.getVehicalOnRoadPrice()).vehicalPriceID(v.getVehicalPriceID())
							.build());
				});

			});

		});
		return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("FALSE").data(dealervehicleData)
				.message("All Data for Dealer-Vehicle Base on BranchID").build());
	}

	@GetMapping("/getDealerBranchDump")
	public ResponseEntity<responce> getDealerWithBranch() {
		List<DealerBranchData> dealerBranchData = new ArrayList<>();
		mainDealerRepository.findAll().stream().forEach(mainData -> {
			mainData.getMainBranches().stream().forEach(mb -> {
				dealerBranchData.add(DealerBranchData.builder().area(mb.getArea()).branchID(mb.getBranchID())
						.branchName(mb.getBranchName()).dalersName(mainData.getDealerName())
						.dealeraddress(mainData.getCity()).dealersID(mainData.getMainDealerID())
						.oem(mainData.getManufacturerName()).phoneNumber(mainData.getContactNumber() + "")
						.region(mb.getRegion()).state(mb.getState()).build());

			});

			mainData.getSubDealer().forEach(subData -> {
				subData.getSubBranches().stream().forEach(sb -> {
					dealerBranchData.add(DealerBranchData.builder().area(sb.getArea()).branchID(sb.getBranchID())
							.branchName(sb.getBranchName()).dalersName(subData.getDealerName())
							.dealeraddress(subData.getCity()).dealersID(subData.getSubDealerID())
							.oem(subData.getManufacturerName()).phoneNumber(subData.getContactNumber() + "")
							.region(sb.getRegion()).state(sb.getState()).build());
				});

			});

		});
		return ResponseEntity.status(HttpStatus.OK).body(responce.builder().error("FALSE").data(dealerBranchData)
				.message("All Data for Dealer With Branches ").build());

	}
	
	
	*/
}
