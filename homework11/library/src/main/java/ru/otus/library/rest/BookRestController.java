package ru.otus.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookDto;
import ru.otus.library.dto.mapper.BookDtoMapper;
import ru.otus.library.dto.BookNewAndChangeDto;
import ru.otus.library.repository.AuthorsRepository;
import ru.otus.library.repository.BooksRepository;
import ru.otus.library.repository.CommentsBookRepository;
import ru.otus.library.repository.GenreRepository;

@RestController
public class BookRestController {
    private final BooksRepository booksRepository;

    private final AuthorsRepository authorsRepository;

    private final GenreRepository genreRepository;

    private final CommentsBookRepository commentsBookRepository;

    public BookRestController(@Autowired BooksRepository booksRepository,
                              @Autowired AuthorsRepository authorsRepository,
                              @Autowired GenreRepository genreRepository,
                              @Autowired CommentsBookRepository commentsBookRepository) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
        this.genreRepository = genreRepository;
        this.commentsBookRepository = commentsBookRepository;
    }

    @GetMapping("/api/book")
    public Flux<BookDto> getBooks() {
        return booksRepository.findAll()
                .map(BookDtoMapper::toDto);
    }

    @GetMapping("/api/book/{id}")
    public Mono<BookDto> getBook(@PathVariable String id) {
        Mono<BookDto> book = booksRepository.findById(id)
                .map(BookDtoMapper::toDto);
        return book;
    }

    @PostMapping("/api/book/")
    public Mono<BookDto> addBook(@RequestBody BookNewAndChangeDto bookDto) {
        Author author = authorsRepository.findById(bookDto.getAuthorId()).block();
        Genre genre = genreRepository.findById(bookDto.getGenreId()).block();
        return booksRepository.save(new Book(bookDto.getName(), author, genre)).map(BookDtoMapper::toDto);
    }

    @PutMapping("/api/book/{id}")
    public Mono<BookDto> updateBook(@RequestBody BookNewAndChangeDto bookDto) {
        Author author = authorsRepository.findById(bookDto.getAuthorId()).block();
        Genre genre = genreRepository.findById(bookDto.getGenreId()).block();
        return booksRepository.
                save(new Book(bookDto.getId(), bookDto.getName(), author, genre)).map(BookDtoMapper::toDto);
    }

    @DeleteMapping("/api/book/{id}")
    public String deleteBook(@PathVariable String id) {
        booksRepository.deleteById(id).subscribe();
        return "redirect:/book";
    }
}
