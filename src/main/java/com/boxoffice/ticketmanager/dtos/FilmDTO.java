package com.boxoffice.ticketmanager.dtos;

import com.boxoffice.ticketmanager.entity.Genre;
import com.boxoffice.ticketmanager.entity.IndicativeRating;

public record FilmDTO(String title, Genre genre, IndicativeRating indicativeRating) {
}
