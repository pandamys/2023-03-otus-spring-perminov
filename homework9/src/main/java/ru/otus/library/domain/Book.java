package ru.otus.library.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.otus.library.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="genre_id")
    private Genre genre;

    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "book")
    @Column(name="comment_id")
    private List<CommentBook> comments;

    public Book() {

    }

    public Book(String name, Author author, Genre genre){
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book(long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = new ArrayList<>();
    }

    public Book(BookDto bookDto){
        this.id = bookDto.getId();
        this.name = bookDto.getName();
        this.author = bookDto.getAuthor();
        this.genre = bookDto.getGenre();
        this.comments = bookDto.getComments();
    }

    public long getId() {
        return id;
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

    public void setComments(CommentBook comment) {
        if (comments != null) {
            this.comments.add(comment);
        } else {
            comments = new ArrayList<>();
            comments.add(comment);
        }
    }

    public String getInfoAboutBook(){
        return String.format("Book: ([id: %d]-[name: %s]-[author: %s]-[genre: %s]",
                id, name, author.getFullName(), genre.getName());
    }

    public String getInfoAboutBookWithComments(){
        return String.format("Book: ([id: %d]-[name: %s]-[author: %s]-[genre: %s]-[comments: %s]",
                id, name, author.getFullName(), genre.getName(), commentsBookToText());
    }

    private String commentsBookToText(){
        StringBuilder sb = new StringBuilder();
        for (CommentBook comment: comments){
            sb.append(comment.getText()).append(";");
        }
        return sb.toString();
    }
}
