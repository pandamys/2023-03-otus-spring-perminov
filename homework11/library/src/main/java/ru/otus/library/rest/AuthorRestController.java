package ru.otus.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorsRepository;

@RestController
public class AuthorRestController {
    private AuthorsRepository authorsRepository;

    public AuthorRestController(@Autowired AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    @GetMapping("/api/author")
    public Flux<Author> getAuthors() {
        Flux<Author> authors = authorsRepository.findAll();
        return authors;
    }
}
