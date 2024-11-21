package com.boxoffice.ticketmanager.entity;

import com.boxoffice.ticketmanager.dtos.MovieSessionDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_session")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class MovieSession implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
    private String startTime;
    private int availableSeats;

    public MovieSession(MovieSessionDTO data){
        this.film = data.film();
        this.startTime = data.startTime();
        this.availableSeats = data.availableSeats();
    }
}
