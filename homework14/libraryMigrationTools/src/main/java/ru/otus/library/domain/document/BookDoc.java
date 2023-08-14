package ru.otus.library.domain.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "books")
public class BookDoc {
    @Id
    private String id;

    private String name;

    @DBRef
    private AuthorDoc author;

    @DBRef
    private GenreDoc genre;

    @DBRef
    private List<CommentBookDoc> comments;

    private Long previousId;

    public BookDoc() {

    }

    public BookDoc(String name, AuthorDoc author, GenreDoc genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public BookDoc(String name, AuthorDoc author, GenreDoc genre, Long previousId) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.previousId = previousId;
    }

    public BookDoc(String id, String name, AuthorDoc author, GenreDoc genre) {
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

    public AuthorDoc getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return author.getId();
    }

    public void setAuthor(AuthorDoc author) {
        this.author = author;
    }

    public GenreDoc getGenre() {
        return genre;
    }

    public String getGenreId() {
        return genre.getId();
    }

    public void setGenre(GenreDoc genre) {
        this.genre = genre;
    }

    public List<CommentBookDoc> getComments() {
        return comments;
    }

    public void setComments(CommentBookDoc comment) {
        if (comments != null) {
            this.comments.add(comment);
        } else {
            comments = new ArrayList<>();
            comments.add(comment);
        }
    }

    public void setComments(List<CommentBookDoc> comments) {
        this.comments = comments;
    }

    public String getInfoAboutBook() {
        return String.format("Book: ([id: %s]-[name: %s]-[author: %s]-[genre: %s]",
                id, name, author.getFullName(), genre.getName());
    }

    public String getInfoAboutBookWithComments() {
        return String.format("Book: ([id: %s]-[name: %s]-[author: %s]-[genre: %s]-[comments: %s]",
                id, name, author.getFullName(), genre.getName(), commentsBookToText());
    }

    private String commentsBookToText() {
        StringBuilder sb = new StringBuilder();
        for (CommentBookDoc comment: comments) {
            sb.append(comment.getText()).append(";");
        }
        return sb.toString();
    }
}
