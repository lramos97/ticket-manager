package com.boxoffice.ticketmanager.services;

import com.boxoffice.ticketmanager.entity.Ticket.Ticket;
import com.boxoffice.ticketmanager.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    public Ticket findTicketById(Long id) throws Exception {
        return this.repository.findTicketById(id).orElseThrow(() -> new Exception("Ticket not found"));
    }

    public List<Ticket> getAllTickets() {
        return this.repository.findAll();
    }

    public void saveTicket(Ticket ticket) {
        this.repository.save(ticket);
    }
}
