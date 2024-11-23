package com.boxoffice.ticketmanager.dtos;

import com.boxoffice.ticketmanager.entity.Movie.Movie;

public record MovieSessionDTO(Movie movie, String startTime, int availableSeats) {
}
