package com.rvprogramming.springbootmapsdemo.web;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rvprogramming.springbootmapsdemo.model.OpenWeatherData;
import com.rvprogramming.springbootmapsdemo.model.OpenWeatherInfo;
import com.rvprogramming.springbootmapsdemo.model.OpenWeatherResponse;
import com.rvprogramming.springbootmapsdemo.model.UserCoords;

@Controller
public class SimpleController {
	private static final Logger LOGGER = LogManager.getLogger(SimpleController.class);

    @Value("${spring.application.name}")
    String appName;
    
	@Value("${spring.positionstack.apikey}")
	String mapsKey;
	
    @RequestMapping("/")
    public String homePage(Model model) {
		LOGGER.trace("Starting homePage, adding appName: '" + String.valueOf(appName) + "'...");
        model.addAttribute("appName", appName);
        model.addAttribute("userCoords", new UserCoords());
        return "home";
    }
    
    @PostMapping("/submit")
    public String formSubmit(@ModelAttribute UserCoords userCoords, Model model) {
    	LOGGER.trace("Submitting form, userCoords: '" + String.valueOf(userCoords) + "' in model: '" + String.valueOf(model) + "'...");
    	model.addAttribute("appName", appName);
        model.addAttribute("userCoords", userCoords);
        model.addAttribute("mapsKey", mapsKey);
        String loclatlon = String.valueOf(userCoords.getLatitude()) + "," + String.valueOf(userCoords.getLongitude());
        model.addAttribute("loclatlon", loclatlon);
        String mapsuri = "https://www.google.com/maps/embed/v1/place?key=" + String.valueOf(mapsKey)+ "&q=" + loclatlon;
        LOGGER.trace("Adding mapsuri to model: '" + String.valueOf(mapsuri) + "'...");
        model.addAttribute("mapsuri", mapsuri);
        OpenWeatherResponse weatherResponse = userCoords.getWeatherResponse();
        if (weatherResponse != null && weatherResponse.hourly != null && weatherResponse.hourly.length > 0) {
        	OpenWeatherInfo[] hourly = weatherResponse.hourly;
        	LOGGER.trace("Converting hourly weather from OpenWeatherInfo to OpenWeatherData type for grid:");
        	OpenWeatherData[] data = Arrays.stream(hourly).map(i -> {
        		OpenWeatherData d = new OpenWeatherData();
        		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss Z");
        		int tzo = weatherResponse.timezone_offset;
        		d.dt = Instant.ofEpochSecond(i.dt).atOffset(ZoneOffset.ofTotalSeconds(tzo)).format(df);
        		d.sunrise = Instant.ofEpochSecond(i.sunrise).atOffset(ZoneOffset.ofTotalSeconds(tzo)).format(df);
        		d.sunset = Instant.ofEpochSecond(i.sunset).atOffset(ZoneOffset.ofTotalSeconds(tzo)).format(df);
        		d.temp = i.temp;
        		d.feels_like = i.feels_like;
        		d.pressure = i.pressure;
        		d.humidity = i.humidity;
        		d.dew_point = i.dew_point;
        		d.uvi = i.uvi;
        		d.clouds = i.clouds;
        		d.visibility = i.visibility;
        		d.wind_speed = i.wind_speed;
        		d.wind_deg = i.wind_deg;
        		d.wind_gust = i.wind_gust;
        		d.pop = i.pop;
        		d.weather = i.weather;
        		return d;
    		}).toArray(OpenWeatherData[]::new);
        	LOGGER.trace("Adding data to model: '" + String.valueOf(data) + "'...");
        	model.addAttribute("data", data);
        }
        return "result";
    }
    
}