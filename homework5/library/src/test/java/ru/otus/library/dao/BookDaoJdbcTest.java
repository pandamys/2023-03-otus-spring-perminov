package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDao;

    @Test
    public void testGetBookById(){
        Author author;
        Genre genre;
        Book expectedBook, actualBook;

        author = new Author(2, "Tester", "Testerov");
        genre = new Genre(1, "Test_Genre");
        expectedBook = new Book(2, "TestBook2", author, genre);

        actualBook = bookDao.getBookById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    public void testAddBook(){
        Author author;
        Genre genre;
        Book expectedBook, actualBook;

        author = new Author(2, "Ivan", "Ivanov");
        genre = new Genre(1, "Test_Genre");
        expectedBook = new Book(3, "TestBook3", author, genre);

        bookDao.insertBook(expectedBook);
        actualBook = bookDao.getBookById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }
}
