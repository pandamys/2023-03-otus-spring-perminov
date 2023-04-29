package ru.otus.library.domain;

public class Book {
    private long id;

    private String name;

    private Author author;

    private Genre genre;

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

    public String getInfoAboutBook(){
        return String.format("Book: ([id: %d]-[name: %s]-[author: %s]-[genre: %s]", id, name, author.getFullName(), genre.getName());
    }
}
