package com.rvprogramming.springbootmapsdemo.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class OpenWeatherData {
	public String dt; // from unix ts
	public String sunrise; // from unix ts
	public String sunset; // from unix ts
	public double temp;
	public double feels_like;
	public Number pressure;
	public Number humidity;
	public Number dew_point;
	public Number uvi;
	public Number clouds;
	public Number visibility;
	public Number wind_speed;
	public Number wind_deg;
	public Number wind_gust;
	public Number pop; // probability of precipitation
	public OpenWeatherWeather[] weather;
}
