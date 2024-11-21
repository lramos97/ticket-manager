package com.boxoffice.ticketmanager.repositories;

import com.boxoffice.ticketmanager.entity.Ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findTicketById(Long id);
}
