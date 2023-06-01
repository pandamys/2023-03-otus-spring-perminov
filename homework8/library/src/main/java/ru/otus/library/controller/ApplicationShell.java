package ru.otus.library.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;
import ru.otus.library.service.GenreService;

import java.util.List;

@ShellComponent
public class ApplicationShell {
    private final BookService bookService;

    private final CommentService commentService;

    private final AuthorService authorService;

    private final GenreService genreService;

    public ApplicationShell(BookService bookService,
                            CommentService commentService,
                            AuthorService authorService,
                            GenreService genreService){
        this.bookService = bookService;
        this.commentService = commentService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @ShellMethod(value = "Get all books", key = {"listBooks", "lb"})
    public void getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        books.forEach(book -> System.out.println(book.getInfoAboutBook()));
    }

    @ShellMethod(value = "Get book", key = {"getBook", "gb"})
    public void getBook(String id){
        Book book;
        book = bookService.getBookById(id);
        if (book != null) {
            System.out.println(book.getInfoAboutBookWithComments());
        } else {
            System.out.printf("Book with id '%s' not found%n", id);
        }
    }

    @ShellMethod(value = "Get book by name", key = {"getBookName", "gbn"})
    public void getBookByName(String name){
        Book book;
        book = bookService.getBookByName(name);
        if (book != null){
            System.out.println(book.getInfoAboutBookWithComments());
        } else {
            System.out.printf("Book with name '%s' not found%n", name);
        }
    }

    @ShellMethod(value = "Add book", key = {"addBook"})
    public void addBook(String nameBook, String author, String genre){
        boolean result;
        result = bookService.addBook(nameBook, author, genre);
        if (result){
            System.out.println("Book added successful");
        } else {
            System.out.println("Error book add");
        }
    }

    @ShellMethod(value = "Update book", key = {"updateBook"})
    public void updateBook(String bookId, String name, String authorId, String genreId){
        bookService.updateBook(bookId, name, authorId, genreId);
    }

    @ShellMethod(value = "Delete book", key = {"rm", "deleteBook"})
    public void deleteBook(String id){
        boolean result;
        result = bookService.removeBook(id);
        if (result){
            System.out.println("Book deleted successful");
        } else {
            System.out.println("Error book delete");
        }
    }

    @ShellMethod(value = "Get comment by id", key = {"getComment", "gc"})
    public void getComment(String commentId){
        CommentBook comment;
        comment = commentService.getById(commentId);
        if (comment != null){
            comment.printComment();
        } else {
            System.out.println("Comment not found");
        }
    }

    @ShellMethod(value = "Get comment by book", key = {"getCommentBook", "gcb"})
    public void getCommentByBook(String bookId){
        List<CommentBook> comments;
        comments = commentService.getCommentsForBook(bookId);
        if (comments.size() > 0){
            comments.forEach(CommentBook::printComment);
        } else {
            System.out.println("Comment for book not found");
        }
    }

    @ShellMethod(value = "Get all comments", key = {"listComments", "lC"})
    public void getComments(){
        List<CommentBook> comments;
        comments = commentService.getAll();
        if (comments.size() > 0){
            comments.forEach(CommentBook::printComment);
        } else {
            System.out.println("Comments not found");
        }
    }

    @ShellMethod(value = "Add comment", key = {"addComment"})
    public void addComment(String text, String bookId){
        boolean result;
        result = commentService.addComment(text, bookId);
        if (result){
            System.out.println("Comment added successful");
        } else {
            System.out.println("Error book add");
        }
    }

    @ShellMethod(value = "Update comment", key = {"updateComment", "uC"})
    public void updateComment(String commentId, String text, String bookId){
        commentService.updateComment(commentId, text, bookId);
    }

    @ShellMethod(value = "Delete comment", key = {"rmCom", "deleteComment"})
    public void deleteComment(String id){
        boolean result;
        result = commentService.removeComment(id);
        if (result){
            System.out.println("Comment deleted successful");
        } else {
            System.out.println("Error comment delete");
        }
    }

    @ShellMethod(value = "list authors", key = {"la"})
    public void listAuthors(){
        List<Author> authors;
        authors = authorService.getAll();
        if (authors.size() > 0){
            authors.forEach(author -> System.out.println(author.getInfo()));
        } else {
            System.out.println("Authors not found");
        }
    }

    @ShellMethod(value = "list genres", key = {"lg"})
    public void listGenres(){
        List<Genre> genres;
        genres = genreService.getAll();
        if (genres.size() > 0){
            genres.forEach(genre -> System.out.println(genre.getInfo()));
        } else {
            System.out.println("Authors not found");
        }
    }
}
