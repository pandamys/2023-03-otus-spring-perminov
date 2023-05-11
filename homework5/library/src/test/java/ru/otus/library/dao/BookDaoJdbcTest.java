package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDao;

    @DisplayName("Test get book by id")
    @Test
    public void testGetBookById(){
        Author author;
        Genre genre;
        Book expectedBook, actualBook;

        author = new Author(2, "Tester", "Testerov");
        genre = new Genre(1, "Test_Genre");
        expectedBook = new Book(2, "TestBook2", author, genre);

        actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Test add new book")
    @Test
    public void testAddBook(){
        Author author;
        Genre genre;
        Book expectedBook, actualBook;

        author = new Author(1, "Test", "Testov");
        genre = new Genre(1, "Test_Genre");
        expectedBook = new Book(3, "TestBook3", author, genre);

        bookDao.insert(expectedBook);
        actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Test delete book")
    @Test
    public void testDeleteBook(){
        long workId = 2;

        assertThatCode(() -> bookDao.getById(workId))
                .doesNotThrowAnyException();

        bookDao.deleteById(workId);

        assertNull(bookDao.getById(workId));
    }

    @DisplayName("Test update name book")
    @Test
    public void testUpdateNameBook(){
        Author author;
        Genre genre;
        Book expectedBook, actualBook;
        String newBookName = "Test book new";

        author = new Author(1, "Test", "Testov");
        genre = new Genre(1, "Test_Genre");
        expectedBook = new Book(1, newBookName, author, genre);

        bookDao.update(expectedBook);
        actualBook = bookDao.getById(expectedBook.getId());
        assertEquals(actualBook.getName(), newBookName);
    }
}
