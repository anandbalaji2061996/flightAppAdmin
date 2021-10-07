package com.flightapp.admin.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.admin.DAO.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, String> {

}
