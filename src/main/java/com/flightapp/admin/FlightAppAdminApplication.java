package com.flightapp.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.flightapp.admin.DAO")
@EnableJpaRepositories(basePackages = "com.flightapp.admin.Interface")
@EnableEurekaClient
public class FlightAppAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightAppAdminApplication.class, args);
	}

}
