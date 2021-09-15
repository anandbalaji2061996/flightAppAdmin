package com.flightapp.admin.Service;

import org.springframework.stereotype.Service;

import com.flightapp.admin.Interface.AdminInterface;

@Service
public class FlightDetailService {
 
	private final AdminInterface adminInterface;
	
	FlightDetailService(AdminInterface adminInterface) { 
		this.adminInterface = adminInterface;
	}
	
	
}
