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
    public ResponseEntity<MovieSession> createSession(@RequestBody MovieSessionDTO session){
        MovieSession newSession = movieSessionService.createSession(session);
        return new ResponseEntity<>(newSession, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MovieSession>> getAllSessions() {
        List<MovieSession> sessions = this.movieSessionService.getAllSessions();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        movieSessionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
