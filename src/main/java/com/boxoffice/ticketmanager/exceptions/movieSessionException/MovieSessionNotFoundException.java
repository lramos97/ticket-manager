package com.boxoffice.ticketmanager.exceptions.movieSessionException;

public class MovieSessionNotFoundException extends RuntimeException{
	public MovieSessionNotFoundException() {
		super("Movie Session Not Found.");
	}

	public MovieSessionNotFoundException(String message) {
		super(message);
	}
}
