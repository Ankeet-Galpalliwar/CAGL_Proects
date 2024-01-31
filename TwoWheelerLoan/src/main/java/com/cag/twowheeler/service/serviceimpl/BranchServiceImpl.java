package com.cag.twowheeler.service.serviceimpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cag.twowheeler.dto.BranchExcelDto;
import com.cag.twowheeler.entity.Branch;
import com.cag.twowheeler.repository.MainDealerRepository;
import com.cag.twowheeler.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	MainDealerRepository mainDealerRepository;
	int i;

	@Override
	public List<BranchExcelDto> branchMappingExcel() {

		i = 1;
		List<BranchExcelDto> branchExcel = new ArrayList<BranchExcelDto>();

		mainDealerRepository.findAll().stream().forEach(main -> {
			main.getMainBranches().stream().sorted(Comparator.comparing(Branch::getBranchID)).forEach(branch -> {
				branchExcel.add(BranchExcelDto.builder().branchID("BRANCH.ID:" + i++ + "=" + branch.getBranchID())
						.branchName(branch.getBranchName()).DealerID(main.getMainDealerID()).state(branch.getState())
						.build());
			});
			i = 1;
			main.getSubDealer().stream().forEach(sub -> {
				sub.getSubBranches().stream().sorted(Comparator.comparing(Branch::getBranchID)).forEach(subbranch -> {
					branchExcel
							.add(BranchExcelDto.builder().branchID("BRANCH.ID:" + i++ + "=" + subbranch.getBranchID())
									.branchName(subbranch.getBranchName()).DealerID(sub.getSubDealerID())
									.state(subbranch.getState()).build());
				});
				i = 1;
			});
			i = 1;
		});
		return branchExcel;
	}

}
