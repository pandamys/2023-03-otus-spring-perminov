package ru.otus.library.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;
import java.util.List;

@Repository
public class CommentBookDaoJpa implements CommentBookDao {
    @PersistenceContext
    private EntityManager em;

    public CommentBookDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public CommentBook getById(long id) {
        return em.find(CommentBook.class, id);
    }

    @Override
    public List<CommentBook> getComments(Book book) {
        TypedQuery<CommentBook> query;
        query = em.createQuery("select cb from CommentBook cb where cb.id = :id", CommentBook.class);
        query.setParameter("id", book.getId());
        return query.getResultList();
    }

    @Override
    public List<CommentBook> getAll(){
        TypedQuery<CommentBook> query;
        query = em.createQuery("select cb from CommentBook cb", CommentBook.class);
        return query.getResultList();
    }

    @Override
    public CommentBook insertCommentBook(CommentBook comment) {
        if (comment.getId() <= 0){
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void updateCommentBook(CommentBook comment) {
        Query query;
        query = em.createQuery("update CommentBook cb " +
                "set cb.text = :text," +
                "cb.book = :book " +
                "where cb.id = :id");
        query.setParameter("text", comment.getText());
        query.setParameter("book", comment.getBook());
        query.setParameter("id", comment.getId());
    }

    @Override
    public void deleteCommentBook(long id) {
        Query query;
        query = em.createQuery("delete " +
                "from CommentBook cb " +
                "where cb.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
