package com.rvprogramming.springbootmapsdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {
	// Error response:
	public int cod;
	public String message;
	
	// Success response:
	public double lat;
	public double lon;
	public String timezone;
	public int timezone_offset;
	public OpenWeatherInfo current;
	public OpenWeatherInfo[] daily;
	public OpenWeatherInfo[] hourly;
	public OpenWeatherInfo[] minutely;
}
