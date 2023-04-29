package ru.otus.library.dao;

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
    public boolean addBook(String name, String genre, String authorName, String authorSurname) {
        return false;
    }

    @Override
    public boolean updateBookName(long id, String name) {
        return false;
    }

    @Override
    public Book getBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select * from books b " +
                        "join authors a on b.author=a.id " +
                        "join genres g on b.genre=g.id " +
                        "where b.id = :id", params, new BookMapper());
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books;
        books = namedParameterJdbcOperations.query(
                "select * from books b " +
                        "join authors a on b.author=a.id " +
                        "join genres g on b.genre=g.id",
                new BookMapper());
        return books;
    }

    @Override
    public Author getAuthorById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name, surname from authors where id = :id", params, new AuthorMapper());
    }

    @Override
    public Genre getGenreById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name from genre where id = :id", params, new GenreMapper());
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
            String name = resultSet.getString("author_name");
            String surname = resultSet.getString("author_surname");
            return new Author(id, name, surname);
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("genre_name");
            return new Genre(id, name);
        }
    }
}
