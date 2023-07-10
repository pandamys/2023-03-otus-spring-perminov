package ru.otus.library.dto;

public class BookNewAndChangeDto {
    private Long id;

    private String name;

    private Long authorId;

    private Long genreId;

    public BookNewAndChangeDto(){

    }

    public BookNewAndChangeDto(String name, Long authorId, Long genreId){
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}
