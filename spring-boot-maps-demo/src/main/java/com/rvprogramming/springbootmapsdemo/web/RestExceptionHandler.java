package com.rvprogramming.springbootmapsdemo.web;
import com.rvprogramming.springbootmapsdemo.web.exception.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOGGER = LogManager.getLogger(RestExceptionHandler.class);

    public RestExceptionHandler() {
        super();
    }

    @ExceptionHandler(CityNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
    	LOGGER.trace("Handling NOT FOUND error: ", ex);
        return handleExceptionInternal(ex, "City not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({
      Exception.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
    	LOGGER.trace("Handling BAD REQUEST error: ", ex);
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
}