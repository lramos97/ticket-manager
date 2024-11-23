package com.boxoffice.ticketmanager.entity.Movie;

import com.boxoffice.ticketmanager.dtos.MovieDTO;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Enumerated(EnumType.STRING)
    private IndicativeRating indicativeRating;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieSession> sessions = new ArrayList<>();

    public Movie(Long id, String title, Genre genre, IndicativeRating indicativeRating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.indicativeRating = indicativeRating;
    }

    public Movie(MovieDTO data) {
        this.title = data.title();
        this.genre = data.genre();
        this.indicativeRating = data.indicativeRating();
    }
}
