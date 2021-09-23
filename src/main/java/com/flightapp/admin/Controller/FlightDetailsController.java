package com.flightapp.admin.Controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/admin/api/v1.0/flight")
public class FlightDetailsController {
	
    private static final Logger logger = LogManager.getLogger(FlightDetailsController.class);

	@Autowired
	FlightDetailService service;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginCredentials credentials) throws AdminNotFoundException{
		logger.info("Admin login check!");
		return new ResponseEntity<>(service.login(credentials),HttpStatus.OK);
	}

	@PostMapping("/airline/register")
	public ResponseEntity<FlightDetails> registerAirlineAndInventory(@RequestBody FlightDetails details) throws BadRequestException, FlightAlreadyFoundException{
		logger.info("Register new Airline!");
		return new ResponseEntity<>(service.registerAirlineAndInventory(details), HttpStatus.CREATED);
	}

	@PutMapping("/airline/inventory/add/{flightNumber}")
	public ResponseEntity<FlightDetails> updateFlightInventory(@PathVariable("flightNumber") String flightNumber,
			@RequestBody FlightDetails details) throws FlightNotFoundException {
		logger.info("Update inventory!");
		return new ResponseEntity<>(service.updateFlightInventory(flightNumber, details), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/airline/flightNumber/{flightNumber}")
	public ResponseEntity<FlightDetails> getFlightDetailsByFlightNumber(@PathVariable("flightNumber") String flightNumber) throws FlightNotFoundException {
		logger.info("Get flight details " + flightNumber);
		return new ResponseEntity<>(service.getFlightDetailsByFlightNumber(flightNumber), HttpStatus.OK);
	}
	
	@GetMapping("/airline")
	public ResponseEntity<List<FlightDetails>> getAllFlightDetails() {
		logger.info("Get All flight details!");
		return new ResponseEntity<>(service.getAllFlightDetails(), HttpStatus.OK);
	}
	
	@GetMapping("/airline/fromPlace/{fromPlace}/toPlace/{toPlace}")
	public ResponseEntity<List<FlightDetails>> getAllFlightDetailsBySearch(@PathVariable("fromPlace") String fromPlace, @PathVariable("toPlace") String toPlace) {
		logger.info("Search for "+ fromPlace + " & " + toPlace);
		return new ResponseEntity<>(service.getAllFlightDetailsBySearch(fromPlace, toPlace), HttpStatus.OK);
	}
	
	@DeleteMapping("/airline/delete/{flightNumber}")
	public ResponseEntity<String> deleteFlightDetails(@PathVariable("flightNumber") String flightNumber) throws FlightNotFoundException {
		logger.info("Delete flight details " + flightNumber);
		return new ResponseEntity<>(service.deleteFlightDetails(flightNumber), HttpStatus.ACCEPTED);
	}
}
