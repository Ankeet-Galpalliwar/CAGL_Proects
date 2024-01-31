package com.cag.twowheeler.service.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cag.twowheeler.dto.BranchDto;
import com.cag.twowheeler.dto.MainDealerDetailsDto;
import com.cag.twowheeler.dto.SubDealerDetailsDto;
import com.cag.twowheeler.dto.VehicleVariantDto;
import com.cag.twowheeler.entity.Branch;
import com.cag.twowheeler.entity.MainDealer;
import com.cag.twowheeler.entity.StateAbbreviation;
import com.cag.twowheeler.entity.SubDealer;
import com.cag.twowheeler.entity.VehicalPrice;
import com.cag.twowheeler.exceptation.AlreadyExist;
import com.cag.twowheeler.repository.BranchRepository;
import com.cag.twowheeler.repository.MainDealerRepository;
import com.cag.twowheeler.repository.StateAbbreviationRepository;
import com.cag.twowheeler.repository.SubDealerRepository;
import com.cag.twowheeler.repository.VehicalPriceRepository;
import com.cag.twowheeler.repository.VehicalVariantRepository;
import com.cag.twowheeler.service.TwoWheelerDealerService;

@Service
public class TwoWheelerDealerServiceImpl implements TwoWheelerDealerService {

	@Autowired
	MainDealerRepository mainDealerRepository;
	@Autowired
	SubDealerRepository subDealerRepository;
	@Autowired
	StateAbbreviationRepository abbreviationRepository;
	@Autowired
	VehicalVariantRepository variantRepository;
	@Autowired
	VehicalPriceRepository vehicalPriceRepository;
	@Autowired
	BranchRepository branchRepository;

	String district = "";
	int count = 1;

	@Override
	public List<MainDealerDetailsDto> getMainDealersDetails(String flag) {
		List<MainDealer> allMainDealers = new ArrayList<>();
		if (!flag.toUpperCase().equals("ALL")) {
			MainDealer mainDealer = mainDealerRepository.findById(flag).get();
			allMainDealers.add(mainDealer);
		} else {
			allMainDealers = mainDealerRepository.findAll();
		}
		List<MainDealerDetailsDto> dealerDetailsDtos = new ArrayList<>();
		if (!allMainDealers.isEmpty()) {
			allMainDealers.stream().forEach(e -> {

				String[] split = e.getDistrict().split(",");
				List<String> district = Arrays.asList(split);

				MainDealerDetailsDto dealerDetailsDto = MainDealerDetailsDto.builder().dealerType(e.getDelearType())
						.mainDealerAccountHolderName(e.getAccountHolderName())
						.mainDealerActivationStatus(e.getActivatioinStatus()).mainDealerStatus(e.getStatus())
						.mainDealerActivationData(e.getActivationData()).mainDealerExpireData(e.getExpiryDate())
						.mainDealerAlternateContactNumber(e.getAlternateContactNumber())
						.mainDealerBankAccNumber(e.getBankAccNumber()).mainDealerBankBranchName(e.getBankBranchName())
						.mainDealerBankName(e.getBankName()).mainDealerContactNumber(e.getContactNumber())
						.mainDealerContactPersonName(e.getContactPersonName()).mainDealerGstNumber(e.getGstNumber())
						.mainDealerIfsc(e.getIfsc()).mainDealerMailID(e.getMailID())
						.mainDealerManufacturerName(e.getManufacturerName()).mainDealerName(e.getDealerName())
						.mainDealerPanNumber(e.getPanNumber()).mainDealerPaniniCheck(e.getPaniniCheck())
						.mainDealerID(e.getMainDealerID()).addressDetails(e.getArea()).city(e.getCity())
						.contactPersonMobile(e.getContactPersonMobile()).district(district).state(e.getState())
						.pinCode(e.getPinCode()).build();
				dealerDetailsDtos.add(dealerDetailsDto);
			});
			return dealerDetailsDtos;
		}
		return dealerDetailsDtos;
	}

	@Override
	public List<SubDealerDetailsDto> getSubDealerDetails(String mainDealerId) {
		Optional<MainDealer> mainDealer = mainDealerRepository.findById(mainDealerId);
		new ArrayList<>();
		if (mainDealer.isPresent()) {
			MainDealer mainDealerInfo = mainDealer.get();
			List<SubDealer> subDealers = mainDealerInfo.getSubDealer();
			List<SubDealerDetailsDto> allSubDealers = new ArrayList<>();
			subDealers.stream().forEach(a -> {
				String[] split = a.getDistrict().split(",");
				List<String> district = Arrays.asList(split);
				SubDealerDetailsDto subDealerDto = SubDealerDetailsDto.builder().dealerType(a.getDelearType())
						.subDealerAccountHolderName(a.getAccountHolderName())
						.subDealerActivationStatus(a.getActivatioinStatus()).subDealerStatus(a.getStatus())
						.subDealerActivationData(a.getActivationData()).subDealerExpireData(a.getExpiryDate())
						.subDealerAlternateContactNumber(a.getAlternateContactNumber())
						.subDealerBankAccNumber(a.getBankAccNumber()).subDealerBankBranchName(a.getBankBranchName())
						.subDealerBankName(a.getBankName()).subDealerContactNumber(a.getContactNumber())
						.subDealerContactPersonName(a.getContactPersonName()).subDealerMailID(a.getMailID())
						.subDealerName(a.getDealerName()).subDealerGstNumber(a.getGstNumber())
						.subDealerID(a.getSubDealerID()).subDealerIfsc(a.getIfsc())
						.subDealerManufacturerName(a.getManufacturerName()).subDealerPanNumber(a.getPanNumber())
						.subDealerPaniniCheck(a.getPaniniCheck()).addressDetails(a.getArea()).city(a.getCity())
						.contactPersonMobile(a.getContactPersonMobile()).district(district).pinCode(a.getPinCode())
						.state(a.getState()).build();

				allSubDealers.add(subDealerDto);

			});
			return allSubDealers;
		}
		return new ArrayList<>();
	}

