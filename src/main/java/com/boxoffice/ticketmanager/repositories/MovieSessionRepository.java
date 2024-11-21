package com.boxoffice.ticketmanager.repositories;

import com.boxoffice.ticketmanager.entity.Film;
import com.boxoffice.ticketmanager.entity.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {

    Optional<MovieSession> findSessionById(Long id);
}
