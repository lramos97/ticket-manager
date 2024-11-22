package com.boxoffice.ticketmanager.services;

import com.boxoffice.ticketmanager.dtos.MovieSessionDTO;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.repositories.MovieSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieSessionService {

    @Autowired
    private MovieSessionRepository repository;

    public MovieSession findSessionById(Long id) throws Exception {
        return this.repository.findSessionById(id).orElseThrow(() -> new Exception("Session not found"));
    }

    public MovieSession createSession(MovieSessionDTO data) {
        MovieSession newSession = new MovieSession(data);
        this.saveSession(newSession);
        return newSession;
    }

    public List<MovieSession> getAllSessions() {
        return this.repository.findAll();
    }

    public void saveSession(MovieSession session) {
        this.repository.save(session);
    }

    public void delete(Long id) throws Exception {
        findSessionById(id);
        repository.deleteById(id);
    }
    public void updateSession(Long sessionId) {
        MovieSession session = repository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));
        if (session.getAvailableSeats() > 0) {
            session.setAvailableSeats(session.getAvailableSeats() - 1);
            repository.save(session);
        } else {
            throw new IllegalStateException("No seats available for this session");
        }
    }
}
