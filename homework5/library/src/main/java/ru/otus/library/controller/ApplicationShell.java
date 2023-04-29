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

    @ShellMethod(value = "Get all books", key = {"listBooks"})
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
    public void addBook(String nameBook, Long author, Long genre){
        boolean result;
        result = bookService.addBook(nameBook, author, genre);
        if (result){
            System.out.println("Book added successful");
        } else {
            System.out.println("Error book add");
        }
    }

    @ShellMethod(value = "Delete book", key = {"rm", "deleteBook"})
    public void deleteBook(long id){
        boolean result;
        result = bookService.removeBook(id);
        if (result){
            System.out.println("Book deleted successful");
        } else {
            System.out.println("Error book delete");
        }
    }
}
