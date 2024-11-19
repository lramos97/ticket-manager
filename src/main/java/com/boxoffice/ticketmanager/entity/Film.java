package com.boxoffice.ticketmanager.entity;

import com.boxoffice.ticketmanager.dtos.FilmDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "film")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Enumerated(EnumType.STRING)
    private IndicativeRating indicativeRating;

    public Film(FilmDTO data) {
        this.title = data.title();
        this.genre = data.genre();
        this.indicativeRating = data.indicativeRating();
    }
}
