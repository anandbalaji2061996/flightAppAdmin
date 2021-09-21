package com.flightapp.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@SpringBootTest
public class AdminControllerTest {

	@Autowired
	private WebApplicationContext applicationContext;
	
	private MockMvc mockMvc;
		
	@BeforeEach
	public void setup() throws Exception{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
	}
	
	@Test
	public void adminLogin() throws Exception {
 
		String s1 = "{\"username\":\"admin\",\"password\":\"admin\"}";
		mockMvc.perform(post("/admin/api/v1.0/flight/login").contentType(MediaType.APPLICATION_JSON).content(s1))
		.andExpect(status().isOk()).andReturn();

	}
	
}
