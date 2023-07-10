package ru.otus.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDto;
import ru.otus.library.dto.mapper.BookDtoMapper;
import ru.otus.library.dto.BookNewAndChangeDto;
import ru.otus.library.service.BookService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookRestController {
    private final BookService bookService;

    public BookRestController(@Autowired BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book")
    public List<BookDto> getBooks(){
        List<BookDto> books;
        books = bookService.getAllBooks()
                .stream()
                .map(BookDtoMapper::toDto)
                .collect(Collectors.toList());
        return books;
    }

    @GetMapping("/api/book/{id}")
    public BookDto getBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null){
            return null;
        }
        return BookDtoMapper.toDto(book);
    }

    @PostMapping("/api/book/")
    public BookDto addBook(@RequestBody BookNewAndChangeDto bookDto){
        Book book = bookService.addBook(bookDto.getName(), bookDto.getAuthorId(), bookDto.getGenreId());
        return BookDtoMapper.toDto(book);
    }

    @PutMapping("/api/book/{id}")
    public BookDto updateBook(@RequestBody BookNewAndChangeDto book){
        Book bookUpdate = bookService.updateBook(book.getId(), book.getName(), book.getAuthorId(), book.getGenreId());
        return BookDtoMapper.toDto(bookUpdate);
    }

    @DeleteMapping("/api/book/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.removeBook(id);
        return "redirect:/book";
    }
}
