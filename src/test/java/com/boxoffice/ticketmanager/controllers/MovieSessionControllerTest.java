package com.boxoffice.ticketmanager.controllers;

import com.boxoffice.ticketmanager.dtos.MovieDTO;
import com.boxoffice.ticketmanager.dtos.MovieSessionDTO;
import com.boxoffice.ticketmanager.entity.Movie.Genre;
import com.boxoffice.ticketmanager.entity.Movie.IndicativeRating;
import com.boxoffice.ticketmanager.entity.Movie.Movie;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.services.MovieSessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieSessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MovieSessionService movieSessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateSession() throws Exception {
        MovieDTO movieDTO = new MovieDTO("Inception", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE);
        Movie movie = new Movie(movieDTO);
        MovieSessionDTO sessionDTO = new MovieSessionDTO(movie, "14:00", 100);
        MovieSession session = new MovieSession(sessionDTO);
        session.setId(1L);

        when(movieSessionService.createSession(sessionDTO)).thenReturn(session);

        mockMvc.perform(post("/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.movie.title").value("Inception"))
                .andExpect(jsonPath("$.startTime").value("14:00"))
                .andExpect(jsonPath("$.availableSeats").value(100));

        verify(movieSessionService, times(1)).createSession(sessionDTO);
    }

    @Test
    void shouldGetAllSessions() throws Exception {
        MovieSession session1 = new MovieSession(new MovieSessionDTO(
                new Movie(new MovieDTO("Inception", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE)),
                "14:00", 100));
        session1.setId(1L);
        MovieSession session2 = new MovieSession(new MovieSessionDTO(
                new Movie(new MovieDTO("The Dark Knight", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE)),
                "18:00", 50));
        session2.setId(2L);
        List<MovieSession> sessions = Arrays.asList(session1, session2);

        when(movieSessionService.getAllSessions()).thenReturn(sessions);

        mockMvc.perform(get("/sessions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].movie.title").value("Inception"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].movie.title").value("The Dark Knight"));

        verify(movieSessionService, times(1)).getAllSessions();
    }


    @Test
    void shouldGetSessionById() throws Exception {
        MovieSession session = new MovieSession(new MovieSessionDTO(
                new Movie(new MovieDTO("Inception", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE)),
                "14:00", 100));
        session.setId(1L);

        when(movieSessionService.findSessionById(1L)).thenReturn(session);

        mockMvc.perform(get("/sessions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.movie.title").value("Inception"))
                .andExpect(jsonPath("$.availableSeats").value(100));

        verify(movieSessionService, times(1)).findSessionById(1L);
    }

    @Test
    void shouldDeleteSession() throws Exception {
        doNothing().when(movieSessionService).delete(1L);

        mockMvc.perform(delete("/sessions/1"))
                .andExpect(status().isNoContent());

        verify(movieSessionService, times(1)).delete(1L);
    }
}
