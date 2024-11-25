package com.boxoffice.ticketmanager.services;

import com.boxoffice.ticketmanager.dtos.MovieDTO;
import com.boxoffice.ticketmanager.entity.Movie.Genre;
import com.boxoffice.ticketmanager.entity.Movie.IndicativeRating;
import com.boxoffice.ticketmanager.entity.Movie.Movie;
import com.boxoffice.ticketmanager.exceptions.movieException.InvalidMovieGenreException;
import com.boxoffice.ticketmanager.exceptions.movieException.MovieNotFoundException;
import com.boxoffice.ticketmanager.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public Movie findMovieById(Long id) {
        return this.repository.findMovieById(id).orElseThrow(MovieNotFoundException::new);
    }

    public Movie createMovie(MovieDTO data) {

        validateMovieGenre(data.genre());
        validateIndicativeRating(data.indicativeRating());

        Movie newMovie = new Movie(data);
        this.saveMovie(newMovie);
        return newMovie;
    }

    public List<Movie> getAllMovies() {
        return this.repository.findAll();
    }

    public void saveMovie(Movie movie) {
        this.repository.save(movie);
    }

    public void delete(Long id) {
        findMovieById(id);
        repository.deleteById(id);
    }

    private void validateMovieGenre(Genre genre) {
        if (genre == null || !EnumSet.allOf(Genre.class).contains(genre)) {
            throw new InvalidMovieGenreException();
        }
    }

    private void validateIndicativeRating(IndicativeRating indicativeRating) {
        if (indicativeRating == null || !EnumSet.allOf(IndicativeRating.class).contains(indicativeRating)) {
            throw new InvalidMovieGenreException();
        }
    }
}
