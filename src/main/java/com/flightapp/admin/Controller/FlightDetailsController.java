package com.flightapp.admin.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.admin.DAO.FlightDetails;
import com.flightapp.admin.DAO.LoginCredentials;
import com.flightapp.admin.Exception.AdminNotFoundException;
import com.flightapp.admin.Exception.BadRequestException;
import com.flightapp.admin.Exception.FlightAlreadyFoundException;
import com.flightapp.admin.Exception.FlightNotFoundException;
import com.flightapp.admin.Service.FlightDetailService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin/api/v1.0/flight")
public class FlightDetailsController {

	@Autowired
	FlightDetailService service;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginCredentials credentials) throws AdminNotFoundException{
		return new ResponseEntity<>(service.login(credentials),HttpStatus.OK);
	}

	@PostMapping("/airline/register")
	public ResponseEntity<FlightDetails> registerAirlineAndInventory(@RequestBody FlightDetails details) throws BadRequestException, FlightAlreadyFoundException{
		return new ResponseEntity<>(service.registerAirlineAndInventory(details), HttpStatus.CREATED);
	}

	@PutMapping("/airline/inventory/add/{flightNumber}")
	public ResponseEntity<FlightDetails> updateFlightInventory(@PathVariable("flightNumber") String flightNumber,
			@RequestBody FlightDetails details) throws FlightNotFoundException {
		return new ResponseEntity<>(service.updateFlightInventory(flightNumber, details), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/airline")
	public ResponseEntity<List<FlightDetails>> getAllFlightDetails() {
		return new ResponseEntity<>(service.getAllFlightDetails(), HttpStatus.OK);
	}
	
	@GetMapping("/airline/{airline}")
	public ResponseEntity<List<FlightDetails>> getAllFlightDetailsBySearch(@PathVariable("airline") String airline) {
		return new ResponseEntity<>(service.getAllFlightDetailsBySearch(airline), HttpStatus.OK);
	}
	
	@DeleteMapping("/airline/delete/{flightNumber}")
	public ResponseEntity<String> deleteFlightDetails(@PathVariable("flightNumber") String flightNumber) throws FlightNotFoundException {
		return new ResponseEntity<>(service.deleteFlightDetails(flightNumber), HttpStatus.ACCEPTED);
	}
}
