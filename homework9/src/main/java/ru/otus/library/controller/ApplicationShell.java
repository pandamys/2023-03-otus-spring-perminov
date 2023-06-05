package ru.otus.library.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;

import java.util.List;

@ShellComponent
public class ApplicationShell {
    private final BookService bookService;

    private final CommentService commentService;

    public ApplicationShell(BookService bookService,
                            CommentService commentService){
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @ShellMethod(value = "Get all books", key = {"listBooks", "lb"})
    public void getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        books.forEach(book -> System.out.println(book.getInfoAboutBook()));
    }

    @ShellMethod(value = "Get book", key = {"getBook", "gb"})
    public void getBook(long id){
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
    public void addBook(String nameBook, Long author, Long genre){
        boolean result;
        result = bookService.addBook(nameBook, author, genre);
        if (result){
            System.out.println("Book added successful");
        } else {
            System.out.println("Error book add");
        }
    }

    @ShellMethod(value = "Update book", key = {"updateBook"})
    public void updateBook(long bookId, String name, long authorId, long genreId){
        bookService.updateBook(bookId, name, authorId, genreId);
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

    @ShellMethod(value = "Get comment by id", key = {"getComment", "gc"})
    public void getComment(long commentId){
        CommentBook comment;
        comment = commentService.getById(commentId);
        if (comment != null){
            comment.printComment();
        } else {
            System.out.println("Comment not found");
        }
    }

    @ShellMethod(value = "Get comment by book", key = {"getCommentBook", "gcb"})
    public void getCommentByBook(long bookId){
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
    public void addComment(String text, long bookId){
        boolean result;
        result = commentService.addComment(text, bookId);
        if (result){
            System.out.println("Comment added successful");
        } else {
            System.out.println("Error book add");
        }
    }

    @ShellMethod(value = "Update comment", key = {"updateComment", "uC"})
    public void updateComment(long commentId, String text, long bookId){
        commentService.updateComment(commentId, text, bookId);
    }

    @ShellMethod(value = "Delete comment", key = {"rmCom", "deleteComment"})
    public void deleteComment(long id){
        boolean result;
        result = commentService.removeComment(id);
        if (result){
            System.out.println("Comment deleted successful");
        } else {
            System.out.println("Error comment delete");
        }
    }
}
