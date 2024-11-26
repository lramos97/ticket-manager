package com.boxoffice.ticketmanager.services;

import com.boxoffice.ticketmanager.dtos.MovieDTO;
import com.boxoffice.ticketmanager.dtos.MovieSessionDTO;
import com.boxoffice.ticketmanager.dtos.TicketDTO;
import com.boxoffice.ticketmanager.entity.Movie.Genre;
import com.boxoffice.ticketmanager.entity.Movie.IndicativeRating;
import com.boxoffice.ticketmanager.entity.Movie.Movie;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.entity.Ticket.Ticket;
import com.boxoffice.ticketmanager.entity.Ticket.TicketType;
import com.boxoffice.ticketmanager.exceptions.movieException.InvalidMovieGenreException;
import com.boxoffice.ticketmanager.exceptions.ticketException.InvalidTicketTypeException;
import com.boxoffice.ticketmanager.exceptions.ticketException.TicketNotFoundException;
import com.boxoffice.ticketmanager.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    private MovieSessionService movieSessionService;

    @InjectMocks
    private TicketService ticketService;
    private Ticket ticket;
    private TicketDTO ticketDTO;

    @BeforeEach
    public void setUp() {
        MovieSessionDTO movieSessionDTO = new MovieSessionDTO(new Movie(new MovieDTO("Inception", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE)), "10:30", 100);
        MovieSession movieSession = new MovieSession(movieSessionDTO);
        movieSession.setId(1L);

        ticketDTO = new TicketDTO("John", movieSession, 13, TicketType.HALF_PRICE, 30.0);
        ticket = new Ticket(ticketDTO);
        ticket.setId(1L);
    }

    @Test
    public void testFindTicketById_Success() throws Exception {
        when(ticketRepository.findTicketById(1L)).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.findTicketById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getBuyer());
    }

    @Test
    public void testFindTicketById_NotFound() {
        when(ticketRepository.findTicketById(1L)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> ticketService.findTicketById(1L));
    }

    @Test
    public void testCreateTicket() {
        MovieSession session = new MovieSession();
        session.setId(1L);
        session.setAvailableSeats(100);

        when(movieSessionService.findSessionById(1L)).thenReturn(session);
        doNothing().when(movieSessionService).reserveSeat(1L);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.createTicket(ticketDTO);

        assertNotNull(result);
        assertEquals("John", result.getBuyer());
        assertEquals(11, result.getSeat());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
        verify(movieSessionService, times(1)).reserveSeat(1L);
        verify(movieSessionService, times(1)).save(session);
    }

    @Test
    public void testGetAllSessions() {
        when(ticketRepository.findAll()).thenReturn(List.of(ticket));

        List<Ticket> sessions = ticketService.getAllTickets();

        assertNotNull(sessions);
        assertEquals(1, sessions.size());
    }

    @Test
    void testWhenTicketTypeIsInvalid() {
        MovieSessionDTO movieSessionDTO = new MovieSessionDTO(new Movie(new MovieDTO("Inception", Genre.ACTION, IndicativeRating.GENERAL_AUDIENCE)), "10:30", 100);
        MovieSession movieSession = new MovieSession(movieSessionDTO);
        TicketDTO invalidData = new TicketDTO("John", movieSession, 13, null, 30.0);

        assertThrows(InvalidTicketTypeException.class, () -> ticketService.createTicket(invalidData));
    }
}
