package com.boxoffice.ticketmanager.services;

import com.boxoffice.ticketmanager.dtos.MovieDTO;
import com.boxoffice.ticketmanager.entity.Movie.Genre;
import com.boxoffice.ticketmanager.entity.Movie.IndicativeRating;
import com.boxoffice.ticketmanager.entity.Movie.Movie;
import com.boxoffice.ticketmanager.exceptions.movieException.InvalidMovieGenreException;
import com.boxoffice.ticketmanager.exceptions.movieException.MovieNotFoundException;
import com.boxoffice.ticketmanager.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        MovieDTO movieDTO = new MovieDTO("Inception", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE);
        Movie movie = new Movie(movieDTO);
        movie.setId(1L);
    }

    @Test
    public void testFindMovieById_Success() {
        Long validId = 1L;
        Movie expectedMovie = new Movie();
        when(movieRepository.findMovieById(validId)).thenReturn(Optional.of(expectedMovie));

        Movie result = movieService.findMovieById(validId);

        assertEquals(expectedMovie, result);
        verify(movieRepository).findMovieById(validId);
    }

    @Test
    public void testFindMovieById_NotFound() {
        Long invalidId = 999L;
        when(movieRepository.findMovieById(invalidId)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.findMovieById(invalidId));
    }

    @Test
    void testCreateMovie() {
        MovieDTO validData = new MovieDTO("Inception", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE);
        Movie expectedMovie = new Movie(validData);

        doNothing().when(movieService).validateMovieGenre(validData.genre());
        doNothing().when(movieService).validateIndicativeRating(validData.indicativeRating());
        doNothing().when(movieRepository).save(any(Movie.class));

        Movie result = movieService.createMovie(validData);

        assertNotNull(result);
        assertEquals(expectedMovie.getTitle(), result.getTitle());
        verify(movieRepository).save(result);
    }

    @Test
    void shouldThrowExceptionWhenGenreIsInvalid() {
        MovieDTO invalidData = new MovieDTO("Inception", null, IndicativeRating.GENERAL_AUDIENCE);

        assertThrows(InvalidMovieGenreException.class, () -> movieService.createMovie(invalidData));
    }

    @Test
    void shouldThrowExceptionWhenIndicativeRatingIsInvalid() {
        MovieDTO invalidData = new MovieDTO("Inception", Genre.ACTION, null);

        assertThrows(InvalidMovieGenreException.class, () -> movieService.createMovie(invalidData));
    }

    @Test
    public void testGetAllMovies() {
        List<Movie> movies = List.of(new Movie(), new Movie());
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();

        assertEquals(movies.size(), result.size());
        verify(movieRepository).findAll();
    }

    @Test
    void shouldDeleteMovieWhenIdIsValid() {
        Long validId = 1L;
        Movie movie = new Movie();
        when(movieRepository.findMovieById(validId)).thenReturn(Optional.of(movie));

        movieService.delete(validId);

        verify(movieRepository).deleteById(validId);
    }
}
