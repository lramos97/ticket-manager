package com.boxoffice.ticketmanager.config;

import com.boxoffice.ticketmanager.entity.Movie.Movie;
import com.boxoffice.ticketmanager.entity.Movie.Genre;
import com.boxoffice.ticketmanager.entity.Movie.IndicativeRating;
import com.boxoffice.ticketmanager.entity.Session.MovieSession;
import com.boxoffice.ticketmanager.entity.Ticket.Ticket;
import com.boxoffice.ticketmanager.entity.Ticket.TicketType;
import com.boxoffice.ticketmanager.repositories.MovieRepository;
import com.boxoffice.ticketmanager.repositories.MovieSessionRepository;
import com.boxoffice.ticketmanager.repositories.TicketRepository;
import com.boxoffice.ticketmanager.services.MovieSessionService;
import com.boxoffice.ticketmanager.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieSessionRepository movieSessionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    MovieSessionService movieSessionService;

    @Override
    public void run(String... args) throws Exception {

        movieRepository.deleteAll();
        movieSessionRepository.deleteAll();
        ticketRepository.deleteAll();

        Movie f1 = new Movie(null,"Lion", Genre.DRAMA, IndicativeRating.TEENS_14_AND_UP);
        Movie f2 = new Movie(null,"Minions", Genre.ANIMATION, IndicativeRating.GENERAL_AUDIENCE);
        Movie f3 = new Movie(null,"Before you", Genre.ROMANCE, IndicativeRating.TEENS_14_AND_UP);
        Movie f4 = new Movie(null,"The Substance", Genre.HORROR, IndicativeRating.ADULTS_ONLY);

        movieRepository.saveAll(Arrays.asList(f1, f2, f3, f4));

        MovieSession s1 = new MovieSession(null, f1, "10:30", 100);
        MovieSession s2 = new MovieSession(null, f1, "19:00", 100);
        MovieSession s3 = new MovieSession(null, f2, "17:30", 100);
        MovieSession s4 = new MovieSession(null, f3, "20:30", 100);
        MovieSession s5 = new MovieSession(null, f4, "22:00", 100);

        movieSessionRepository.saveAll(Arrays.asList(s1,s2,s3,s4,s5));

        Ticket t1 = new Ticket("Maria", s1, 1, TicketType.FULL_PRICE, TicketService.BASE_PRICE);
        Ticket t2 = new Ticket("João", s1, 2, TicketType.HALF_PRICE, TicketService.BASE_PRICE);
        Ticket t3 = new Ticket("Carlos", s2, 1, TicketType.HALF_PRICE, TicketService.BASE_PRICE);
        Ticket t4 = new Ticket("Laura", s4, 1, TicketType.FULL_PRICE, TicketService.BASE_PRICE);
        Ticket t5 = new Ticket("Mauro", s5, 1, TicketType.FULL_PRICE, TicketService.BASE_PRICE);
        ticketRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5));
    }
}
