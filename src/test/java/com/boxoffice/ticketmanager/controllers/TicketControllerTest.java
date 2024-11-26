package com.boxoffice.ticketmanager.controllers;

import com.boxoffice.ticketmanager.dtos.TicketDTO;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.entity.Ticket.Ticket;
import com.boxoffice.ticketmanager.entity.Ticket.TicketType;
import com.boxoffice.ticketmanager.repositories.TicketRepository;
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

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    MovieSessionService movieSessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateTicket() throws Exception {
        MovieSession movieSession = new MovieSession();
        movieSession.setId(1L);
        movieSession.setAvailableSeats(10);
        movieSessionService.save(movieSession);

        TicketDTO ticketDTO = new TicketDTO(
                "John Doe",
                movieSession,
                1,
                TicketType.FULL_PRICE,
                30.0
        );

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.buyer").value("John Doe"))
                .andExpect(jsonPath("$.seat").value(1))
                .andExpect(jsonPath("$.ticketType").value("FULL_PRICE"));
    }

    @Test
    void shouldReturnAllTickets() throws Exception {
        Ticket ticket = new Ticket("Jane Doe", new MovieSession(), 1, TicketType.HALF_PRICE, 15.0);
        ticketRepository.save(ticket);

        mockMvc.perform(get("/tickets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].buyer").value("Jane Doe"))
                .andExpect(jsonPath("$[0].ticketType").value("HALF_PRICE"));
    }

    @Test
    void shouldReturnTicketById() throws Exception {
        Ticket ticket = new Ticket("John Smith", new MovieSession(), 2, TicketType.FULL_PRICE, 30.0);
        ticket = ticketRepository.save(ticket);

        mockMvc.perform(get("/tickets/{id}", ticket.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buyer").value("John Smith"))
                .andExpect(jsonPath("$.seat").value(2));
    }

    @Test
    void shouldReturnTicketsBySession() throws Exception {
        MovieSession movieSession = new MovieSession();
        movieSession.setId(1L);
        movieSession.setAvailableSeats(10);
        movieSessionService.save(movieSession);

        Ticket ticket1 = new Ticket("Alice", movieSession, 1, TicketType.FULL_PRICE, 30.0);
        Ticket ticket2 = new Ticket("Bob", movieSession, 2, TicketType.HALF_PRICE, 15.0);
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);

        mockMvc.perform(get("/tickets/session/{sessionId}", movieSession.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].buyer").value("Alice"))
                .andExpect(jsonPath("$[1].buyer").value("Bob"));
    }
}
