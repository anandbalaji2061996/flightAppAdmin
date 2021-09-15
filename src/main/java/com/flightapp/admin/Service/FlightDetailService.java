package com.flightapp.admin.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flightapp.admin.DAO.FlightDetails;
import com.flightapp.admin.DAO.LoginCredentials;
import com.flightapp.admin.Interface.AdminInterface;

@Service
public class FlightDetailService {
 
	private final AdminInterface adminInterface;
	
	FlightDetailService(AdminInterface adminInterface) { 
		this.adminInterface = adminInterface;
	}
	
	public String login(LoginCredentials credentials) {
		if(credentials.getUsername().equalsIgnoreCase("admin") && credentials.getPassword().equalsIgnoreCase("admin")) {
			return "Success";
		} else 
			return "Invalid credentials";
	}
	
	public FlightDetails registerAirlineAndInventory(FlightDetails details) {
		return adminInterface.save(details);
	}
	
	public FlightDetails updateFlightInventory(String flightNumber, FlightDetails details) {
		FlightDetails flightDetails = adminInterface.findById(flightNumber).orElse(null);
		if(flightDetails != null) {
			flightDetails.setAirline(details.getAirline());
			flightDetails.setEndDateTime(details.getEndDateTime());
			flightDetails.setFromPlace(details.getFromPlace());
			flightDetails.setMeals(details.getMeals());
			flightDetails.setNosOfBusinessClassSeats(details.getNosOfBusinessClassSeats());
			flightDetails.setNosOfNonBusinessClassSeats(details.getNosOfNonBusinessClassSeats());
			flightDetails.setNosOfRows(details.getNosOfRows());
			flightDetails.setScheduledDays(details.getScheduledDays());
			flightDetails.setStartDateTime(details.getStartDateTime());
			flightDetails.setTicketCost(details.getTicketCost());
			flightDetails.setToPlace(details.getToPlace());
			
			adminInterface.save(flightDetails);
		}
		
		return flightDetails;
	}
	
	public List<FlightDetails> getAllFlightDetails() {
		return adminInterface.findAll();
	}
}
