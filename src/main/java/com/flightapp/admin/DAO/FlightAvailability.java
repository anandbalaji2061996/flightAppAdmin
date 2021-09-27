package com.flightapp.admin.DAO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="flightScheduler")
public class FlightAvailability {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String flightNumber;
	private String airline;
	private String fromPlace;
	private String toPlace;
	private String journeyDate;
	private int nosOfBusinessClassSeats;
	private int nosOfNonBusinessClassSeats;
	private int nosOfBookedBusinessClassSeats;
	private int nosOfBookedNonBusinessClassSeats;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getFromPlace() {
		return fromPlace;
	}
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}
	public String getToPlace() {
		return toPlace;
	}
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	public String getJourneyDate() {
		return journeyDate;
	}
	public void setJourneyDate(String journeyDate) {
		this.journeyDate = journeyDate;
	}
	public int getNosOfBusinessClassSeats() {
		return nosOfBusinessClassSeats;
	}
	public void setNosOfBusinessClassSeats(int nosOfBusinessClassSeats) {
		this.nosOfBusinessClassSeats = nosOfBusinessClassSeats;
	}
	public int getNosOfNonBusinessClassSeats() {
		return nosOfNonBusinessClassSeats;
	}
	public void setNosOfNonBusinessClassSeats(int nosOfNonBusinessClassSeats) {
		this.nosOfNonBusinessClassSeats = nosOfNonBusinessClassSeats;
	}
	public int getNosOfBookedBusinessClassSeats() {
		return nosOfBookedBusinessClassSeats;
	}
	public void setNosOfBookedBusinessClassSeats(int nosOfBookedBusinessClassSeats) {
		this.nosOfBookedBusinessClassSeats = nosOfBookedBusinessClassSeats;
	}
	public int getNosOfBookedNonBusinessClassSeats() {
		return nosOfBookedNonBusinessClassSeats;
	}
	public void setNosOfBookedNonBusinessClassSeats(int nosOfBookedNonBusinessClassSeats) {
		this.nosOfBookedNonBusinessClassSeats = nosOfBookedNonBusinessClassSeats;
	}
}
