package ru.otus.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;
import java.util.List;

@RestController
public class AuthorRestController {
    private AuthorService authorService;

    public AuthorRestController(@Autowired AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/api/author")
    public List<Author> getAuthors(){
        List<Author> authors = authorService.getAll();
        return authors;
    }
}
