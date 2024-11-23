package com.boxoffice.ticketmanager.exceptions;

public class MovieNotFoundException extends RuntimeException{
	public MovieNotFoundException() {
		super("Movie Not Found.");
	}

	public MovieNotFoundException(String message) {
		super(message);
	}
}
