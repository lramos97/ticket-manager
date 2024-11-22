package com.boxoffice.ticketmanager.entity.Ticket;

import com.boxoffice.ticketmanager.dtos.TicketDTO;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Buyer name is required")
    private String buyer;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "session_id")
    private MovieSession movieSession;
    private Integer seat;
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
    private Double price;

    public Ticket(String buyer, MovieSession movieSession, int seat, TicketType ticketType, double basePrice) {
        this.buyer = buyer;
        this.movieSession = movieSession;
        this.seat = seat;
        this.ticketType = ticketType;
        calculatePrice(basePrice);
    }

    public Ticket(TicketDTO data){
        this.buyer = data.buyer();
        this.movieSession = data.movieSession();
        this.seat = data.seat();
        this.ticketType = data.type();
    }

    public void calculatePrice(double basePrice) {
        this.price = ticketType == TicketType.HALF_PRICE ? basePrice / 2 : basePrice;
    }
}
