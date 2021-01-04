package com.mastercard;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteCheckerController {		

	public static final String YES = "yes"; 
	public static final String NO = "no";
	public static int index;
	public static List<String> linkedCities;
	
	@Autowired	
	public RouteCheckerService routeCheckerService;
		
	@RequestMapping(value = "/connected",method = RequestMethod.GET)
	public String routeChecker(@RequestParam("origin") String fromCity, @RequestParam("destination") String toCity) throws IOException{
		
		System.out.println("From City: "+fromCity + " To City:"+toCity);
		if(routeCheckerService.cityRouteChecker(fromCity, toCity))
			return YES;
		else
			return NO;		
	}
}

