package com.boxoffice.ticketmanager.exceptions.movieException;

public class InvalidMovieGenreException extends RuntimeException {

    public InvalidMovieGenreException() {
        super("Invalid Movie Genre");

    }
    public InvalidMovieGenreException(String message) {
        super(message);
    }
}
