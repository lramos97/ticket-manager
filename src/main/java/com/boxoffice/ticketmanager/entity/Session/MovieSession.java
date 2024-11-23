package com.boxoffice.ticketmanager.entity.Session;

import com.boxoffice.ticketmanager.dtos.MovieSessionDTO;
import com.boxoffice.ticketmanager.entity.Movie.Movie;
import com.boxoffice.ticketmanager.entity.Ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
    @JoinColumn(name = "movie_id")
    private Movie movie;
    private String startTime;
    private int availableSeats;

    @OneToMany(mappedBy = "movieSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    public MovieSession(Long id, Movie movie, String startTime, int availableSeats) {
        this.id = id;
        this.movie = movie;
        this.startTime = startTime;
        this.availableSeats = availableSeats;
    }

    public MovieSession(MovieSessionDTO data){
        this.movie = data.movie();
        this.startTime = data.startTime();
        this.availableSeats = data.availableSeats();
    }

    public void reserveSeat() {
        if (this.availableSeats > 0) {
            this.availableSeats--;
        } else {
            throw new IllegalStateException("No available seats for this session.");
        }
    }
}

