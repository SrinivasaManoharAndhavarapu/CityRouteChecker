package com.mastercard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class RouteCheckerService {
	
	public int index;
	public List<String> linkedCities = new ArrayList<String>();
	
	/**
	 * To Check Route available between Origin & Destination City
	 * 
	 * @param fromCity -> Origin City
	 * @param toCity -> Destination City
	 * @return
	 * @throws IOException
	 */
	public boolean cityRouteChecker(String fromCity, String toCity) throws IOException{

		boolean routeValid = false;
		// Adding Origin City to list
		linkedCities.add(fromCity);
		index = 0;
		try {
		//Finding all the reachable cities to Origin and also checking whether Destination City is there in that list
		while(linkedCities.size() > index) {
	       		findAndAddNeighbourCities(linkedCities.get(index));
	    		routeValid = checkCity(toCity);
	    		if(routeValid) break;
	    		index++;
	    	}
		}
		finally{
			linkedCities.clear();
		}
	    	System.out.println("cityRouteChecker::: " + routeValid);
	    	return routeValid;
		
	}

	/**
	 * To find the all the cities reachable from Origin
	 * 
	 * @param cityToCheck
	 * @throws IOException
	 */
	public void findAndAddNeighbourCities(String cityToCheck) throws IOException{
		File file = null;
		Stream < String > lines = null;
		file = ResourceUtils.getFile("classpath:city.txt");
    	try {
        lines = Files.lines( Paths.get( file.getPath() ) );
    	lines.forEach(
		        		line -> {
		        			String cityMap[] = line.split( "," );
		        			
				        		if((null != cityMap[0] && null != cityMap[1])){ 
				        		    if(cityMap[0].equalsIgnoreCase(cityToCheck))
				        		    	checkAndAddCity(cityMap[1]);
				        			     
				        			if(cityMap[1].equalsIgnoreCase(cityToCheck))
				        				checkAndAddCity(cityMap[0]);
				        		}
		        		});
    	}finally{
	    	file = null;
	    	lines.close();
	    }
	}
	
	/**
	 * To check and add the City only if is not already present in the reachable City list from Origin
	 * 
	 * @param cityToAdd
	 */
	public void checkAndAddCity(String cityToAdd){
		if(!checkCity(cityToAdd)) {
			linkedCities.add(cityToAdd);
		}
	}
	
	/**
	 * To check whether the City is in the reachable cities list from Origin
	 * 
	 * @param cityToVerify
	 * @return
	 */
	public boolean checkCity(String cityToVerify) {
		if(null == cityToVerify)
			return false;
		return linkedCities.contains(cityToVerify);
	}

}
