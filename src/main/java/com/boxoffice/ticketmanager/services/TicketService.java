package com.boxoffice.ticketmanager.services;

import com.boxoffice.ticketmanager.dtos.TicketDTO;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.entity.Ticket.Ticket;
import com.boxoffice.ticketmanager.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketService {

    public static final Double BASE_PRICE = 30.0;
    @Autowired
    private TicketRepository ticketRepository;

	@Autowired
	private MovieSessionService movieSessionService;

    public Ticket createTicket(TicketDTO ticketDTO) {

        MovieSession session = movieSessionService.findSessionById(ticketDTO.movieSession().getId());
        movieSessionService.reserveSeat(ticketDTO.movieSession().getId());

        Ticket ticket = new Ticket();
        ticket.setBuyer(ticketDTO.buyer());
        ticket.setMovieSession(session);
        ticket.setSeat(session.getAvailableSeats() + 1);
        ticket.setTicketType(ticketDTO.type());
        ticket.calculatePrice(BASE_PRICE);

        movieSessionService.save(session);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return this.ticketRepository.findAll();
    }

    public Ticket findTicketById(Long id) throws Exception {
        return this.ticketRepository.findTicketById(id).orElseThrow(() -> new Exception("Movie not found"));
    }

    public List<TicketDTO> findTicketsBySession(Long sessionId) {
        return ticketRepository.findByMovieSessionId(sessionId).stream()
                .map(ticket -> new TicketDTO(
                        ticket.getBuyer(),
                        ticket.getMovieSession(),
                        ticket.getSeat(),
                        ticket.getTicketType()
                ))
                .toList();
    }
}

