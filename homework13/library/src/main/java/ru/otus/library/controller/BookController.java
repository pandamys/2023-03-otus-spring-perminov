package ru.otus.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookDto;
import ru.otus.library.dto.BookDtoMapper;
import ru.otus.library.dto.BookNewAndChangeDto;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    public BookController(@Autowired BookService bookService,
                          @Autowired AuthorService authorService,
                          @Autowired GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/")
    public String mainPage() {
        return "redirect:/book";
    }

    @GetMapping("/book")
    public String getAllBooks(Model model) {
        List<BookDto> books;
        books = bookService.getAllBooks()
                .stream()
                .map(BookDtoMapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("book/edit")
    public String editBook(@RequestParam("id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        getDataForModel(model);
        return "book-edit";
    }

    @PostMapping("book/edit")
    public String editBook(BookNewAndChangeDto book) {
        bookService.updateBook(book.getId(), book.getName(), book.getAuthorId(), book.getGenreId());
        return "redirect:/";
    }

    @GetMapping("book/view")
    public String viewBook(@RequestParam("id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book-view";
    }

    @GetMapping("book/add")
    public String addBook(BookNewAndChangeDto bookNewAndChangeDto, Model model) {
        getDataForModel(model);
        return "book-add";
    }

    @PostMapping("book/add")
    public String addBook(@ModelAttribute("book") BookNewAndChangeDto bookNewAndChangeDto,
                          BindingResult bindingResult,
                          Model model) {
        bookService.addBook(
                bookNewAndChangeDto.getName(),
                bookNewAndChangeDto.getAuthorId(),
                bookNewAndChangeDto.getGenreId());
        return "redirect:/book";
    }

    @GetMapping("book/delete")
    public String deleteBook(@RequestParam("id") Long id) {
        bookService.removeBook(id);
        return "redirect:/book";
    }

    private void getDataForModel(Model model) {
        List<Author> authors = authorService.getAll();
        List<Genre> genres = genreService.getAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
    }
}
