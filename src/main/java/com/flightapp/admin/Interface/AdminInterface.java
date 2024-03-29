package com.flightapp.admin.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.admin.DAO.FlightDetails;

@Repository
public interface AdminInterface extends JpaRepository<FlightDetails, String> {

	List<FlightDetails> findByAirline(String airline);
	
	List<FlightDetails> findByFromPlaceAndToPlace(String fromplace, String toPlace);
	
	List<FlightDetails> findByAirlineAndFromPlaceAndToPlace(String airline, String fromPlace, String toPlace);
}
