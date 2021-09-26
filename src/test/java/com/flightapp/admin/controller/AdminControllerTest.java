package com.flightapp.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.flightapp.admin.Service.FlightDetailService;

@WebAppConfiguration
@SpringBootTest
public class AdminControllerTest {

	@Autowired
	private WebApplicationContext applicationContext;
	
	private MockMvc mockMvc;
	
	@Mock
	FlightDetailService service;
		
	@BeforeEach
	public void setup() throws Exception{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
	}
	
	@Test
	public void adminLogin() throws Exception {
 
		service.username = "admin";
		service.password = "admin";
		String s1 = "{\"username\":\"admin\",\"password\":\"admin\"}";
		mockMvc.perform(post("/api1/v1.0/admin/flight/login").contentType(MediaType.APPLICATION_JSON).content(s1))
		.andExpect(status().isNotFound()).andReturn();

	}
	
}
