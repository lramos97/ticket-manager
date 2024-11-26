package com.boxoffice.ticketmanager.controllers;

import com.boxoffice.ticketmanager.dtos.MovieSessionDTO;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.services.MovieSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class MovieSessionController {

    @Autowired
    private MovieSessionService movieSessionService;

    @PostMapping
    public ResponseEntity<MovieSessionDTO> createSession(@RequestBody MovieSessionDTO session) {
        MovieSession newSession = movieSessionService.createSession(session);
        return new ResponseEntity<>(new MovieSessionDTO(
	        newSession.getMovie(),
	        newSession.getStartTime(),
	        newSession.getAvailableSeats()
        ), HttpStatus.CREATED);
    }

	@GetMapping
	public ResponseEntity<List<MovieSessionDTO>> getAllSessions() {
		List<MovieSessionDTO> sessionDTOs = movieSessionService.getAllSessions()
			.stream()
			.map(session -> new MovieSessionDTO(
				session.getMovie(),
				session.getStartTime(),
				session.getAvailableSeats()))
			.toList();

		return ResponseEntity.ok(sessionDTOs);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MovieSessionDTO> getSessionById(@PathVariable Long id) {
		MovieSession session = movieSessionService.findSessionById(id);
		MovieSessionDTO sessionDTO = new MovieSessionDTO(
			session.getMovie(),
			session.getStartTime(),
			session.getAvailableSeats()
		);
		return ResponseEntity.ok(sessionDTO);
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieSessionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
