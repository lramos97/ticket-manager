package com.boxoffice.ticketmanager.dtos;

import com.boxoffice.ticketmanager.entity.Film.Film;

public record MovieSessionDTO(Film film, String startTime, int availableSeats) {
}
