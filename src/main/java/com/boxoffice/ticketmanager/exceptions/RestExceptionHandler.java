package com.boxoffice.ticketmanager.exceptions;

import com.boxoffice.ticketmanager.exceptions.movieException.InvalidIndicativeRatingException;
import com.boxoffice.ticketmanager.exceptions.movieException.InvalidMovieGenreException;
import com.boxoffice.ticketmanager.exceptions.movieException.MovieNotFoundException;
import com.boxoffice.ticketmanager.exceptions.movieSessionException.MovieSessionNotFoundException;
import com.boxoffice.ticketmanager.exceptions.movieSessionException.NoSeatsAvailableException;
import com.boxoffice.ticketmanager.exceptions.ticketException.InvalidTicketTypeException;
import com.boxoffice.ticketmanager.exceptions.ticketException.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(MovieNotFoundException.class)
	private ResponseEntity<String> movieNotFound(MovieNotFoundException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie Not Found.");
	}

	@ExceptionHandler(InvalidMovieGenreException.class)
	private ResponseEntity<String> invalidMovieGenre(InvalidMovieGenreException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Movie Genre.");
	}

	@ExceptionHandler(InvalidIndicativeRatingException.class)
	private ResponseEntity<String> invalidIndicativeRating(InvalidIndicativeRatingException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Indicative Rating.");
	}

	@ExceptionHandler(MovieSessionNotFoundException.class)
	private ResponseEntity<String> movieSessionNotFound(MovieSessionNotFoundException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie Session Not Found.");
	}

	@ExceptionHandler(NoSeatsAvailableException.class)
	private ResponseEntity<String> noSeatsAvailableException(NoSeatsAvailableException exception){
		return ResponseEntity.status(HttpStatus.CONFLICT).body("No Seats Available.");
	}

	@ExceptionHandler(TicketNotFoundException.class)
	private ResponseEntity<String> ticketNotFound(TicketNotFoundException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket Not Found.");
	}

	@ExceptionHandler(InvalidTicketTypeException.class)
	private ResponseEntity<String> invalidTicketType(InvalidTicketTypeException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Ticket Type.");
	}
}
