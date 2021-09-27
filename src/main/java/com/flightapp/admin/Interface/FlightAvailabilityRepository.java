package com.flightapp.admin.Interface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.admin.DAO.FlightAvailability;

public interface FlightAvailabilityRepository extends JpaRepository<FlightAvailability, Long>{
	FlightAvailability findByFlightNumberAndFromPlaceAndToPlaceAndJourneyDate(String flightNumber, String fromPlace, String toPlace, String jpurneyDate);
	
}
