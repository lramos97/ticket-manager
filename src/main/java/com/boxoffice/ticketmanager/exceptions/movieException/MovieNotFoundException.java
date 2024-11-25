package com.boxoffice.ticketmanager.exceptions.movieException;

public class MovieNotFoundException extends RuntimeException{
	public MovieNotFoundException() {
		super("Movie Not Found.");
	}

	public MovieNotFoundException(String message) {
		super(message);
	}
}
