package com.boxoffice.ticketmanager.exceptions;

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

	@ExceptionHandler(MovieSessionNotFoundException.class)
	private ResponseEntity<String> movieSessionNotFound(MovieSessionNotFoundException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie Session Not Found.");
	}

	@ExceptionHandler(NoSeatsAvailableException.class)
	private ResponseEntity<String> noSeatsAvailableException(NoSeatsAvailableException exception){
		return ResponseEntity.status(HttpStatus.CONFLICT).body("No Seats Available.");
	}
}