	/**
	 * @Api for Adding MainDealer
	 */
	@Override
	public String addMainDealer(MainDealerDetailsDto dealer, String userName) {
		if (mainDealerRepository.findByBankAccNumber(dealer.getMainDealerBankAccNumber()) != null)
			throw new AlreadyExist("Bank_Account Number", "ERROR_CODE_000");
		if (mainDealerRepository.findByPanNumber(dealer.getMainDealerPanNumber()) != null)
			throw new AlreadyExist("PanCard_Number", "ERROR_CODE_000");
		if (mainDealerRepository.findByGstNumber(dealer.getMainDealerGstNumber()) != null)
			throw new AlreadyExist("Gst_Number", "ERROR_CODE_000");

		if (dealer != null) {
			// Get State Abbreviation
			Optional<StateAbbreviation> abbreviation = abbreviationRepository.findById(dealer.getState());
			StateAbbreviation stateAbbreviation = abbreviation.get();
			String state = stateAbbreviation.getStateAbbreviation();

			// main dealer IdGeneratation
			String id = "";
			id += state + dealer.getMainDealerManufacturerName().subSequence(0, 3) + "-" + "____" + "-A01";
			List<String> similarDealerId = new ArrayList<>();
			similarDealerId = mainDealerRepository.getSimilarDealerId(id);
			String incrementId = null;
			if (!similarDealerId.isEmpty()) {
				List<String> sortedId = similarDealerId.stream().sorted().collect(Collectors.toList());

				String dealerid = sortedId.get(sortedId.size() - 1);
				String dealercount = dealerid.substring(6, 10);
				char[] charArray = dealercount.toCharArray();
				int count = Integer.parseInt(dealercount);
				count++;
				String arr = "" + count;
				int temp = charArray.length - 1;
				for (int i = arr.length() - 1; i >= 0; i--) {
					charArray[temp] = arr.charAt(i);
					temp--;
				}
				String updatedCount = new String(charArray);
				incrementId = state + dealer.getMainDealerManufacturerName().subSequence(0, 3) + "-" + updatedCount
						+ "-A01";
			} else {
				incrementId = state + dealer.getMainDealerManufacturerName().subSequence(0, 3) + "-0001-A01";
			}

			// District Logic
			district = "";
			count = 1;
			dealer.getDistrict().stream().forEach(e -> {
				if (dealer.getDistrict().size() == count)
					district += e;
				else
					district += e + ',';
				count++;
			});

			// mainDealer Object Set
			MainDealer mainDealer = MainDealer.builder().mainDealerID(incrementId).status("PENDING")
					.timeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()))
					.paymentEligible("YES").makerDate(LocalDate.now()).makerUserID(userName)
					.activatioinStatus(dealer.getMainDealerActivationStatus())
					.accountHolderName(dealer.getMainDealerAccountHolderName())
					.activationData(dealer.getMainDealerActivationData())
					.expiryDate(LocalDate.parse(dealer.getMainDealerActivationData() + "").plusYears(3))
					.Area(dealer.getAddressDetails())
					.alternateContactNumber(dealer.getMainDealerAlternateContactNumber())
					.bankAccNumber(dealer.getMainDealerBankAccNumber())
					.bankBranchName(dealer.getMainDealerBankBranchName()).bankName(dealer.getMainDealerBankName())
					.contactNumber(dealer.getMainDealerContactNumber())
					.contactPersonMobile(dealer.getContactPersonMobile())
					.contactPersonName(dealer.getMainDealerContactPersonName()).dealerName(dealer.getMainDealerName())
					.delearType("MAIN").district(district).city(dealer.getCity()).ifsc(dealer.getMainDealerIfsc())
					.gstNumber(dealer.getMainDealerGstNumber()).mailID(dealer.getMainDealerMailID())
					.manufacturerName(dealer.getMainDealerManufacturerName()).state(dealer.getState())
					.pinCode(dealer.getPinCode()).idStatus("OK").paniniCheck(dealer.getMainDealerPaniniCheck())
					.panNumber(dealer.getMainDealerPanNumber()).build();
			MainDealer save = mainDealerRepository.save(mainDealer);
			return save.getMainDealerID();
		}
		return null;
	}

	/**
	 * @Api For Adding SubDealer
	 */
	@Override
	public Boolean addSubDealers(String mainDealerID, SubDealerDetailsDto subDealerdto, String userName) {
		// Get Main Dealer
		Optional<MainDealer> dealer = mainDealerRepository.findById(mainDealerID);
		if (dealer.isPresent()) {

			MainDealer mainDealer = dealer.get(); // MainDealer Reference Set in SubDealer Object
			//
			String dealerID = mainDealer.getMainDealerID();
			String maincount = dealerID.substring(6, 10);

			// Get State Abbreviation
			Optional<StateAbbreviation> abbreviation = abbreviationRepository.findById(mainDealer.getState());
			StateAbbreviation stateAbbreviation = abbreviation.get();
			String state = stateAbbreviation.getStateAbbreviation();

			String id = "";
			String incrementId = null;// locally Variable
			id += state + mainDealer.getManufacturerName().subSequence(0, 3) + "-" + maincount + "-S" + "__";
			List<String> similarDealerId = new ArrayList<>();
			similarDealerId = subDealerRepository.getSimilarDealerId(id);
			if (!similarDealerId.isEmpty()) {
				List<String> sortedId = similarDealerId.stream().sorted().collect(Collectors.toList());
				String priviousId = sortedId.get(sortedId.size() - 1);
				String count = priviousId.substring(12);
				char[] charArray = count.toCharArray();
				int num = Integer.parseInt(count);
				num++;
				String arr = "" + num;
				int temp = charArray.length - 1;
				for (int i = arr.length() - 1; i >= 0; i--) {
					charArray[temp] = arr.charAt(i);
					temp--;
				}
				String updatedcount = new String(charArray);
				incrementId = state + mainDealer.getManufacturerName().subSequence(0, 3) + "-" + maincount + "-S"
						+ updatedcount;
			} else {
				incrementId = state + mainDealer.getManufacturerName().subSequence(0, 3) + "-" + maincount + "-S01";
			}

			// district logic
			district = "";
			count = 1;
			subDealerdto.getDistrict().stream().forEach(e -> {
				if (subDealerdto.getDistrict().size() == count)
					district += e;
				else
					district += e + ',';
				count++;
			});

			// Seating SubDealer
			SubDealer subDealer = SubDealer.builder().subDealerID(incrementId)
					.timeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()))
					.accountHolderName(subDealerdto.getSubDealerAccountHolderName())
					.activationData(subDealerdto.getSubDealerActivationData())
					.expiryDate(LocalDate.parse(subDealerdto.getSubDealerActivationData() + "").plusYears(3))
					.status("PENDING").paymentEligible("NO").makerDate(LocalDate.now()).makerUserID(userName)
					.activatioinStatus(subDealerdto.getSubDealerActivationStatus())
					.Area(subDealerdto.getAddressDetails())
					.alternateContactNumber(subDealerdto.getSubDealerAlternateContactNumber())
					.bankAccNumber(subDealerdto.getSubDealerBankAccNumber())
					.bankName(subDealerdto.getSubDealerBankName())
					.bankBranchName(subDealerdto.getSubDealerBankBranchName()).city(subDealerdto.getCity())
					.contactNumber(subDealerdto.getSubDealerContactNumber())
					.contactPersonMobile(subDealerdto.getContactPersonMobile())
					.contactPersonName(subDealerdto.getSubDealerContactPersonName())
					.dealerName(subDealerdto.getSubDealerName()).delearType("SUB").district(mainDealer.getDistrict())
					.gstNumber(subDealerdto.getSubDealerGstNumber()).ifsc(subDealerdto.getSubDealerIfsc())
					.mailID(subDealerdto.getSubDealerMailID()).mainDealers(mainDealer)
					.manufacturerName(subDealerdto.getSubDealerManufacturerName())
					.panNumber(subDealerdto.getSubDealerPanNumber()).paniniCheck(subDealerdto.getSubDealerPaniniCheck())
					.pinCode(subDealerdto.getPinCode()).state(mainDealer.getState()).district(district).idStatus("OK")
					.build();

			subDealerRepository.save(subDealer);
			return true;
		}
		return false;
	}

	@Override
	public Boolean editMainDealer(String mainDealerId, MainDealerDetailsDto mainDetailsDto, String userName) {
		MainDealer data = mainDealerRepository.findById(mainDealerId).get();
		System.err.println(mainDetailsDto.getMainDealerBankAccNumber() + "=========" + data.getBankAccNumber());

		if (!data.getBankAccNumber().equalsIgnoreCase(mainDetailsDto.getMainDealerBankAccNumber())) {
			if (mainDealerRepository.findByBankAccNumber(mainDetailsDto.getMainDealerBankAccNumber()) != null)
				throw new AlreadyExist("Bank_Account Number", "ERROR_CODE_000");
		}
		if (!data.getPanNumber().equalsIgnoreCase(mainDetailsDto.getMainDealerPanNumber())) {
			if (mainDealerRepository.findByPanNumber(mainDetailsDto.getMainDealerPanNumber()) != null)
				throw new AlreadyExist("PanCard_Number", "ERROR_CODE_000");
		}
		if (!data.getGstNumber().equalsIgnoreCase(mainDetailsDto.getMainDealerGstNumber())) {
			if (mainDealerRepository.findByGstNumber(mainDetailsDto.getMainDealerGstNumber()) != null)
				throw new AlreadyExist("Gst_Number", "ERROR_CODE_000");
		}

		if (mainDetailsDto != null) {
			Optional<MainDealer> dealer = mainDealerRepository.findById(mainDealerId);
			if (dealer.isPresent()) {
				MainDealer mainDealer = dealer.get();
				// District Logic
				district = "";
				count = 1;
				mainDetailsDto.getDistrict().stream().forEach(e -> {
					if (mainDetailsDto.getDistrict().size() == count)
						district += e;
					else
						district += e + ',';
					count++;
				});

				MainDealer newMainDealer = MainDealer.builder().status("UPDATED").updatedDate(LocalDate.now())
						.timeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()))
						.updaterUserID(userName).checkerDate(mainDealer.getCheckerDate())
						.checkerUserID(mainDealer.getCheckerUserID()).makerDate(mainDealer.getMakerDate())
						.makerUserID(mainDealer.getMakerUserID()).delearType("MAIN").idStatus("OK")
						.activatioinStatus(mainDetailsDto.getMainDealerActivationStatus())
						.accountHolderName(mainDetailsDto.getMainDealerAccountHolderName())
						.activationData(mainDetailsDto.getMainDealerActivationData())
						.expiryDate(LocalDate.parse(mainDetailsDto.getMainDealerActivationData() + "").plusYears(3))
						.mainBranches(null).alternateContactNumber(mainDetailsDto.getMainDealerAlternateContactNumber())
						.bankAccNumber(mainDetailsDto.getMainDealerBankAccNumber())
						.bankBranchName(mainDetailsDto.getMainDealerBankBranchName())
						.bankName(mainDetailsDto.getMainDealerBankName())
						.contactNumber(mainDetailsDto.getMainDealerContactNumber())
						.contactPersonName(mainDetailsDto.getMainDealerContactPersonName())
						.dealerName(mainDetailsDto.getMainDealerName())
						.gstNumber(mainDetailsDto.getMainDealerGstNumber()).ifsc(mainDetailsDto.getMainDealerIfsc())
						.mailID(mainDetailsDto.getMainDealerMailID())
						.manufacturerName(mainDetailsDto.getMainDealerManufacturerName())
						.panNumber(mainDetailsDto.getMainDealerPanNumber())
						.paniniCheck(mainDetailsDto.getMainDealerPaniniCheck()).Area(mainDetailsDto.getAddressDetails())
						.state(mainDetailsDto.getState()).district(district).city(mainDetailsDto.getCity())
						.pinCode(mainDetailsDto.getPinCode()).mainBranches(mainDealer.getMainBranches())
						.Veriants(mainDealer.getVeriants()).contactPersonMobile(mainDetailsDto.getContactPersonMobile())
						.build();
				BeanUtils.copyProperties(newMainDealer, mainDealer); // Hear Id updated to Zero (---v)
				mainDealer.setMainDealerID(mainDealerId); // Seating original Id {Because it will Be zero }
				mainDealer.setPaymentEligible("YES");
//				mainDealer.setMainBranches(mainDealer.getMainBranches());
				mainDealerRepository.save(mainDealer);
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean editSubDealer(String subDealerId, SubDealerDetailsDto subDetailsDto, String userName) {

//		SubDealer data = subDealerRepository.findById(subDealerId).get();
//		if (data.getBankAccNumber() != subDetailsDto.getSubDealerBankAccNumber()) {
//			if (subDealerRepository.findByBankAccNumber(subDetailsDto.getSubDealerBankAccNumber()) != null)
//				throw new AlreadyExist("Bank_Account Number", "ERROR_CODE_000");
//		}
//		if (!data.getPanNumber().equalsIgnoreCase(subDetailsDto.getSubDealerPanNumber())) {
//			if (subDealerRepository.findByPanNumber(subDetailsDto.getSubDealerPanNumber()) != null)
//				throw new AlreadyExist("Pan_Card Number", "ERROR_CODE_000");
//		}
//		if (!data.getGstNumber().equalsIgnoreCase(subDetailsDto.getSubDealerGstNumber())) {
//			if (subDealerRepository.findByGstNumber(subDetailsDto.getSubDealerGstNumber()) != null)
//				throw new AlreadyExist("Gst_Number", "ERROR_CODE_000");
//		}

		if (subDetailsDto != null) {
			Optional<SubDealer> dealer = subDealerRepository.findById(subDealerId);
			if (dealer.isPresent()) {
				SubDealer subDealer = dealer.get();

				MainDealer mainDealer = subDealer.getMainDealers();

				// district logic
				district = "";
				count = 1;
				subDetailsDto.getDistrict().stream().forEach(e -> {
					if (subDetailsDto.getDistrict().size() == count)
						district += e;
					else
						district += e + ',';
					count++;
				});
				SubDealer newSubDealer = SubDealer.builder().status("UPDATED").updatedDate(LocalDate.now())
						.timeZone(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()))
						.updaterUserID(userName).checkerDate(subDealer.getCheckerDate())
						.checkerUserID(subDealer.getCheckerUserID()).makerDate(subDealer.getMakerDate())
						.makerUserID(subDealer.getMakerUserID()).delearType("SUB").idStatus("OK")
						.activatioinStatus(subDetailsDto.getSubDealerActivationStatus())
						.accountHolderName(subDetailsDto.getSubDealerAccountHolderName())
						.activationData(subDetailsDto.getSubDealerActivationData())
						.expiryDate(LocalDate.parse(subDetailsDto.getSubDealerActivationData() + "").plusYears(3))
						.alternateContactNumber(subDetailsDto.getSubDealerAlternateContactNumber())
						.bankAccNumber(subDetailsDto.getSubDealerBankAccNumber())
						.bankBranchName(subDetailsDto.getSubDealerBankBranchName())
						.bankName(subDetailsDto.getSubDealerBankName())
						.contactNumber(subDetailsDto.getSubDealerContactNumber())
						.contactPersonName(subDetailsDto.getSubDealerContactPersonName())
						.dealerName(subDetailsDto.getSubDealerName()).gstNumber(subDetailsDto.getSubDealerGstNumber())
						.ifsc(subDetailsDto.getSubDealerIfsc()).mailID(subDetailsDto.getSubDealerMailID())
						.manufacturerName(subDetailsDto.getSubDealerManufacturerName())
						.panNumber(subDetailsDto.getSubDealerPanNumber())
						.paniniCheck(subDetailsDto.getSubDealerPaniniCheck()).mainDealers(mainDealer)
						.contactPersonMobile(subDetailsDto.getContactPersonMobile()).state(subDetailsDto.getState())
						.city(subDetailsDto.getCity()).district(district).subBranches(subDealer.getSubBranches())
						.vehicleVeriants(subDealer.getVehicleVeriants()).pinCode(subDetailsDto.getPinCode())
						.Area(subDetailsDto.getAddressDetails()).build();
				BeanUtils.copyProperties(newSubDealer, subDealer);// Hear Id updated to null
				subDealer.setSubDealerID(subDealerId);
				subDealer.setPaymentEligible("NO");
				subDealerRepository.save(subDealer);// save Update Sub_Dealer
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	@Override
	public List<String> getOem() {

		return mainDealerRepository.findAll().stream().map(data -> data.getManufacturerName().trim()).distinct()
				.collect(Collectors.toList());

	}

	@Override
	public List<String> getStates(String oem) {
		return mainDealerRepository.findAll().stream().filter(e -> e.getManufacturerName().equalsIgnoreCase(oem))
				.map(e -> e.getState()).distinct().collect(Collectors.toList());
	}

	@Override
	public List<String> getDistricts(String State) {
		return mainDealerRepository.findAll().stream().filter(e -> (e.getState().equalsIgnoreCase(State)))
				.map(e -> e.getDistrict()).distinct().collect(Collectors.toList());
	}

	@Override
	public List<MainDealerDetailsDto> getExcelFile() {
		List<MainDealer> allDealer = new ArrayList<>();
		allDealer = mainDealerRepository.findAll();
		List<MainDealerDetailsDto> allMainDealerdto = new ArrayList<>();
		if (!allDealer.isEmpty()) {
			// .filter(mainstatus -> mainstatus.getStatus().equalsIgnoreCase("Approved"))
			allDealer.stream().forEach(e -> {

				List<SubDealer> allSubDealer = new ArrayList<>();
				List<SubDealerDetailsDto> subDealerDtos = new ArrayList<>();

				allSubDealer = e.getSubDealer();

				if (!allSubDealer.isEmpty()) {
					allSubDealer.stream()// .filter(substatus -> substatus.getStatus().equalsIgnoreCase("APPROVED"))
							.forEach(data -> {
								List<String> subDistrict = new ArrayList<String>();
								subDistrict.add(data.getDistrict());
								SubDealerDetailsDto subDealerDto = SubDealerDetailsDto.builder()
										.subDealerAccountHolderName(data.getAccountHolderName())
										.subDealerActivationData(data.getActivationData())
										.subDealerExpireData(data.getExpiryDate()).updaterDate(data.getUpdatedDate())
										.updaterUserID(data.getUpdaterUserID())
										.paymentEligible(data.getPaymentEligible())
										.subDealerAlternateContactNumber(data.getAlternateContactNumber())
										.subDealerBankAccNumber(data.getBankAccNumber())
										.subDealerBankBranchName(data.getBankBranchName())
										.subDealerBankName(data.getBankName())
										.subDealerContactNumber(data.getContactNumber())
										.subDealerContactPersonName(data.getContactPersonName())
										.subDealerMailID(data.getMailID()).subDealerName(data.getDealerName())
										.subDealerGstNumber(data.getGstNumber()).subDealerID(data.getSubDealerID())
										.subDealerIfsc(data.getIfsc())
										.subDealerManufacturerName(data.getManufacturerName())
										.subDealerPanNumber(data.getPanNumber())
										.subDealerPaniniCheck(data.getPaniniCheck()).addressDetails(data.getArea())
										.city(data.getCity()).contactPersonMobile(data.getContactPersonMobile())
										.district(subDistrict).pinCode(data.getPinCode()).state(data.getState())
										.mainDealer(data.getMainDealers()).timeZone(data.getTimeZone()).build();
								subDealerDtos.add(subDealerDto);
							});
				}
				// list is use for to store main Dealer District
				List<String> mainDistrict = new ArrayList<String>();
				mainDistrict.add(e.getDistrict());
				MainDealerDetailsDto dealerDetailsDto = MainDealerDetailsDto.builder()
						.mainDealerAccountHolderName(e.getAccountHolderName())
						.mainDealerActivationData(e.getActivationData()).mainDealerExpireData(e.getExpiryDate())
						.updaterDate(e.getUpdatedDate()).updaterUserID(e.getUpdaterUserID())
						.paymentEligible(e.getPaymentEligible())
						.mainDealerAlternateContactNumber(e.getAlternateContactNumber())
						.mainDealerBankAccNumber(e.getBankAccNumber()).mainDealerBankBranchName(e.getBankBranchName())
						.mainDealerBankName(e.getBankName()).mainDealerContactNumber(e.getContactNumber())
						.mainDealerContactPersonName(e.getContactPersonName()).mainDealerGstNumber(e.getGstNumber())
						.mainDealerIfsc(e.getIfsc()).mainDealerMailID(e.getMailID())
						.mainDealerManufacturerName(e.getManufacturerName()).mainDealerName(e.getDealerName())
						.mainDealerPanNumber(e.getPanNumber()).mainDealerPaniniCheck(e.getPaniniCheck())
						.mainDealerID(e.getMainDealerID()).addressDetails(e.getArea()).city(e.getCity())
						.contactPersonMobile(e.getContactPersonMobile()).district(mainDistrict).state(e.getState())
						.pinCode(e.getPinCode()).subDealerDetailsDtos(subDealerDtos).timeZone(e.getTimeZone()).build();
				allMainDealerdto.add(dealerDetailsDto);
			});
			return allMainDealerdto;
		}
		return allMainDealerdto;
	}

//===============================Branches============================================
	@Override
	public String addMainBranches(String mainDealerID, List<BranchDto> branchs) {
		// Get Main Dealer By ID
		MainDealer mainDealer = mainDealerRepository.findById(mainDealerID).get();
		List<Branch> mainBranches = mainDealer.getMainBranches();
		if (!branchs.isEmpty()) {
			branchs.stream().forEach(e -> {
				Branch branch = branchRepository.findById(e.getBranchID()).get();
				if (!mainBranches.contains(branch))
					mainBranches.add(branch);
			});
			mainDealer.setMainBranches(mainBranches);
			mainDealerRepository.save(mainDealer);
			return "Sucessfully Added";
		}
		return "Not Added";
	}

	@Override
	public Boolean removeMainBranches(String mainDealerID, List<BranchDto> branchs) {
		MainDealer mainDealer = mainDealerRepository.findById(mainDealerID).get();
		List<Branch> mainBranches = mainDealer.getMainBranches();
		if (!branchs.isEmpty()) {
			branchs.stream().forEach(e -> {
				Branch branch = branchRepository.findById(e.getBranchID()).get();
				if (mainBranches.contains(branch)) {
					mainBranches.remove(branch);
				}
			});
			mainDealer.setMainBranches(mainBranches);
			mainDealerRepository.save(mainDealer);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Set<BranchDto> showMainBranches(String mainDealerID) {
		Set<BranchDto> notContainMainBranches = new LinkedHashSet<BranchDto>();

		MainDealer mainDealer = mainDealerRepository.findById(mainDealerID).get();
		List<Branch> allBranches = branchRepository.findByState(mainDealer.getState());
		List<Branch> mainBranches = mainDealer.getMainBranches();

		if (!allBranches.isEmpty()) {
			allBranches.stream().forEach(e -> {
				if (!mainBranches.contains(e)) {
					BranchDto branchDto = BranchDto.builder().branchID(e.getBranchID()).branchName(e.getBranchName())
							.state(e.getState()).area(e.getArea()).region(e.getRegion()).build();
					notContainMainBranches.add(branchDto);
				}
			});
		}
		return notContainMainBranches;
	}

	@Override
	public Set<BranchDto> showMainBranches(String mainDealerID, String region, String area) {
		Set<BranchDto> notContainMainBranches = new LinkedHashSet<BranchDto>();

//		System.err.println(notContainMainBranches.toString());

		MainDealer mainDealer = mainDealerRepository.findById(mainDealerID).get();
		List<Branch> allBranches = branchRepository.findByState(mainDealer.getState());
		List<Branch> mainBranches = mainDealer.getMainBranches();

		/** e.getMainDealer==null */ // To Get Non Use Dealer

		if (region.equalsIgnoreCase("null") & area.equalsIgnoreCase("null")) {
			allBranches.stream().forEach(e -> {
				if (!mainBranches.contains(e)) {
					BranchDto branchDto = BranchDto.builder().branchID(e.getBranchID()).branchName(e.getBranchName())
							.state(e.getState()).area(e.getArea()).region(e.getRegion()).build();
					notContainMainBranches.add(branchDto);
				}
			});
			return notContainMainBranches;
		} else if (!region.equalsIgnoreCase("null") & area.equalsIgnoreCase("null")) {
			allBranches.stream().filter(e -> e.getRegion().equalsIgnoreCase(region)).forEach(e -> {
				if (!mainBranches.contains(e)) {
					BranchDto branchDto = BranchDto.builder().branchID(e.getBranchID()).branchName(e.getBranchName())
							.state(e.getState()).area(e.getArea()).region(e.getRegion()).build();
					notContainMainBranches.add(branchDto);
				}
			});
			return notContainMainBranches;
		} else {// (!region.equalsIgnoreCase("null")&& !area.equalsIgnoreCase("null"))
			allBranches.stream()
					.filter(e -> e.getArea().equalsIgnoreCase(area) && e.getRegion().equalsIgnoreCase(region))
					.forEach(e -> {
						if (!mainBranches.contains(e)) {
							BranchDto branchDto = BranchDto.builder().branchID(e.getBranchID())
									.branchName(e.getBranchName()).state(e.getState()).area(e.getArea())
									.region(e.getRegion()).build();
							notContainMainBranches.add(branchDto);
						}
					});
			return notContainMainBranches;
		}
	}

	@Override
	public Set<BranchDto> showAvaliableMainBranches(String mainDealerID) {
		Set<BranchDto> branchs = new LinkedHashSet<BranchDto>();
		MainDealer mainDealer = mainDealerRepository.findById(mainDealerID).get();
		List<Branch> mainBranches = mainDealer.getMainBranches();
		mainBranches.stream().forEach(e -> {
			BranchDto branchDto = BranchDto.builder().branchID(e.getBranchID()).branchName(e.getBranchName())
					.state(e.getState()).area(e.getArea()).region(e.getRegion()).build();
			branchs.add(branchDto);
		});
		return branchs;
	}

	// ===========================

	@Override
	public Set<BranchDto> showSubBranches(String subDealearID) {
		Set<BranchDto> notContainMainBranches = new LinkedHashSet<BranchDto>();

		SubDealer subDealer = subDealerRepository.findById(subDealearID).get();
		List<Branch> allBranches = branchRepository.findByState(subDealer.getState());
		List<Branch> subBranches = subDealer.getSubBranches();

		if (!allBranches.isEmpty()) {
			allBranches.stream().forEach(e -> {
				if (!subBranches.contains(e)) {
					BranchDto branchDto = BranchDto.builder().branchID(e.getBranchID()).branchName(e.getBranchName())
							.state(e.getState()).area(e.getArea()).region(e.getRegion()).build();
					notContainMainBranches.add(branchDto);
				}
			});
		}
		return notContainMainBranches;
	}

	@Override
	public Set<BranchDto> showSubBranches(String subDealerID, String region, String area) {
		Set<BranchDto> notContainMainBranches = new LinkedHashSet<BranchDto>();
		SubDealer subDealer = subDealerRepository.findById(subDealerID).get();
		List<Branch> allBranches = branchRepository.findByState(subDealer.getState());
		List<Branch> subBranches = subDealer.getSubBranches();

		if (region.equalsIgnoreCase("null") & area.equalsIgnoreCase("null")) {
			allBranches.stream().forEach(e -> {
				if (!subBranches.contains(e)) {
					BranchDto branchDto = BranchDto.builder().branchID(e.getBranchID()).branchName(e.getBranchName())
							.state(e.getState()).area(e.getArea()).region(e.getRegion()).build();
					notContainMainBranches.add(branchDto);
				}
			});
			return notContainMainBranches;
		} else if (!region.equalsIgnoreCase("null") & area.equalsIgnoreCase("null")) {
			allBranches.stream().filter(e -> e.getRegion().equalsIgnoreCase(region)).forEach(e -> {
				if (!subBranches.contains(e)) {
					BranchDto branchDto = BranchDto.builder().branchID(e.getBranchID()).branchName(e.getBranchName())
							.state(e.getState()).area(e.getArea()).region(e.getRegion()).build();
					notContainMainBranches.add(branchDto);
				}
			});
			return notContainMainBranches;
		} else {// (!region.equalsIgnoreCase("null")&& !area.equalsIgnoreCase("null"))
			allBranches.stream()
					.filter(e -> e.getArea().equalsIgnoreCase(area) && e.getRegion().equalsIgnoreCase(region))
					.forEach(e -> {
						if (!subBranches.contains(e)) {
							BranchDto branchDto = BranchDto.builder().branchID(e.getBranchID())
									.branchName(e.getBranchName()).state(e.getState()).area(e.getArea())
									.region(e.getRegion()).build();
							notContainMainBranches.add(branchDto);
						}
					});
			return notContainMainBranches;
		}
	}

	@Override
	public Set<BranchDto> showAvaliableSubBranches(String subDealerID) {
		Set<BranchDto> containBranches = new LinkedHashSet<>();
		List<Branch> subBranches = subDealerRepository.findById(subDealerID).get().getSubBranches();
		subBranches.stream().forEach(e -> {
			BranchDto branchDto = BranchDto.builder().branchID(e.getBranchID()).branchName(e.getBranchName())
					.state(e.getState()).area(e.getArea()).region(e.getRegion()).build();
			containBranches.add(branchDto);
		});
		return containBranches;
	}

	/**
	 * @API For Added All Branches Base on MainDealer
	 */
	@Override
	public String addAllMainBranches(String mainDealerID, String subDealerID) {
		List<Branch> mainBranches = mainDealerRepository.findById(mainDealerID).get().getMainBranches();
		if (!mainBranches.isEmpty()) {
			SubDealer subDealer = subDealerRepository.findById(subDealerID).get();
			List<Branch> subBranches = subDealer.getSubBranches();
			mainBranches.stream().forEach(e -> {
				if (!subBranches.contains(e))
					subBranches.add(e);
			});
			subDealer.setSubBranches(subBranches);
			subDealerRepository.save(subDealer);
			return "Sucessfully Added";
		}
		return "Not Added Sucessfully";
	}

	@Override
	public String addSubBranches(String subDealerID, List<BranchDto> branchs) {
		SubDealer subDealer = subDealerRepository.findById(subDealerID).get();
		List<Branch> subBranches = subDealer.getSubBranches();
		if (!branchs.isEmpty()) {
			branchs.stream().forEach(e -> {
				Branch branch = branchRepository.findById(e.getBranchID()).get();
				if (!subBranches.contains(branch)) {
					subBranches.add(branch);
				}
			});
			subDealer.setSubBranches(subBranches);
			subDealerRepository.save(subDealer);
			return "Sucessfully Added";
		}
		return "Not Added Sucessfully";
	}

	@Override
	public Boolean removeSubBranches(String subDealerID, List<BranchDto> branchs) {
		SubDealer subDealer = subDealerRepository.findById(subDealerID).get();
		List<Branch> subBranches = subDealer.getSubBranches();
		if (!branchs.isEmpty()) {
			branchs.stream().forEach(e -> {
				Branch branch = branchRepository.findById(e.getBranchID()).get();

				if (subBranches.contains(branch))
					subBranches.remove(branch);

			});
			subDealer.setSubBranches(subBranches);
			subDealerRepository.save(subDealer);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

//===============================================================================================	
//=============================NON USE API=======================================================

	// ===============================Variants================================================

	@Override
	public List<VehicleVariantDto> mainVariants(String mainDealerID) {
		// Create List For Return Statement
		List<VehicleVariantDto> vehicleVariantDto = new ArrayList<VehicleVariantDto>();

		Optional<MainDealer> dealer = mainDealerRepository.findById(mainDealerID);
		if (dealer.isPresent()) {
			MainDealer mainDealer = dealer.get();

			List<VehicalPrice> variants = vehicalPriceRepository.findByStateAndOem(
					mainDealer.getState().trim().toUpperCase(), mainDealer.getManufacturerName().trim().toUpperCase());

			if (!variants.isEmpty()) {
				variants.stream().forEach(e -> {
					List<VehicalPrice> veriants = dealer.get().getVeriants();
					if (!veriants.contains(e)) {
						VehicleVariantDto vehicleVariant = new VehicleVariantDto();
						vehicleVariant.setVariantID(e.getVehicalPriceID());
						vehicleVariant.setVariantName(e.getVariantName());
						vehicleVariantDto.add(vehicleVariant);
					}
				});
			}
			return vehicleVariantDto;
		}
		return vehicleVariantDto;
	}

	@Override
	public List<VehicleVariantDto> avaliableMainVariants(String mainDealerID) {
		MainDealer mainDealer = mainDealerRepository.findById(mainDealerID).get();
		List<VehicalPrice> veriants = mainDealer.getVeriants();
		ArrayList<VehicleVariantDto> avaliableVariants = new ArrayList<VehicleVariantDto>();
		if (!veriants.isEmpty()) {
			veriants.stream().forEach(e -> {
				VehicleVariantDto vehicleVariantDto = new VehicleVariantDto();
				vehicleVariantDto.setVariantID(e.getVehicalPriceID());
				vehicleVariantDto.setVariantName(e.getVariantName());
				avaliableVariants.add(vehicleVariantDto);
			});
		}
		return avaliableVariants;
	}

	@Override
	public boolean addVeriantsMian(String mainDealerID, List<VehicleVariantDto> variants) {
		Optional<MainDealer> dealer = mainDealerRepository.findById(mainDealerID);
		if (dealer.isPresent()) {
			MainDealer mainDealer = dealer.get();
			List<VehicalPrice> varientsList = mainDealer.getVeriants();
			variants.stream().forEach(e -> {
				Optional<VehicalPrice> optionalData = vehicalPriceRepository.findById(e.getVariantID());
				if (optionalData.isPresent()) {
					VehicalPrice vehicalPriceData = optionalData.get();
					if (!varientsList.contains(vehicalPriceData))
						varientsList.add(vehicalPriceData);
				}
			});
			mainDealer.setVeriants(varientsList);
			mainDealerRepository.save(mainDealer);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean removeMainVariant(String mainDealerID, List<VehicleVariantDto> variants) {
		MainDealer mainDealer = mainDealerRepository.findById(mainDealerID).get();
		List<VehicalPrice> veriants = mainDealer.getVeriants();
		variants.stream().forEach(e -> {
			Optional<VehicalPrice> optionalData = vehicalPriceRepository.findById(e.getVariantID());
			if (optionalData.isPresent()) {
				VehicalPrice vehicalPrice = optionalData.get();
				veriants.remove(vehicalPrice);
			}
		});
		mainDealer.setVeriants(veriants);
		MainDealer responce = mainDealerRepository.save(mainDealer);
		if (responce != null)
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	@Override
	public List<VehicleVariantDto> subVariants(String subDealerID) {
		// Create Empty DtoList For Return
		ArrayList<VehicleVariantDto> varients = new ArrayList<VehicleVariantDto>();

		// Get Sub_Dealer BY SubDealerID
		SubDealer subDealer = subDealerRepository.findById(subDealerID).get();

		// Get SubDealer Existing Variants
		List<VehicalPrice> subDealerVeriants = subDealer.getVehicleVeriants();

		MainDealer mainDealer = mainDealerRepository.findById(subDealer.getMainDealers().getMainDealerID()).get();

		// Get Main Dealer Existing Variants
		List<VehicalPrice> veriants = mainDealer.getVeriants();

		veriants.stream().forEach(e -> {
			if (!subDealerVeriants.contains(e)) {
				VehicleVariantDto vehicleVariantDto = new VehicleVariantDto();
				vehicleVariantDto.setVariantID(e.getVehicalPriceID());
				vehicleVariantDto.setVariantName(e.getVariantName());
				varients.add(vehicleVariantDto);
			}
		});
		return varients;
	}

	@Override
	public List<VehicleVariantDto> avaliableSubVariants(String subDealerID) {
		SubDealer subDealer = subDealerRepository.findById(subDealerID).get();
		List<VehicalPrice> veriants = subDealer.getVehicleVeriants();
		ArrayList<VehicleVariantDto> avaliableVariants = new ArrayList<VehicleVariantDto>();
		if (!veriants.isEmpty()) {
			veriants.stream().forEach(e -> {
				VehicleVariantDto vehicleVariantDto = new VehicleVariantDto();
				vehicleVariantDto.setVariantID(e.getVehicalPriceID());
				vehicleVariantDto.setVariantName(e.getVariantName());
				avaliableVariants.add(vehicleVariantDto);
			});
		}
		return avaliableVariants;
	}

	@Override
	public boolean addVariantSub(String subDealerID, List<VehicleVariantDto> variants) {
		// Get SubDealer By SubDealerID
		SubDealer subDealer = subDealerRepository.findById(subDealerID).get();
		List<VehicalPrice> subVeriants = subDealer.getVehicleVeriants();
		variants.stream().forEach(e -> {
			VehicalPrice vehicalPriceData = vehicalPriceRepository.findById(e.getVariantID()).get();
			if (!subVeriants.contains(vehicalPriceData))
				subVeriants.add(vehicalPriceData);
		});
		subDealer.setVehicleVeriants(subVeriants);
		subDealerRepository.save(subDealer);
		return Boolean.TRUE;
	}

	@Override
	public Boolean removeSubVariant(String subDealerID, List<VehicleVariantDto> variants) {
		SubDealer subDealer = subDealerRepository.findById(subDealerID).get();
		List<VehicalPrice> vehicleVeriants = subDealer.getVehicleVeriants();
		variants.stream().forEach(e -> {
			Optional<VehicalPrice> optionalData = vehicalPriceRepository.findById(e.getVariantID());
			if (optionalData.isPresent()) {
				VehicalPrice vehical = optionalData.get();
				vehicleVeriants.remove(vehical);
			}
		});
		subDealer.setVehicleVeriants(vehicleVeriants);
		SubDealer response = subDealerRepository.save(subDealer);
		if (response != null)
			return Boolean.TRUE;

		return Boolean.FALSE;
	}

}
