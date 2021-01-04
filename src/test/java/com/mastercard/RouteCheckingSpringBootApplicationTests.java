package com.mastercard;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class RouteCheckingSpringBootApplicationTests {

	@Autowired
	public RouteCheckerController controllerObj;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RouteCheckerService service;
	
	@Test
	void contextLoads() {
		Assertions.assertNotNull(controllerObj);
	}
	
	@Test
	void callAPIForCheckCityRouteForYesCase() throws Exception{
		when(service.cityRouteChecker("Boston","Philadelphia")).thenReturn(true);
		
		// Yes case
		this.mockMvc.perform(get("http://localhost:8080/connected?origin=Boston&destination=Philadelphia")).andExpect(status().isOk())
		.andExpect(content().string(containsString("yes")));
		
	}
	
	@Test
	void callAPIForCheckCityRouteForNoCase() throws Exception{
		// No case
		when(service.cityRouteChecker("Boston","Virginia")).thenReturn(false);
		
	
	
			this.mockMvc.perform(get("http://localhost:8080/connected?origin=Boston&destination=Virginia")).andExpect(status().isOk())
			.andExpect(content().string(containsString("no")));
	}	
}
