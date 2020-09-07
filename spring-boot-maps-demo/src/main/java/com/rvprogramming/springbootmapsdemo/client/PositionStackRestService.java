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
import com.rvprogramming.springbootmapsdemo.model.PositionStackError;
import com.rvprogramming.springbootmapsdemo.model.PositionStackResponse;

@Component
@PropertySource("classpath:application.properties")
public class PositionStackRestService {
	private static final Logger LOGGER = LogManager.getLogger(PositionStackRestService.class);
	
	
	private static String API_KEY;
	
	@Value("${spring.positionstack.apikey}")
	public void setApiKey(String API_KEY) {
		PositionStackRestService.API_KEY = API_KEY;
	}
	
	public PositionStackResponse getPositionStackResponse(String query) {
		LOGGER.trace("Entering getPositionStackResponse for query: '" + String.valueOf(query) + "'...");
		String url = "http://api.positionstack.com/v1/forward";
		URI uri = UriComponentsBuilder.fromUriString(url)
				.queryParam("access_key", API_KEY)
				.queryParam("query", query)
				.queryParam("limit", 1)
				.build()
				.toUri();
		/* TRY POST
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject json = new JSONObject()
			.put("access_key", API_KEY)
			.put("query", query)
			.put("limit", 1);
		HttpEntity<String> request = new HttpEntity<String>(json.toString(), headers);*/
		LOGGER.debug("Built uri: '" + String.valueOf(uri) + "'.");
		RestTemplate restTemplate = new RestTemplate();
		String json = restTemplate.getForObject(uri, String.class);
		LOGGER.debug("Unmarshalling json response: '" + String.valueOf(json) + "'.");
		ObjectMapper mapper = new ObjectMapper();
		PositionStackResponse response = new PositionStackResponse();
		try {
			response = mapper.readValue(json, PositionStackResponse.class);
		} catch (Exception ex) {
			LOGGER.error("Error unmarshalling JSON: '" + String.valueOf(json) + "'!", ex);
			response.error = new PositionStackError();
			response.error.code = ex.getClass().getSimpleName();
			response.error.message = ex.getMessage();
		}
		LOGGER.debug("Returning response: '" + String.valueOf(response) + "'...");
		return response;
	}
	
}


