package com.boxoffice.ticketmanager.dtos;

import com.boxoffice.ticketmanager.entity.Film.Genre;
import com.boxoffice.ticketmanager.entity.Film.IndicativeRating;

public record FilmDTO(String title, Genre genre, IndicativeRating indicativeRating) {
}
