package com.boxoffice.ticketmanager.entity.Session;

import com.boxoffice.ticketmanager.dtos.MovieSessionDTO;
import com.boxoffice.ticketmanager.entity.Film.Film;
import com.boxoffice.ticketmanager.entity.Ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie_session")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class MovieSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
    private String startTime;
    private int availableSeats;

    @OneToMany(mappedBy = "movieSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    public MovieSession(Long id, Film film, String startTime, int availableSeats) {
        this.id = id;
        this.film = film;
        this.startTime = startTime;
        this.availableSeats = availableSeats;
    }

    public MovieSession(MovieSessionDTO data){
        this.film = data.film();
        this.startTime = data.startTime();
        this.availableSeats = data.availableSeats();
    }

    public void reserveSeat() {
        if (availableSeats <= 0) {
            throw new IllegalStateException("No seats available for this session");
        }
        availableSeats--;
    }
}
