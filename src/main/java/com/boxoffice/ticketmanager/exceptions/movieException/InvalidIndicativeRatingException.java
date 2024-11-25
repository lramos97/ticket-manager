package com.boxoffice.ticketmanager.exceptions.movieException;

public class InvalidIndicativeRatingException extends RuntimeException {

    public InvalidIndicativeRatingException() {

        super("Invalid Indicative Rating.");
    }

    public InvalidIndicativeRatingException(String message) {

        super(message);
    }
}
