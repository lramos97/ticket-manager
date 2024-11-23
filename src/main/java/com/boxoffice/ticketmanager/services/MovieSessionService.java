package com.boxoffice.ticketmanager.services;

import com.boxoffice.ticketmanager.dtos.MovieSessionDTO;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.exceptions.MovieSessionNotFoundException;
import com.boxoffice.ticketmanager.exceptions.NoSeatsAvailableException;
import com.boxoffice.ticketmanager.repositories.MovieSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieSessionService {

    @Autowired
    private MovieSessionRepository repository;

    public MovieSession findSessionById(Long id) {
        return this.repository.findSessionById(id).orElseThrow(MovieSessionNotFoundException::new);
    }

    public MovieSession createSession(MovieSessionDTO data) {
        MovieSession newSession = new MovieSession(data);
        this.save(newSession);
        return newSession;
    }

    public List<MovieSession> getAllSessions() {
        return this.repository.findAll();
    }

    public void save(MovieSession session) {
        this.repository.save(session);
    }

    public void delete(Long id) {
        findSessionById(id);
        repository.deleteById(id);
    }
    public void reserveSeat(Long sessionId) {
        MovieSession session = repository.findById(sessionId).orElseThrow(MovieSessionNotFoundException::new);
		if (session.getAvailableSeats() <= 0)
			throw new NoSeatsAvailableException();
		session.setAvailableSeats(session.getAvailableSeats() - 1);
		repository.save(session);
    }
}
