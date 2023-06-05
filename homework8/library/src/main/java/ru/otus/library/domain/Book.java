package ru.otus.library.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "books")
public class Book {
    @Id
    private String id;

    private String name;

    @DBRef
    private Author author;

    @DBRef
    private Genre genre;

    @DBRef
    private List<CommentBook> comments;

    public Book() {

    }

    public Book(String name, Author author, Genre genre){
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book(String id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = new ArrayList<>();
    }

    public String getId() {
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

    public void setComments(List<CommentBook> comments) {
        this.comments = comments;
    }

    public String getInfoAboutBook(){
        return String.format("Book: ([id: %s]-[name: %s]-[author: %s]-[genre: %s]",
                id, name, author.getFullName(), genre.getName());
    }

    public String getInfoAboutBookWithComments(){
        return String.format("Book: ([id: %s]-[name: %s]-[author: %s]-[genre: %s]-[comments: %s]",
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
