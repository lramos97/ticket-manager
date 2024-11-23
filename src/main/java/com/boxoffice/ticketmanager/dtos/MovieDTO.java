package com.boxoffice.ticketmanager.dtos;

import com.boxoffice.ticketmanager.entity.Movie.Genre;
import com.boxoffice.ticketmanager.entity.Movie.IndicativeRating;

public record MovieDTO(String title, Genre genre, IndicativeRating indicativeRating) {
}
