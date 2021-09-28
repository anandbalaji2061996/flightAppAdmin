package com.flightapp.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.flightapp.admin.DAO.FlightAvailability;
import com.flightapp.admin.Exception.BadRequestException;
import com.flightapp.admin.Exception.SeatNotAvailableException;
import com.flightapp.admin.Interface.FlightAvailabilityRepository;
import com.flightapp.admin.Service.FlightSeatAvailability;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FlightSeatAvailabilityTest {

	@Autowired
	FlightAvailabilityRepository availabilityRepository;
	
	private FlightSeatAvailability availability;
	
	private FlightAvailability details;
	
	@BeforeEach 
	private void setup() {
		availability = new FlightSeatAvailability(availabilityRepository);
		
		details = new FlightAvailability();
				
		details.setFlightNumber("test123");
		details.setAirline("test");
		details.setFromPlace("place1");
		details.setToPlace("place2");
		details.setJourneyDate("01/01/2012");
		details.setNosOfBusinessClassSeats(1);
		details.setNosOfNonBusinessClassSeats(1);
		details.setNosOfBookedBusinessClassSeats(0);
		details.setNosOfBookedNonBusinessClassSeats(0);
	}
	
	@AfterEach
	public void setup2() {
		availabilityRepository.deleteAll();
	}
	
	@Test
	public void saveRecordTest() throws SeatNotAvailableException {
		details.setNosOfBookedBusinessClassSeats(10);
		
		Throwable thrown = catchThrowable(() -> availability.saveRecord(details));
		assertThat(thrown).isInstanceOf(SeatNotAvailableException.class);
		
		details.setNosOfBookedBusinessClassSeats(1);
		
		assertNotNull(availability.saveRecord(details));
		
		details.setNosOfBookedBusinessClassSeats(1);

		thrown = catchThrowable(() -> availability.saveRecord(details));
		assertThat(thrown).isInstanceOf(SeatNotAvailableException.class);
		
		details.setNosOfBookedNonBusinessClassSeats(10);
		thrown = catchThrowable(() -> availability.saveRecord(details));
		assertThat(thrown).isInstanceOf(SeatNotAvailableException.class);
	}
	
	@Test
	public void getRecordsTest() throws SeatNotAvailableException {
		details.setNosOfBookedNonBusinessClassSeats(1);
		assertNotNull(availability.saveRecord(details));
		
		FlightAvailability a = availability.getRecord(details);
		assertEquals("test123",a.getFlightNumber());
		assertEquals("test",a.getAirline());
		assertEquals("place1",a.getFromPlace());
		assertEquals("place2",a.getToPlace());
		assertEquals("01/01/2012",a.getJourneyDate());
		assertEquals(1,a.getNosOfBusinessClassSeats());
		assertEquals(1,a.getNosOfNonBusinessClassSeats());
		assertEquals(0,a.getNosOfBookedBusinessClassSeats());
		assertEquals(1,a.getNosOfBookedNonBusinessClassSeats());
	}
	
	@Test
	public void updateSeatAfterCancellationTest() throws BadRequestException, SeatNotAvailableException {
		Throwable thrown = catchThrowable(() -> availability.updateSeatRecordAfterCancellation(details));
		assertThat(thrown).isInstanceOf(BadRequestException.class);
		details.setNosOfBookedNonBusinessClassSeats(1);
		assertNotNull(availability.saveRecord(details));
		FlightAvailability a = availability.getRecord(details);
		assertNotNull(a);
		assertEquals(1,a.getNosOfBookedNonBusinessClassSeats());
		
		details.setNosOfBookedNonBusinessClassSeats(1);
		
		FlightAvailability aa = availability.updateSeatRecordAfterCancellation(details);
		assertEquals(0,aa.getNosOfBookedNonBusinessClassSeats());
		
	}
}
