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
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = ticketService.createTicket(ticketDTO);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllFilms() {
        List<Ticket> tickets = this.ticketService.getAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<TicketDTO>> getTicketsBySession(@PathVariable Long sessionId) {
        List<TicketDTO> tickets = ticketService.findTicketsBySession(sessionId);
        return ResponseEntity.ok(tickets);
    }
}
