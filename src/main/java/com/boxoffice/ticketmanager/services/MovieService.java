package com.boxoffice.ticketmanager.services;

import com.boxoffice.ticketmanager.dtos.MovieDTO;
import com.boxoffice.ticketmanager.entity.Movie.Movie;
import com.boxoffice.ticketmanager.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public Movie findMovieById(Long id) throws Exception {
        return this.repository.findMovieById(id).orElseThrow(() -> new Exception("Movie not found"));
    }

    public Movie createMovie(MovieDTO data) {
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

    public void delete(Long id) throws Exception {
        findMovieById(id);
        repository.deleteById(id);
    }
}
