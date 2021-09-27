package com.flightapp.admin.Service;

import org.springframework.stereotype.Service;

import com.flightapp.admin.DAO.FlightAvailability;
import com.flightapp.admin.Exception.SeatNotAvailableException;
import com.flightapp.admin.Interface.FlightAvailabilityRepository;

@Service
public class FlightSeatAvailability {

	private final FlightAvailabilityRepository availabilityRepository;

	public FlightSeatAvailability(FlightAvailabilityRepository availabilityRepository) {
		this.availabilityRepository = availabilityRepository;
	}

	public FlightAvailability saveRecord(FlightAvailability availability) throws SeatNotAvailableException {
		FlightAvailability record = availabilityRepository.findByFlightNumberAndFromPlaceAndToPlaceAndJourneyDate(
				availability.getFlightNumber(), availability.getFromPlace(), availability.getToPlace(),
				availability.getJourneyDate());
		if (record == null) {
			if (availability.getNosOfBusinessClassSeats() >= availability.getNosOfBookedBusinessClassSeats()
					&& availability.getNosOfNonBusinessClassSeats() >= availability
							.getNosOfBookedNonBusinessClassSeats())
				return availabilityRepository.save(availability);
			else
				throw new SeatNotAvailableException("Only less seats are available to book!");
		} else {

			int numberOfBusinessClass = record.getNosOfBookedBusinessClassSeats()
					+ availability.getNosOfBookedBusinessClassSeats();
			int numberOfNonBusinessClass = record.getNosOfBookedNonBusinessClassSeats()
					+ availability.getNosOfBookedNonBusinessClassSeats();
			if (record.getNosOfBusinessClassSeats() >= numberOfBusinessClass) {
				if (record.getNosOfNonBusinessClassSeats() >= numberOfNonBusinessClass) {
					record.setNosOfBookedBusinessClassSeats(numberOfBusinessClass);
					record.setNosOfBookedNonBusinessClassSeats(numberOfNonBusinessClass);
				} else
					throw new SeatNotAvailableException("No Non-Business Class Seats Available");
			} else
				throw new SeatNotAvailableException("No Business Class Seats Available");

			return availabilityRepository.save(record);
		}
	}

	public FlightAvailability getRecord(FlightAvailability availability) {
		return availabilityRepository.findByFlightNumberAndFromPlaceAndToPlaceAndJourneyDate(
				availability.getFlightNumber(), availability.getFromPlace(), availability.getToPlace(),
				availability.getJourneyDate());
	}
}
