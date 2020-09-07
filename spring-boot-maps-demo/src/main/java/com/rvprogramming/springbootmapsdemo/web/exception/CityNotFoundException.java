package com.rvprogramming.springbootmapsdemo.web.exception;

public class CityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 793158457101006338L;
	
    public CityNotFoundException() {
        super();
    }

    public CityNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CityNotFoundException(final String message) {
        super(message);
    }

    public CityNotFoundException(final Throwable cause) {
        super(cause);
    }

}
