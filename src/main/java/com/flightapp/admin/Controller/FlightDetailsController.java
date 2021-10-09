package com.flightapp.admin.Controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.flightapp.admin.DAO.FlightAvailability;
import com.flightapp.admin.DAO.FlightDetails;
import com.flightapp.admin.DAO.LoginCredentials;
import com.flightapp.admin.Exception.AdminNotFoundException;
import com.flightapp.admin.Exception.AirlineNotFoundException;
import com.flightapp.admin.Exception.BadRequestException;
import com.flightapp.admin.Exception.FlightAlreadyFoundException;
import com.flightapp.admin.Exception.FlightNotFoundException;
import com.flightapp.admin.Exception.SeatNotAvailableException;
import com.flightapp.admin.Service.FlightDetailService;
import com.flightapp.admin.Service.FlightSeatAvailability;

@CrossOrigin("*")
@RestController
@RequestMapping("/api2/v1.0/admin/flight")
public class FlightDetailsController {
	
    private static final Logger logger = LoggerFactory.getLogger(FlightDetailsController.class);

	@Autowired
	FlightDetailService service;
	
	@Autowired
	FlightSeatAvailability availabilityService;

	@PostMapping(path = "/login", produces = {"application/text"})
	public ResponseEntity<String> login(@Valid @RequestBody LoginCredentials credentials) throws AdminNotFoundException{
		logger.info("Admin login check!");
		return new ResponseEntity<>(service.login(credentials),HttpStatus.OK);
	}

	@PostMapping(path = "/airline/register", produces = {"application/text"})
	public ResponseEntity<String> registerAirlineAndInventory(@Valid @RequestBody FlightDetails details) throws BadRequestException, FlightAlreadyFoundException, AirlineNotFoundException{
		logger.info("Register new Airline!");
		return new ResponseEntity<>(service.registerAirlineAndInventory(details), HttpStatus.CREATED);
	}

	@PutMapping(path = "/airline/inventory/add/{flightNumber}", produces = {"application/json"})
	public ResponseEntity<FlightDetails> updateFlightInventory(@Valid @PathVariable("flightNumber") String flightNumber,
			@RequestBody FlightDetails details) throws FlightNotFoundException {
		logger.info("Update inventory!");
		return new ResponseEntity<>(service.updateFlightInventory(flightNumber, details), HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path = "/airline/flightNumber/{flightNumber}", produces = {"application/json"})
	public ResponseEntity<FlightDetails> getFlightDetailsByFlightNumber(@Valid @PathVariable("flightNumber") String flightNumber) throws FlightNotFoundException {
		logger.info("Get flight details " + flightNumber);
		return new ResponseEntity<>(service.getFlightDetailsByFlightNumber(flightNumber), HttpStatus.OK);
	}
	
	@GetMapping(path = "/airline", produces = {"application/json"})
	public ResponseEntity<List<FlightDetails>> getAllFlightDetails() {
		logger.info("Get All flight details!");
		return new ResponseEntity<>(service.getAllFlightDetails(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/airline/fromPlace/{fromPlace}/toPlace/{toPlace}", produces = {"application/json"})
	public ResponseEntity<List<FlightDetails>> getAllFlightDetailsBySearch(@Valid @PathVariable("fromPlace") String fromPlace, @PathVariable("toPlace") String toPlace) {
		logger.info("Search for "+ fromPlace + " & " + toPlace);
		return new ResponseEntity<>(service.getAllFlightDetailsBySearch(fromPlace, toPlace), HttpStatus.OK);
	}
	
	@GetMapping(path = "/airline/airlineName/{airline}/fromPlace/{fromPlace}/toPlace/{toPlace}", produces = {"application/json"})
	public ResponseEntity<List<FlightDetails>> getAllFlightDetailsByAirline(@Valid @PathVariable("airline") String airline, @PathVariable("fromPlace") String fromPlace, @PathVariable("toPlace") String toPlace) {
		logger.info("Search for "+ airline);
		return new ResponseEntity<>(service.getAllFlightDetailsByAirline(airline, fromPlace, toPlace), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/airline/delete/{flightNumber}", produces = {"application/text"})
	public ResponseEntity<String> deleteFlightDetails(@Valid @PathVariable("flightNumber") String flightNumber) throws FlightNotFoundException {
		logger.info("Delete flight details " + flightNumber);
		return new ResponseEntity<>(service.deleteFlightDetails(flightNumber), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping(path = "/airline/airlineDelete/{airline}", produces = {"application/text"})
	public ResponseEntity<String> deleteFlightDetailsByAirline(@Valid @PathVariable("airline") String airline) {
		logger.info("Delete flight details " + airline);
		return new ResponseEntity<>(service.deleteFlightDetailsAirline(airline), HttpStatus.ACCEPTED);
	}
	
	@PostMapping(path = "/airline/availability", produces = {"application/json"})
	public ResponseEntity<FlightAvailability> saveAvailability(@Valid @RequestBody FlightAvailability availability) throws SeatNotAvailableException {
		logger.info("Update Availability");
		return new ResponseEntity<>(availabilityService.saveRecord(availability),HttpStatus.OK);				
	}
	
	@PostMapping(path = "/airline/seats/availability", produces = {"application/json"})
	public ResponseEntity<FlightAvailability> getAvailability(@Valid @RequestBody FlightAvailability availability) {
		logger.info("Get Availability");
		return new ResponseEntity<>(availabilityService.getRecord(availability),HttpStatus.OK);				
	}
	
	@PostMapping(path = "/airline/seats/availability/aftercancel", produces = {"application/json"})
	public ResponseEntity<FlightAvailability> updateSeatsAfterCancellation(@Valid @RequestBody FlightAvailability availability) throws BadRequestException {
		logger.info("Cancel seats Availability");
		return new ResponseEntity<>(availabilityService.updateSeatRecordAfterCancellation(availability),HttpStatus.OK);				
	}
	
}
