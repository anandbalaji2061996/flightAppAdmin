package com.flightapp.admin.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.admin.DAO.FlightDetails;
import com.flightapp.admin.DAO.LoginCredentials;
import com.flightapp.admin.Service.FlightDetailService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin/api/v1.0/flight")
public class FlightDetailsController {

	@Autowired
	FlightDetailService service;

	@PostMapping("/login")
	public String login(@RequestBody LoginCredentials credentials) {
		return service.login(credentials);
	}

	@PostMapping("/airline/register")
	public FlightDetails registerAirlineAndInventory(@RequestBody FlightDetails details) {
		return service.registerAirlineAndInventory(details);
	}

	@PutMapping("/airline/inventory/add/{flightNumber}")
	public FlightDetails updateFlightInventory(@PathVariable("flightNumber") String flightNumber,
			@RequestBody FlightDetails details) {
		return service.updateFlightInventory(flightNumber, details);
	}
	
	@GetMapping("/airline")
	public List<FlightDetails> getAllFlightDetails() {
		return service.getAllFlightDetails();
	}
}
