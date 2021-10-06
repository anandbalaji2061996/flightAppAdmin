package com.flightapp.admin.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.flightapp.admin.DAO.FlightDetails;
import com.flightapp.admin.DAO.LoginCredentials;
import com.flightapp.admin.Exception.AdminNotFoundException;
import com.flightapp.admin.Exception.BadRequestException;
import com.flightapp.admin.Exception.FlightAlreadyFoundException;
import com.flightapp.admin.Exception.FlightNotFoundException;
import com.flightapp.admin.Interface.AdminInterface;

@Service
public class FlightDetailService {

	private static final Logger logger = LoggerFactory.getLogger(FlightDetailService.class);

//	@Value("${admin.username}")
	public String username = "admin";

//	@Value("${admin.password}")
	public String password = "admin";

	private final AdminInterface adminInterface;

	public FlightDetailService(AdminInterface adminInterface) {
		this.adminInterface = adminInterface;
	}

	public String login(LoginCredentials credentials) throws AdminNotFoundException {
		if (credentials.getUsername().equalsIgnoreCase(username)
				&& credentials.getPassword().equalsIgnoreCase(password)) {
			return "Success";
		} else {
			logger.warn("Invalid username/password");
			throw new AdminNotFoundException("Invalid username / password credentials");
		}
	}

	public FlightDetails registerAirlineAndInventory(FlightDetails details)
			throws BadRequestException, FlightAlreadyFoundException {
		if (details == null) {
			logger.warn("Empty Body");
			throw new BadRequestException("Empty Body!");
		}
		if (adminInterface.findById(details.getFlightNumber()).isPresent()) {
			logger.warn("Flight details already exists!");
			throw new FlightAlreadyFoundException("Flight details already exists!");
		}
		return adminInterface.save(details);
	}

	public FlightDetails updateFlightInventory(String flightNumber, FlightDetails details)
			throws FlightNotFoundException {
		FlightDetails flightDetails = adminInterface.findById(flightNumber).orElse(null);
		if (flightDetails != null) {
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
			flightDetails.setDiscountCode(details.getDiscountCode());
			flightDetails.setDiscount(details.getDiscount());
			return adminInterface.save(flightDetails);
		}
		logger.warn("Flight details not found!");
		throw new FlightNotFoundException("Flight details not found!");
	}

	public List<FlightDetails> getAllFlightDetails() {
		return adminInterface.findAll();
	}

	public List<FlightDetails> getAllFlightDetailsBySearch(String fromPlace, String toPlace) {
		return adminInterface.findByFromPlaceAndToPlace(fromPlace, toPlace);
	}

	public List<FlightDetails> getAllFlightDetailsByAirline(String airline, String fromPlace, String toPlace) {
		if (airline != null && fromPlace != null && toPlace != null) {
			return adminInterface.findByAirlineAndFromPlaceAndToPlace(airline, fromPlace, toPlace);
		} else if (fromPlace != null && toPlace != null) {
			return adminInterface.findByFromPlaceAndToPlace(fromPlace, toPlace);
		} else {
			return adminInterface.findByAirline(airline);
		}
	}

	public FlightDetails getFlightDetailsByFlightNumber(String flightNumber) throws FlightNotFoundException {
		FlightDetails flightDetails = adminInterface.findById(flightNumber).orElse(null);
		if (flightDetails != null) {
			return flightDetails;
		}
		logger.warn("Flight details not found!");
		throw new FlightNotFoundException("Flight details not found!");
	}

	public String deleteFlightDetails(String flightNumber) throws FlightNotFoundException {
		if (!adminInterface.findById(flightNumber).isPresent()) {
			logger.warn("Flight details not found!");
			throw new FlightNotFoundException("Flight Details not found");
		}
		adminInterface.deleteById(flightNumber);

		return "Success";
	}

	public String deleteFlightDetailsAirline(String airline) {

		adminInterface.findByAirline(airline).forEach(details -> {
			adminInterface.deleteById(details.getFlightNumber());
		});

		return "Success";
	}
}
