package com.flightapp.admin.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.flightapp.admin.DAO.FlightAvailability;
import com.flightapp.admin.Exception.BadRequestException;
import com.flightapp.admin.Exception.SeatNotAvailableException;
import com.flightapp.admin.Interface.FlightAvailabilityRepository;

@Service
public class FlightSeatAvailability {
	
	private static final Logger logger = LoggerFactory.getLogger(FlightSeatAvailability.class);

	private final FlightAvailabilityRepository availabilityRepository;

	public FlightSeatAvailability(FlightAvailabilityRepository availabilityRepository) {
		this.availabilityRepository = availabilityRepository;
	}

	public FlightAvailability saveRecord(FlightAvailability availability) throws SeatNotAvailableException {
		FlightAvailability record = availabilityRepository.findByFlightNumberAndFromPlaceAndToPlaceAndJourneyDate(
				availability.getFlightNumber(), availability.getFromPlace(), availability.getToPlace(),
				availability.getJourneyDate());
		if (record == null) {
			logger.info("New Record in seat availability");
			if (availability.getNosOfBusinessClassSeats() >= availability.getNosOfBookedBusinessClassSeats()
					&& availability.getNosOfNonBusinessClassSeats() >= availability
							.getNosOfBookedNonBusinessClassSeats())
				return availabilityRepository.save(availability);
			else
				throw new SeatNotAvailableException("Only less seats are available to book!");
		} else {
			logger.info("Updating record in seat availability");
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

	public FlightAvailability updateSeatRecordAfterCancellation(FlightAvailability availability)
			throws BadRequestException {
		FlightAvailability record = availabilityRepository.findByFlightNumberAndFromPlaceAndToPlaceAndJourneyDate(
				availability.getFlightNumber(), availability.getFromPlace(), availability.getToPlace(),
				availability.getJourneyDate());

		if (record == null) {
			logger.info("No records found");
			throw new BadRequestException("No records found");
		} else {
			logger.info("Updation of seat availability after booking cancel");
			if (record.getNosOfBookedBusinessClassSeats() >= availability.getNosOfBookedBusinessClassSeats() && record
					.getNosOfBookedNonBusinessClassSeats() >= availability.getNosOfBookedNonBusinessClassSeats()) {
				int addnumberOfBusinessClass = record.getNosOfBookedBusinessClassSeats()
						- availability.getNosOfBookedBusinessClassSeats();
				int addnumberOfNonBusinessClass = record.getNosOfBookedNonBusinessClassSeats()
						- availability.getNosOfBookedNonBusinessClassSeats();
				record.setNosOfBookedBusinessClassSeats(addnumberOfBusinessClass);
				record.setNosOfBookedNonBusinessClassSeats(addnumberOfNonBusinessClass);
				return availabilityRepository.save(record);
			} else
				throw new BadRequestException("Invalid data!");
		}

	}

	public FlightAvailability getRecord(FlightAvailability availability) {
		logger.info("Get Record for seat availability");
		return availabilityRepository.findByFlightNumberAndFromPlaceAndToPlaceAndJourneyDate(
				availability.getFlightNumber(), availability.getFromPlace(), availability.getToPlace(),
				availability.getJourneyDate());
	}
}
