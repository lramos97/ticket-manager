package com.boxoffice.ticketmanager.controllers;

import com.boxoffice.ticketmanager.dtos.TicketDTO;
import com.boxoffice.ticketmanager.entity.Ticket.Ticket;
import com.boxoffice.ticketmanager.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticket) {
        Ticket newticket = ticketService.createTicket(ticket);
        return new ResponseEntity<>(new TicketDTO(
                newticket.getBuyer(),
                newticket.getMovieSession(),
                newticket.getSeat(),
                newticket.getTicketType(),
                newticket.getPrice()
        ), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> ticketDTOs = ticketService.getAllTickets()
                .stream()
                .map(ticket -> new TicketDTO(
                        ticket.getBuyer(),
                        ticket.getMovieSession(),
                        ticket.getSeat(),
                        ticket.getTicketType(),
                        ticket.getPrice()))
                .toList();
        return ResponseEntity.ok(ticketDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) throws Exception {
        Ticket ticket = ticketService.findTicketById(id);
        TicketDTO ticketDTO = new TicketDTO(
                ticket.getBuyer(),
                ticket.getMovieSession(),
                ticket.getSeat(),
                ticket.getTicketType(),
                ticket.getPrice());

        return ResponseEntity.ok(ticketDTO);
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<TicketDTO>> getTicketsBySession(@PathVariable Long sessionId) {
        List<TicketDTO> tickets = ticketService.findTicketsBySession(sessionId);
        return ResponseEntity.ok(tickets);
    }
}
