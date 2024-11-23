package com.boxoffice.ticketmanager.repositories;

import com.boxoffice.ticketmanager.entity.Movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findMovieById(Long id);
}
