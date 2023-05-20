package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями к книгам")
@DataJpaTest
@Import(CommentBookDaoJpa.class)
public class CommentBookDaoJpaTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentBookDao commentBookDao;

    @DisplayName("Test get comment by id")
    @Test
    public void testGetCommentById(){
        Book book;
        CommentBook expectedComment, actualComment;

        expectedComment = em.find(CommentBook.class, 1);

        actualComment = commentBookDao.getById(expectedComment.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("Test add new comment")
    @Test
    public void testAddBook(){
        Book book;
        CommentBook expectedComment, actualComment;

        book = em.find(Book.class, 2);
        expectedComment = new CommentBook("Test comments", book);

        commentBookDao.save(expectedComment);
        actualComment = commentBookDao.getById(expectedComment.getId());
        assertThat(actualComment).isNotNull().usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("Test update comment")
    @Test
    public void testUpdateNameBook(){
        CommentBook expectedComment, actualComment;
        String newTextComment = "Test comment updated";
        long workId = 1;

        expectedComment = em.find(CommentBook.class, workId);
        expectedComment.setText(newTextComment);
        em.detach(expectedComment);
        commentBookDao.save(expectedComment);

        actualComment = em.find(CommentBook.class, workId);
        assertEquals(newTextComment, actualComment.getText());
    }

    @DisplayName("Test delete comment")
    @Test
    public void testDeleteBook(){
        long workId = 2;
        Book expectedBook = em.find(Book.class, workId);

        commentBookDao.deleteById(workId);
        em.detach(expectedBook);

        assertNull(commentBookDao.getById(workId));
    }
}
