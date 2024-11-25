package com.boxoffice.ticketmanager.exceptions.ticketException;

public class InvalidTicketTypeException extends RuntimeException {

    public InvalidTicketTypeException() {
        super("Invalid Ticket Type.");
    }
    public InvalidTicketTypeException(String message) {
        super(message);
    }
}
