package ru.otus.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreRepository;

@RestController
public class GenreRestController {
    private GenreRepository genreRepository;

    public GenreRestController(@Autowired GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/api/genre")
    public Flux<Genre> getGenres() {
        Flux<Genre> genres = genreRepository.findAll();
        return genres;
    }
}
