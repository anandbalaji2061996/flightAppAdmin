package com.flightapp.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.flightapp.admin.DAO.Airline;
import com.flightapp.admin.DAO.FlightDetails;
import com.flightapp.admin.DAO.LoginCredentials;
import com.flightapp.admin.Exception.AdminNotFoundException;
import com.flightapp.admin.Exception.AirlineNotFoundException;
import com.flightapp.admin.Exception.BadRequestException;
import com.flightapp.admin.Exception.FlightAlreadyFoundException;
import com.flightapp.admin.Exception.FlightNotFoundException;
import com.flightapp.admin.Interface.AdminInterface;
import com.flightapp.admin.Interface.AirlineRepository;
import com.flightapp.admin.Service.FlightDetailService;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FlightDetailServiceTest {

	@Autowired
	AdminInterface adminInterface;

	@Autowired
	AirlineRepository airlineRepository;
	
	private FlightDetailService service;

	private FlightDetails details;

	@BeforeEach
	public void setup1() {
		service = new FlightDetailService(adminInterface, airlineRepository);
		service.username = "admin";
		service.password = "admin";

		Airline a = new Airline();
		a.setAddress("testaddress");
		a.setContactNumber("98751214133");
		a.setName("testAI");
		a.setFlightDetails(new ArrayList<>());;
		airlineRepository.save(a);
		
		details = new FlightDetails();
		details.setAirline("testAI");
		details.setEndDateTime("02:00");
		details.setFlightNumber("test123");
		details.setFromPlace("place1");
		details.setMeals("Veg,Non-Veg");
		details.setNosOfBusinessClassSeats(12);
		details.setNosOfNonBusinessClassSeats(11);
		details.setNosOfRows(6);
		details.setScheduledDays("Daily");
		details.setStartDateTime("00:00");
		details.setTicketCost(12000);
		details.setToPlace("place2");
		details.setDiscountCode("testcode20");
		details.setDiscount(20);
		
	}

	@AfterEach
	public void setup2() {
		adminInterface.deleteAll();
		airlineRepository.deleteAll();
	}

	@Test
	public void loginTest() throws AdminNotFoundException {
		LoginCredentials credentials = new LoginCredentials();
		credentials.setPassword("admin");
		credentials.setUsername("admin");
		assertEquals("Success", service.login(credentials));

		credentials.setUsername("admin123");
		Throwable thrown = catchThrowable(() -> service.login(credentials));

		assertThat(thrown).isInstanceOf(AdminNotFoundException.class);
	}

	@Test
	public void registerAirlineAndInventoryTest() throws BadRequestException, FlightAlreadyFoundException, AirlineNotFoundException {
		Throwable thrown = catchThrowable(() -> service.registerAirlineAndInventory(null));

		assertThat(thrown).isInstanceOf(BadRequestException.class);
		
		assertTrue(service.registerAirlineAndInventory(details).equals("Flight Registered successfully"));

		FlightDetails response = adminInterface.findById(details.getFlightNumber()).orElse(null);

		assertNotNull(response);
		assertEquals("testAI", response.getAirline());
		assertEquals("02:00", response.getEndDateTime());
		assertEquals("test123", response.getFlightNumber());
		assertEquals("place1", response.getFromPlace());
		assertEquals("Veg,Non-Veg", response.getMeals());
		assertEquals(12, response.getNosOfBusinessClassSeats());
		assertEquals(11, response.getNosOfNonBusinessClassSeats());
		assertEquals(6, response.getNosOfRows());
		assertEquals("Daily", response.getScheduledDays());
		assertEquals("00:00", response.getStartDateTime());
		assertEquals(12000, response.getTicketCost());
		assertEquals("place2", response.getToPlace());
		assertEquals("testcode20", response.getDiscountCode());
		assertEquals(20, response.getDiscount());

		thrown = catchThrowable(() -> service.registerAirlineAndInventory(details));

		assertThat(thrown).isInstanceOf(FlightAlreadyFoundException.class);

	}

	@Test
	public void updateFlightInventoryTest()
			throws FlightNotFoundException, BadRequestException, FlightAlreadyFoundException, AirlineNotFoundException {
		Throwable thrown = catchThrowable(() -> service.updateFlightInventory("test1", null));

		assertThat(thrown).isInstanceOf(FlightNotFoundException.class);

		service.registerAirlineAndInventory(details);

		details.setAirline("testAirline");
		details.setEndDateTime("02:00");
		details.setFromPlace("place1");
		details.setMeals("Veg,Non-Veg");
		details.setNosOfBusinessClassSeats(12);
		details.setNosOfNonBusinessClassSeats(11);
		details.setNosOfRows(6);
		details.setScheduledDays("WeekEnd");
		details.setStartDateTime("00:00");
		details.setTicketCost(13000);
		details.setToPlace("place2");
		details.setDiscountCode("testcode30");
		details.setDiscount(30);

		FlightDetails response = service.updateFlightInventory("test123", details);

		assertNotNull(response);
		assertEquals(13000, response.getTicketCost());
		assertEquals("WeekEnd", response.getScheduledDays());
		assertEquals("testcode30", response.getDiscountCode());
		assertEquals(30, response.getDiscount());
	}

	@Test
	public void getAllFlightDetailsTest() throws BadRequestException, FlightAlreadyFoundException, AirlineNotFoundException {
		service.registerAirlineAndInventory(details);

		assertTrue(service.getAllFlightDetails().size() >= 1);
	}

	@Test
	public void getAllFlightDetailsBySearchTest() throws BadRequestException, FlightAlreadyFoundException, AirlineNotFoundException {
		service.registerAirlineAndInventory(details);
		assertEquals(1, service.getAllFlightDetailsBySearch("place1", "place2").size());
		assertEquals(0, service.getAllFlightDetailsBySearch("place1","testAirline2").size());
	}
	
	@Test
	public void getAllFlightDetailsBySearchByAirlineNameAndPlaceTest() throws BadRequestException, FlightAlreadyFoundException, AirlineNotFoundException {
		service.registerAirlineAndInventory(details);
		assertEquals(1, service.getAllFlightDetailsByAirline("testAI", "place1", "place2").size());
		assertEquals(0, service.getAllFlightDetailsByAirline("testAirline2", "place1", "place2").size());
	}
	
	@Test
	public void deleteFlightDetailsTest() throws FlightNotFoundException, BadRequestException, FlightAlreadyFoundException, AirlineNotFoundException {
		service.registerAirlineAndInventory(details);
		FlightDetails response = adminInterface.findById(details.getFlightNumber()).orElse(null);
		assertNotNull(adminInterface.findById(response.getFlightNumber()));
		
		assertEquals("Success", service.deleteFlightDetails(response.getFlightNumber()));
		assertFalse(adminInterface.findById(response.getFlightNumber()).isPresent());
	}
	
	@Test
	public void deleteFlightDetailsAirlineTest() throws FlightNotFoundException, BadRequestException, FlightAlreadyFoundException, AirlineNotFoundException {
		service.registerAirlineAndInventory(details);
		FlightDetails response = adminInterface.findById(details.getFlightNumber()).orElse(null);		assertNotNull(adminInterface.findByAirline(response.getAirline()));
		
		assertEquals("Success", service.deleteFlightDetailsAirline(response.getAirline()));
	}
	
	@Test
	public void getFlightDetailsByFlightNumberTest() throws FlightNotFoundException, BadRequestException, FlightAlreadyFoundException, AirlineNotFoundException {
		service.registerAirlineAndInventory(details);
		FlightDetails response = adminInterface.findById(details.getFlightNumber()).orElse(null);
		
		assertEquals(response.getFlightNumber(), service.getFlightDetailsByFlightNumber(response.getFlightNumber()).getFlightNumber());
	}
}
