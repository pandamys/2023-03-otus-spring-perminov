package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import(BookDaoJpa.class)
public class BookDaoJpaTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookDaoJpa bookDao;

    @DisplayName("Test get book by id")
    @Test
    public void testGetBookById(){
        Book expectedBook, actualBook;
        expectedBook = em.find(Book.class, 1);
        actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Test add new book")
    @Test
    public void testAddBook(){
        Author author;
        Genre genre;
        Book expectedBook, actualBook;

        author = em.find(Author.class, 1);
        genre = em.find(Genre.class, 1);
        expectedBook = new Book(3, "TestBook3", author, genre);

        bookDao.save(expectedBook);
        actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).isNotNull().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Test delete book")
    @Test
    public void testDeleteBook(){
        long workId = 2;
        Book expectedBook = em.find(Book.class, workId);

        bookDao.deleteById(workId);
        em.detach(expectedBook);

        assertNull(bookDao.getById(workId));
    }

    @DisplayName("Test update name book")
    @Test
    public void testUpdateNameBook(){
        Book expectedBook, actualBook;
        String newBookName = "Test book new";
        long workId = 1;

        expectedBook = em.find(Book.class, workId);
        expectedBook.setName(newBookName);
        em.detach(expectedBook);
        bookDao.save(expectedBook);

        actualBook = em.find(Book.class, workId);
        assertEquals(newBookName, actualBook.getName());
    }
}
