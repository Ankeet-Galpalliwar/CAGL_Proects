package com.cagl.Service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cagl.Entity.DigiAgilCustomer;
import com.cagl.Service.TwlEligilibilityService;
import com.cagl.dto.DigiAgilCustomerDto;
import com.cagl.repository.TwlEligbilityRepository;

@Service
public class TwlEligbilityServiceImpl implements TwlEligilibilityService {
	@Autowired
	TwlEligbilityRepository eligbilityRepository;
	@Override
	public DigiAgilCustomerDto insertCustomerData(DigiAgilCustomerDto digiAgilCustomerDto) {
		DigiAgilCustomer customer=new DigiAgilCustomer();
		BeanUtils.copyProperties(digiAgilCustomerDto, customer);
		DigiAgilCustomer save = eligbilityRepository.save(customer);
		BeanUtils.copyProperties(save, digiAgilCustomerDto);
		return digiAgilCustomerDto;
	}
}
