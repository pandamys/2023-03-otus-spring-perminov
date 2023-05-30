package ru.otus.library.dao;

import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;

import java.util.List;

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
        CommentBook expectedComment, actualComment;

        expectedComment = em.find(CommentBook.class, 1);

        actualComment = commentBookDao.getById(expectedComment.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("Test get all comments")
    @Test
    public void testGetAllComments(){
        List<CommentBook> actualComments, expectedComments;
        actualComments = commentBookDao.getAll();
        expectedComments = em.getEntityManager()
                .createQuery("select cb from CommentBook cb", CommentBook.class)
                .getResultList();
        assertThat(actualComments).containsOnlyOnceElementsOf(expectedComments);
    }

    @DisplayName("Test comments for book")
    @Test
    public void testCommentsForBook(){
        Book book1, book2;
        book1 = em.find(Book.class, 1);
        book2 = em.find(Book.class, 2);
        List<CommentBook> actualComments, expectedComments1, expectedComments2;
        TypedQuery<CommentBook> query;

        query = em.getEntityManager()
                .createQuery("select cb from CommentBook cb where cb.book = :id", CommentBook.class);
        query.setParameter("id", book1);
        expectedComments1 = query.getResultList();
        actualComments = commentBookDao.getAll(book1);

        assertThat(actualComments).containsOnlyOnceElementsOf(expectedComments1);

        query.setParameter("id", book2);
        expectedComments2 = query.getResultList();
        actualComments = commentBookDao.getAll(book2);

        assertThat(actualComments).containsOnlyOnceElementsOf(expectedComments2);
    }

    @DisplayName("Test add new comment")
    @Test
    public void testAddComment(){
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
    public void testUpdateNameComment(){
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
    public void testDeleteComment(){
        long workId = 1;
        CommentBook expectedComment, actualComment;
        expectedComment = em.find(CommentBook.class, workId);

        commentBookDao.remove(expectedComment);
        em.flush();
        actualComment = em.find(CommentBook.class, workId);


        assertNull(actualComment);
    }
}
