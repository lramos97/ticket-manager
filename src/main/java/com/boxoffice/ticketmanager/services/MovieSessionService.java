package com.boxoffice.ticketmanager.services;

import com.boxoffice.ticketmanager.dtos.MovieSessionDTO;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.repositories.MovieSessionRepository;
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
}
