package com.boxoffice.ticketmanager.exceptions.movieSessionException;

public class NoSeatsAvailableException extends RuntimeException {

    public NoSeatsAvailableException() {

        super("No Seats Available.");
    }

    public NoSeatsAvailableException(String message) {

        super(message);
    }
}
