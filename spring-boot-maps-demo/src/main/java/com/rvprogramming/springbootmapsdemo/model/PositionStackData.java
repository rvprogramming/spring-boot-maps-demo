package com.rvprogramming.springbootmapsdemo.model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionStackData {
	public double latitude;
	public double longitude;
	
	@JsonCreator
	public PositionStackData(
		@JsonProperty("latitude") double latitude,
		@JsonProperty("longitude") double longitude
	) {
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
		
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "PositionStackData{latitude=" + String.valueOf(this.latitude) + ", longitude=" + String.valueOf(this.longitude) + "}";
	}
}
