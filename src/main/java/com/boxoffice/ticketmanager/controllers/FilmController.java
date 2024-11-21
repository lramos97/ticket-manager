package com.boxoffice.ticketmanager.controllers;

import com.boxoffice.ticketmanager.dtos.FilmDTO;
import com.boxoffice.ticketmanager.entity.Film;
import com.boxoffice.ticketmanager.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody FilmDTO film){
        Film newFilm = filmService.createFilm(film);
        return new ResponseEntity<>(newFilm, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> films = this.filmService.getAllFilms();
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        filmService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
