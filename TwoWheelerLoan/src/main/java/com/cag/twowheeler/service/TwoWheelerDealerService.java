package com.cag.twowheeler.service;

import java.util.List;
import java.util.Set;

import com.cag.twowheeler.dto.BranchDto;
import com.cag.twowheeler.dto.MainDealerDetailsDto;
import com.cag.twowheeler.dto.SubDealerDetailsDto;
import com.cag.twowheeler.dto.VehicleVariantDto;

public interface TwoWheelerDealerService {

	public String addMainDealer(MainDealerDetailsDto dealerDetailsDto,String userName);

	public Boolean addSubDealers(String mainDealerID, SubDealerDetailsDto subDealerDetailsDto,String userName);

	public List<MainDealerDetailsDto> getMainDealersDetails(String flag);

	public List<SubDealerDetailsDto> getSubDealerDetails(String mainDealerId);

	public Boolean editSubDealer(String subDealerId, SubDealerDetailsDto subDetailsDto,String userName);

	public Boolean editMainDealer(String mainDealerId, MainDealerDetailsDto mainDetailsDto,String userName);


	public List<String> getOem();

	public List<String> getStates(String oem);

	public List<String> getDistricts(String State);

	public List<MainDealerDetailsDto> getExcelFile();

	// ==================Variants===========================================================

	public List<VehicleVariantDto> mainVariants(String DealerID);

	public List<VehicleVariantDto> avaliableMainVariants(String mainDealerID);

	public boolean addVeriantsMian(String mainDealerID, List<VehicleVariantDto> variants);

	public Boolean removeMainVariant(String mainDealerID, List<VehicleVariantDto> variants);

	// ==================

	public List<VehicleVariantDto> subVariants(String DealerID);

	public List<VehicleVariantDto> avaliableSubVariants(String subDealerID);

	public boolean addVariantSub(String subDealerID, List<VehicleVariantDto> variants);

	public Boolean removeSubVariant(String subDealerID, List<VehicleVariantDto> variants);

	// ==================Branches============================================================

	public String addMainBranches(String mainDealerID, List<BranchDto> branchs);

	public Boolean removeMainBranches(String mainDealerID, List<BranchDto> branchs);

	public Set<BranchDto> showAvaliableMainBranches(String mainDealerID);

	public Set<BranchDto> showMainBranches(String mainDealerID, String region, String area);

	public Set<BranchDto> showMainBranches(String mainDealerID);

	// ====================

	public Set<BranchDto> showAvaliableSubBranches(String subDealerID);

	public Set<BranchDto> showSubBranches(String subDealearID);

	public Set<BranchDto> showSubBranches(String subDealerID, String region, String area);

	public String addAllMainBranches(String mainDealerID, String subDealerID);

	public String addSubBranches(String subDealerID, List<BranchDto> branchs);

	public Boolean removeSubBranches(String subDealerID, List<BranchDto> branchs);

}
