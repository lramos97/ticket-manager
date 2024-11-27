package com.boxoffice.ticketmanager.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.boxoffice.ticketmanager.dtos.MovieDTO;
import com.boxoffice.ticketmanager.dtos.MovieSessionDTO;
import com.boxoffice.ticketmanager.entity.Movie.Genre;
import com.boxoffice.ticketmanager.entity.Movie.IndicativeRating;
import com.boxoffice.ticketmanager.entity.Movie.Movie;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.exceptions.movieSessionException.MovieSessionNotFoundException;
import com.boxoffice.ticketmanager.exceptions.movieSessionException.NoSeatsAvailableException;
import com.boxoffice.ticketmanager.repositories.MovieSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class MovieSessionServiceTest {

	@Mock
	private MovieSessionRepository movieSessionRepository;

	@InjectMocks
	private MovieSessionService movieSessionService;

	private MovieSession movieSession;
	private MovieSessionDTO movieSessionDTO;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		MovieDTO movieDTO = new MovieDTO("Inception", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE);
		Movie movie = new Movie(movieDTO);
		movie.setId(1L);

		movieSessionDTO = new MovieSessionDTO(movie, "14:00", 100);
		movieSession = new MovieSession(movieSessionDTO);
		movieSession.setId(1L);
	}

	@Test
	public void testFindSessionById_Success() {
		when(movieSessionRepository.findSessionById(1L)).thenReturn(Optional.of(movieSession));

		MovieSession result = movieSessionService.findSessionById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Inception", result.getMovie().getTitle());
	}

	@Test
	public void testFindSessionById_NotFound() {
		when(movieSessionRepository.findSessionById(1L)).thenReturn(Optional.empty());

		assertThrows(MovieSessionNotFoundException.class, () -> movieSessionService.findSessionById(1L));
	}

	@Test
	public void testCreateSession() {
		when(movieSessionRepository.save(any(MovieSession.class))).thenReturn(movieSession);

		MovieSession result = movieSessionService.createSession(movieSessionDTO);

		assertNotNull(result);
		assertEquals("Inception", result.getMovie().getTitle());
		verify(movieSessionRepository, times(1)).save(any(MovieSession.class));
	}

	@Test
	public void testGetAllSessions() {
		when(movieSessionRepository.findAll()).thenReturn(List.of(movieSession));

		List<MovieSession> sessions = movieSessionService.getAllSessions();

		assertNotNull(sessions);
		assertEquals(1, sessions.size());
		assertEquals("Inception", sessions.get(0).getMovie().getTitle());
	}

	@Test
	public void testDeleteSession() {
		when(movieSessionRepository.findSessionById(1L)).thenReturn(Optional.of(movieSession));

		movieSessionService.delete(1L);

		verify(movieSessionRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testReserveSeat_Success() {
		when(movieSessionRepository.findSessionById(1L)).thenReturn(Optional.of(movieSession));
		when(movieSessionRepository.save(any(MovieSession.class))).thenReturn(movieSession);

		movieSessionService.reserveSeat(1L);

		assertEquals(99, movieSession.getAvailableSeats());
		verify(movieSessionRepository, times(1)).save(any(MovieSession.class));
	}

	@Test
	public void testReserveSeat_NoSeatsAvailable() {
		movieSession.setAvailableSeats(0);
		when(movieSessionRepository.findSessionById(1L)).thenReturn(Optional.of(movieSession));

		assertThrows(NoSeatsAvailableException.class, () -> movieSessionService.reserveSeat(1L));
	}

}

