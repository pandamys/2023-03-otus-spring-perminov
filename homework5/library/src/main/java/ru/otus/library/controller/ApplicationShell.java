package ru.otus.library.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Book;
import ru.otus.library.service.BookService;

import java.util.List;

@ShellComponent
public class ApplicationShell {
    private final BookService bookService;

    public ApplicationShell(BookService bookService){
        this.bookService = bookService;
    }

    @ShellMethod(value = "Get all books", key = {"listBook"})
    public void getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        books.forEach(book -> System.out.println(book.getInfoAboutBook()));
    }

    @ShellMethod(value = "Get book", key = {"getBook"})
    public void getBook(long id){
        Book book;
        book = bookService.getBookById(id);
        if (book != null) {
            System.out.println(book.getInfoAboutBook());
        } else {
            System.out.println("Book with id '%s' not found");
        }
    }

    @ShellMethod(value = "Add book", key = {"addBook"})
    public void addBook(String nameBook, String nameAuthor, String surnameAuthor, String genre){
        boolean result;
        result = bookService.addBook(nameBook, nameAuthor, surnameAuthor, genre);
    }
}
