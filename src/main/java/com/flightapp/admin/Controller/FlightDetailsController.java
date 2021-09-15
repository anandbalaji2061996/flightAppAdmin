package com.flightapp.admin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.admin.DAO.FlightDetails;
import com.flightapp.admin.Interface.AdminInterface;

@RestController
@RequestMapping("/api/v1.0/flight")
public class FlightDetailsController {

	@Autowired
	AdminInterface adminInterface;
	
	@GetMapping("/admin/login/username{username}/password/{password}")
	public String login(@PathVariable("username") String username, @PathVariable("password") String password) {
		if(username == "admin" && password == "admin") {
			return "Success";
		} else 
			return "Invalid credentials";
	}
	
	@PostMapping("/airline/register")
	public FlightDetails registerAirlineAndInventory(@RequestBody FlightDetails details) {
		return adminInterface.save(details);
	}
	
	@PutMapping("/airline/inventory/add/{flightNumber}")
	public FlightDetails updateFlightInventory(@PathVariable("flightNumber") String flightNumber, @RequestBody FlightDetails details) {
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
}
