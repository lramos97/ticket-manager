package com.boxoffice.ticketmanager.services;

import com.boxoffice.ticketmanager.dtos.FilmDTO;
import com.boxoffice.ticketmanager.entity.Film.Film;
import com.boxoffice.ticketmanager.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository repository;

    public Film findFilmById(Long id) throws Exception {
        return this.repository.findFilmById(id).orElseThrow(() -> new Exception("Film not found"));
    }

    public Film createFilm(FilmDTO data) {
        Film newFilm = new Film(data);
        this.saveFilm(newFilm);
        return newFilm;
    }

    public List<Film> getAllFilms() {
        return this.repository.findAll();
    }

    public void saveFilm(Film film) {
        this.repository.save(film);
    }

    public void delete(Long id) throws Exception {
        findFilmById(id);
        repository.deleteById(id);
    }
}
