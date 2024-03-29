package com.flightapp.admin.DAO;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "airlineDetails")
public class Airline {

	@Id
	private String name;
	@NotEmpty(message = "Address should not be empty")
	private String address;
	@NotEmpty(message = "Contact number should not be empty")
	private String contactNumber;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="airlineName")
	private List<FlightDetails> flightDetails;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public List<FlightDetails> getFlightDetails() {
		return flightDetails;
	}
	public void setFlightDetails(List<FlightDetails> flightDetails) {
		this.flightDetails = flightDetails;
	}
	
}
