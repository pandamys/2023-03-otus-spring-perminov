package ru.otus.library.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations){
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Book getBookById(long id) {
        Book book;
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            book = namedParameterJdbcOperations.queryForObject(
                    "select b.*, a.name author_name, a.surname author_surname, g.name genre_name from books b " +
                            "join authors a on b.author=a.id " +
                            "join genres g on b.genre=g.id " +
                            "where b.id = :id", params, new BookMapper());
        } catch (EmptyResultDataAccessException e){
            book = null;
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books;
        books = namedParameterJdbcOperations.query(
                "select b.*, a.name author_name, a.surname author_surname, g.name genre_name from books b " +
                        "join authors a on b.author=a.id " +
                        "join genres g on b.genre=g.id",
                new BookMapper());
        return books;
    }

    @Override
    public void insertBook(Book book) {
        namedParameterJdbcOperations.update(
                "insert into books (name, author, genre) values (:name, :author, :genre)",
                Map.of("name", book.getName(),
                        "author", book.getAuthor().getId(),
                        "genre", book.getGenre().getId()));
    }

    @Override
    public void updateBook(long id, Map<String, Object> params) {

    }

    @Override
    public void deleteBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
    }

    @Override
    public Author getAuthorById(long id) {
        Author author;
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            author = namedParameterJdbcOperations.queryForObject(
                    "select id, name, surname from authors where id = :id", params, new AuthorMapper());
        } catch (EmptyResultDataAccessException e){
            author = null;
        }

        return author;
    }

    @Override
    public Genre getGenreById(long id) {
        Genre genre;
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            genre = namedParameterJdbcOperations.queryForObject(
                    "select id, name from genres where id = :id", params, new GenreMapper());
        } catch (EmptyResultDataAccessException e){
            genre = null;
        }
        return genre;
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long authorId = resultSet.getLong("author");
            String authorName = resultSet.getString("author_name");
            String authorSurname = resultSet.getString("author_surname");
            long genreId = resultSet.getLong("genre");
            String genreName = resultSet.getString("genre_name");
            Author author = new Author(authorId, authorName, authorSurname);
            Genre genre = new Genre(genreId, genreName);
            return new Book(id, name, author, genre);
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            return new Author(id, name, surname);
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
