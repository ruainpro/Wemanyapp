package com.dao.wethemany.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Others_Services {
	
	@Value("${carbonconstant}")
	private int carbonconstant;

	@Value("${companyemission}")
	private int companyemission;

	
	public double calculateC02Emission(double productvalue) {
		
		double absosluteEmission=(productvalue/100);
		
		double c02EmisisionValue=((productvalue*carbonconstant*absosluteEmission)/100);
		return c02EmisisionValue;
		
	}

}
