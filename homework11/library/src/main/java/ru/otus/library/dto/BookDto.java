package ru.otus.library.dto;

import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.mapper.CommentBookDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BookDto {
    private String id;

    private String name;

    private Author author;

    private Genre genre;

    private List<CommentBookDto> comments;

    public BookDto() {

    }

    public BookDto(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        if (book.getComments() != null) {
            this.comments = book.getComments().stream().map(CommentBookDtoMapper::toDto).collect(Collectors.toList());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorId() {
        return author.getId();
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getGenreId() {
        return genre.getId();
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<CommentBookDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentBookDto> comments) {
        this.comments = comments;
    }
}
