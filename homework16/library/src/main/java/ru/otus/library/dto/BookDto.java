package ru.otus.library.dto;

import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;
import ru.otus.library.domain.Genre;

import java.util.List;

public class BookDto {
    private long id;

    private String name;

    private Author author;

    private Genre genre;

    private List<CommentBook> comments;

    public BookDto(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        this.comments = book.getComments();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<CommentBook> getComments() {
        return comments;
    }

    public void setComments(List<CommentBook> comments) {
        this.comments = comments;
    }
}
