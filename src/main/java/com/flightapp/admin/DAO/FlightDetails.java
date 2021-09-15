package com.flightapp.admin.DAO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flightDetails")
public class FlightDetails {

	@Id
	private String flightNumber;
	private String airline;
	private String fromPlace;
	private String toPlace;
	private String startDateTime;
	private String endDateTime;
	private String scheduledDays;
	private String nosOfBusinessClassSeats;
	private String nosOfNonBusinessClassSeats;
	private String ticketCost;
	private String nosOfRows;
	private String meals;
	
	
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
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getScheduledDays() {
		return scheduledDays;
	}
	public void setScheduledDays(String scheduledDays) {
		this.scheduledDays = scheduledDays;
	}
	public String getNosOfBusinessClassSeats() {
		return nosOfBusinessClassSeats;
	}
	public void setNosOfBusinessClassSeats(String nosOfBusinessClassSeats) {
		this.nosOfBusinessClassSeats = nosOfBusinessClassSeats;
	}
	public String getNosOfNonBusinessClassSeats() {
		return nosOfNonBusinessClassSeats;
	}
	public void setNosOfNonBusinessClassSeats(String nosOfNonBusinessClassSeats) {
		this.nosOfNonBusinessClassSeats = nosOfNonBusinessClassSeats;
	}
	public String getTicketCost() {
		return ticketCost;
	}
	public void setTicketCost(String ticketCost) {
		this.ticketCost = ticketCost;
	}
	public String getNosOfRows() {
		return nosOfRows;
	}
	public void setNosOfRows(String nosOfRows) {
		this.nosOfRows = nosOfRows;
	}
	public String getMeals() {
		return meals;
	}
	public void setMeals(String meals) {
		this.meals = meals;
	}
	
}
