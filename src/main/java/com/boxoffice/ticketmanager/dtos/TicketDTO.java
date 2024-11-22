package com.boxoffice.ticketmanager.dtos;

import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.entity.Ticket.TicketType;

public record TicketDTO(String buyer, MovieSession movieSession, int seat, TicketType type) {
}
