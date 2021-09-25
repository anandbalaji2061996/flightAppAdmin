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
	private int nosOfBusinessClassSeats;
	private int nosOfNonBusinessClassSeats;
	private int ticketCost;
	private int nosOfRows;
	private String meals;
	private String discountCode;
	private int discount;
	
	
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
	public int getTicketCost() {
		return ticketCost;
	}
	public void setTicketCost(int ticketCost) {
		this.ticketCost = ticketCost;
	}
	public int getNosOfRows() {
		return nosOfRows;
	}
	public void setNosOfRows(int nosOfRows) {
		this.nosOfRows = nosOfRows;
	}
	public String getMeals() {
		return meals;
	}
	public void setMeals(String meals) {
		this.meals = meals;
	}
	public String getDiscountCode() {
		return discountCode;
	}
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
}
