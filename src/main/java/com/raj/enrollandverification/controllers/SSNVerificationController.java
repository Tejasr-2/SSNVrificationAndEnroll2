package com.raj.enrollandverification.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.enrollandverification.constants.AppConstants;
import com.raj.enrollandverification.domains.Citizen;
import com.raj.enrollandverification.exceptionhandler.CitizenNotFoundException;
import com.raj.enrollandverification.response.SSNResponse;
import com.raj.enrollandverification.services.CitizenService;

@RestController
@RequestMapping("/SSN")
public class SSNVerificationController {

	@Autowired
	CitizenService citizenService;
	
	@GetMapping("/citizens/{citizenSSN}")
	public ResponseEntity<?> verifySSN(@PathVariable("citizenSSN")String citizenSSN) {
		
		//String ssn = ssnToString(citizenSSN);
		
		String ssn = citizenSSN;
		
		System.out.println(ssn);
		Citizen citizen = citizenService.findCitizenById(ssn);
		
		//Exception
		if( citizen.getFirstName() == null)
		{
			SSNResponse res = new SSNResponse();
			res.setSsn("Null");
			res.setStatus(AppConstants.NO_SUCH_CITIZEN);
			
			return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
		}
		
		
		
		return new ResponseEntity<>(citizen,HttpStatus.CREATED);
	}

	private String ssnToString(String citizenSSN) {
		StringBuilder str = new StringBuilder(citizenSSN);
		try {
		str.deleteCharAt(4);
		str.deleteCharAt(6);
		}
		catch (Exception e) {
			throw new CitizenNotFoundException();
		}
		return str.toString();
	}
	
	
	
	
}
