 package com.rvprogramming.springbootmapsdemo.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.rvprogramming.springbootmapsdemo.client.OpenWeatherRestService;
import com.rvprogramming.springbootmapsdemo.client.PositionStackRestService;
import com.rvprogramming.springbootmapsdemo.web.exception.CityNotFoundException;

@Component
@PropertySource("classpath:application.properties")
public class UserCoords {
	private static final Logger LOGGER = LogManager.getLogger(UserCoords.class);
	
	private String placeName;
	private double latitude;
	private double longitude;
	private OpenWeatherResponse weatherResponse;
	
	private static final Pattern LAT_LON_PATTERN = Pattern.compile("(^[-+]?(?:[1-8]?\\d(?:\\.\\d+)?|90(?:\\.0+)?)),\\s*([-+]?(?:180(?:\\.0+)?|(?:(?:1[0-7]\\d)|(?:[1-9]?\\d))(?:\\.\\d+)?))$");
	
	public UserCoords() {
	}
	
	public String getPlaceName() {
		return this.placeName;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public OpenWeatherResponse getWeatherResponse() {
		return this.weatherResponse;
	}
	
	public void setPlaceName(String placeName) {
		LOGGER.trace("Setting placeName: '" + String.valueOf(placeName) + "'...");
		this.placeName = placeName;
		
		// Get lat/lon:
		Matcher matcher = LAT_LON_PATTERN.matcher(this.placeName);
		if (matcher.find()) {
			LOGGER.debug("Found coords from placeName, adding lat/lon...");
			try {
				double lat = Double.parseDouble(matcher.group(1));
				double lon = Double.parseDouble(matcher.group(2));
				this.setLatitude(lat);
				this.setLongitude(lon);
			} catch (Exception ex) {
				LOGGER.error("Error parsing lat/lon from placeName: '" + String.valueOf(placeName) + "'!", ex);
				throw ex; // raise for /errors page
			}
		} else {
			LOGGER.debug("No coords specified, checking rest API...");
			try {
				PositionStackResponse response = new PositionStackRestService().getPositionStackResponse(placeName);
				if (response.error != null) {
					throw new CityNotFoundException("Cannot find '" + String.valueOf(placeName) + "', api code: '" + response.error.code + "', message: '" + response.error.message + "'!");
				}
				if (response.data != null) {
					double lat = response.data.get(0).latitude;
					double lon = response.data.get(0).longitude;
					this.setLatitude(lat);
					this.setLongitude(lon);
				}
			} catch (Exception ex) {
				LOGGER.error("Error parsing lat/lon from placeName: '" + String.valueOf(placeName) + "'!", ex);
				throw ex; // raise for /errors page
			}
		}
		
		// Get weather info:
		try {
			OpenWeatherResponse response = new OpenWeatherRestService().getOpenWeatherResponse(this.latitude, this.longitude, "minutely,daily");
			LOGGER.debug("Got back response: '" + String.valueOf(response) + "'.");
			this.setWeatherResponse(response);
		} catch(Exception ex) {
			LOGGER.error("Error getting weather info for lat/lon: '" + String.valueOf(this.latitude) + "/" + String.valueOf(this.longitude) + "'!", ex);
			throw ex;
		}
	}

	public void setLatitude(double latitude) {
		LOGGER.trace("Setting latitude: '" + String.valueOf(latitude) + "'...");
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		LOGGER.trace("Setting longitude: '" + String.valueOf(longitude) + "'...");
		this.longitude = longitude;
	}
	
	public void setWeatherResponse(OpenWeatherResponse weatherResponse) {
		LOGGER.trace("Setting weatherResponse: '" + String.valueOf(weatherResponse) + "'...");
		this.weatherResponse = weatherResponse;
	}
	
}
