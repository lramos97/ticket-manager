package com.boxoffice.ticketmanager.entity.Ticket;

import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.entity.User.User;
import jakarta.persistence.*;
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
    private User user;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private MovieSession movieSession;
    private int seat;
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
    private Double price;

    public void calculatePrice(double basePrice) {
        this.price = ticketType == TicketType.HALF_PRICE ? basePrice / 2 : basePrice;
    }
}
