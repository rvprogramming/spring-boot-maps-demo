package com.rvprogramming.springbootmapsdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherWeather {
	public int id;
	public String main;
	public String description;
	public String icon;
}
