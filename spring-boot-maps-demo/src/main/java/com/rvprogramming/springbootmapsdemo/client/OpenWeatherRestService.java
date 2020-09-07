package com.rvprogramming.springbootmapsdemo.client;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvprogramming.springbootmapsdemo.model.OpenWeatherResponse;

@Component
@PropertySource("classpath:application.properties")
public class OpenWeatherRestService {
	private static final Logger LOGGER = LogManager.getLogger(OpenWeatherRestService.class);
	
	
	private static String API_KEY;
	
	@Value("${spring.openweathermap.apikey}")
	public void setApiKey(String API_KEY) {
		OpenWeatherRestService.API_KEY = API_KEY;
	}
	
	public OpenWeatherResponse getOpenWeatherResponse(double lat, double lon, String exclude) {
		LOGGER.trace("Entering getOpenWeatherResponse for lat: '" + String.valueOf(lat) + "', lon: '" + String.valueOf(lon) + "', exclude: '" + String.valueOf(exclude) + "'...");
		String url = "https://api.openweathermap.org/data/2.5/onecall";
		URI uri = UriComponentsBuilder.fromUriString(url)
				.queryParam("lat", lat)
				.queryParam("lon", lon)
				.queryParam("exclude", exclude)
				.queryParam("appid", API_KEY)
				.build()
				.toUri();
		/* TRY POST
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject json = new JSONObject()
		...
		HttpEntity<String> request = new HttpEntity<String>(json.toString(), headers);*/
		LOGGER.debug("Built uri: '" + String.valueOf(uri) + "'.");
		RestTemplate restTemplate = new RestTemplate();
		String json = restTemplate.getForObject(uri, String.class);
		LOGGER.debug("Unmarshalling json response: '" + String.valueOf(json) + "'.");
		ObjectMapper mapper = new ObjectMapper();
		OpenWeatherResponse response = new OpenWeatherResponse();
		try {
			response = mapper.readValue(json, OpenWeatherResponse.class);
		} catch (Exception ex) {
			LOGGER.error("Error unmarshalling JSON: '" + String.valueOf(json) + "'!", ex);
			response.cod = -1;
			response.message = ex.getClass().getSimpleName() + ": " + ex.getMessage();
		}
		LOGGER.debug("Returning response: '" + String.valueOf(response) + "'...");
		return response;
	}
	
}


