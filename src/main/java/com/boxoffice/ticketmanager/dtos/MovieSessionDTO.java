package com.boxoffice.ticketmanager.dtos;

import com.boxoffice.ticketmanager.entity.Film;

public record MovieSessionDTO(Film film, String startTime, int availableSeats) {
}
