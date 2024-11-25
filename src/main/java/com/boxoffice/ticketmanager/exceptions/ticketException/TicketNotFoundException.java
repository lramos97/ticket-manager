package com.boxoffice.ticketmanager.exceptions.ticketException;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException() {

        super("Ticket Not Found.");
    }

    public TicketNotFoundException(String message) {

        super(message);
    }
}
