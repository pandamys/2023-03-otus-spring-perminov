package ru.otus.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.GenreService;
import java.util.List;

@RestController
public class GenreRestController {
    private GenreService genreService;

    public GenreRestController(@Autowired GenreService genreService){
        this.genreService = genreService;
    }

    @GetMapping("/api/genre")
    public List<Genre> getGenres(){
        List<Genre> genres = genreService.getAll();
        return genres;
    }
}
