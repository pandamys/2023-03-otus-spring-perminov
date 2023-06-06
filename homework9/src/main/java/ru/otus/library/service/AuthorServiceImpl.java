package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorsRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorsRepository authorsRepository;

    public AuthorServiceImpl(AuthorsRepository authorsRepository){
        this.authorsRepository = authorsRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorsRepository.findAll();
    }

    @Override
    public Author getById(long id) {
        return authorsRepository.findById(id).orElse(null);
    }
}