package com.boxoffice.ticketmanager.repositories;

import com.boxoffice.ticketmanager.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {

    Optional<Film> findFilmById(Long id);
}
