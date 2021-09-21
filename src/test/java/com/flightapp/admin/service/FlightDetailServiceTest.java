package com.flightapp.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.flightapp.admin.DAO.FlightDetails;
import com.flightapp.admin.DAO.LoginCredentials;
import com.flightapp.admin.Exception.AdminNotFoundException;
import com.flightapp.admin.Exception.BadRequestException;
import com.flightapp.admin.Exception.FlightAlreadyFoundException;
import com.flightapp.admin.Exception.FlightNotFoundException;
import com.flightapp.admin.Interface.AdminInterface;
import com.flightapp.admin.Service.FlightDetailService;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FlightDetailServiceTest {

	@Autowired
	AdminInterface adminInterface;

	private FlightDetailService service;

	private FlightDetails details;

	@BeforeEach
	public void setup1() {
		service = new FlightDetailService(adminInterface);

		details = new FlightDetails();
		details.setAirline("testAirline");
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
	}

	@AfterEach
	public void setup2() {
		adminInterface.deleteAll();
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
	public void registerAirlineAndInventoryTest() throws BadRequestException, FlightAlreadyFoundException {
		Throwable thrown = catchThrowable(() -> service.registerAirlineAndInventory(null));

		assertThat(thrown).isInstanceOf(BadRequestException.class);

		FlightDetails response = service.registerAirlineAndInventory(details);

		assertNotNull(response);
		assertEquals("testAirline", response.getAirline());
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

		thrown = catchThrowable(() -> service.registerAirlineAndInventory(details));

		assertThat(thrown).isInstanceOf(FlightAlreadyFoundException.class);

	}

	@Test
	public void updateFlightInventoryTest()
			throws FlightNotFoundException, BadRequestException, FlightAlreadyFoundException {
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

		FlightDetails response = service.updateFlightInventory("test123", details);

		assertNotNull(response);
		assertEquals(13000, response.getTicketCost());
		assertEquals("WeekEnd", response.getScheduledDays());
	}

	@Test
	public void getAllFlightDetailsTest() throws BadRequestException, FlightAlreadyFoundException {
		service.registerAirlineAndInventory(details);

		assertTrue(service.getAllFlightDetails().size() >= 1);
	}

	@Test
	public void getAllFlightDetailsBySearchTest() throws BadRequestException, FlightAlreadyFoundException {
		service.registerAirlineAndInventory(details);
		assertEquals(1, service.getAllFlightDetailsBySearch("testAirline").size());
		assertEquals(0, service.getAllFlightDetailsBySearch("testAirline2").size());
	}
	
	@Test
	public void deleteFlightDetailsTest() throws FlightNotFoundException, BadRequestException, FlightAlreadyFoundException {
		FlightDetails response = service.registerAirlineAndInventory(details);
		assertNotNull(adminInterface.findById(response.getFlightNumber()));
		
		assertEquals("Success", service.deleteFlightDetails(response.getFlightNumber()));
		assertFalse(adminInterface.findById(response.getFlightNumber()).isPresent());
	}
}
