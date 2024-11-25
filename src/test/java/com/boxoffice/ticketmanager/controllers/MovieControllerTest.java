package com.boxoffice.ticketmanager.controllers;

import com.boxoffice.ticketmanager.dtos.MovieDTO;
import com.boxoffice.ticketmanager.entity.Movie.Genre;
import com.boxoffice.ticketmanager.entity.Movie.IndicativeRating;
import com.boxoffice.ticketmanager.entity.Movie.Movie;
import com.boxoffice.ticketmanager.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MovieService movieService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldCreateMovie() throws Exception {
		MovieDTO movieDTO = new MovieDTO("Inception", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE);
		Movie movie = new Movie(movieDTO);
		movie.setId(1L);

		ArgumentCaptor<MovieDTO> captor = ArgumentCaptor.forClass(MovieDTO.class);
		when(movieService.createMovie(captor.capture())).thenReturn(movie);

		mockMvc.perform(post("/movies")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(movieDTO)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.title").value("Inception"))
			.andExpect(jsonPath("$.genre").value("ACTION"))
			.andExpect(jsonPath("$.indicativeRating").value("GENERAL_AUDIENCE"));

		verify(movieService, times(1)).createMovie(captor.capture());
	}

	@Test
	void shouldGetAllMovies() throws Exception {
		MovieDTO movieDTO = new MovieDTO("Inception", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE);
		Movie movie = new Movie(movieDTO);
		MovieDTO movieDTO2 = new MovieDTO("The Dark Knight", Genre.HORROR, IndicativeRating.GENERAL_AUDIENCE);
		Movie movie2 = new Movie(movieDTO2);
		List<Movie> movies = Arrays.asList(movie, movie2);

		when(movieService.getAllMovies()).thenReturn(movies);

		mockMvc.perform(get("/movies"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].title").value("Inception"))
			.andExpect(jsonPath("$[1].title").value("The Dark Knight"));

		verify(movieService, times(1)).getAllMovies();
	}

	@Test
	void shouldGetMovieById() throws Exception {
		MovieDTO movieDTO = new MovieDTO("Inception", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE);
		Movie movie = new Movie(movieDTO);

		when(movieService.findMovieById(1L)).thenReturn(movie);

		mockMvc.perform(get("/movies/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(movie.getId()))
			.andExpect(jsonPath("$.title").value(movie.getTitle()));

		verify(movieService, times(1)).findMovieById(1L);
	}

	@Test
	void shouldDeleteMovie() throws Exception {
		doNothing().when(movieService).delete(1L);

		mockMvc.perform(delete("/movies/1"))
			.andExpect(status().isNoContent());

		verify(movieService, times(1)).delete(1L);
	}
}
