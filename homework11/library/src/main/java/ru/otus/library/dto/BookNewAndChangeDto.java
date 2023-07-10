package ru.otus.library.dto;

public class BookNewAndChangeDto {
    private String id;

    private String name;

    private String authorId;

    private String genreId;

    public BookNewAndChangeDto() {

    }

    public BookNewAndChangeDto(String id, String name, String authorId, String genreId) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public BookNewAndChangeDto(String name, String authorId, String genreId) {
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;
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

    public String getAuthorId() {
        return authorId;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }
}
