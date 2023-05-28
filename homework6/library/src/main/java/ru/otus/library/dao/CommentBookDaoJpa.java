package ru.otus.library.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<CommentBook> getAll(Book book) {
        TypedQuery<CommentBook> query;
        query = em.createQuery(
                "select cb from CommentBook cb left join fetch cb.book where cb.book = :id", CommentBook.class);
        query.setParameter("id", book);
        return query.getResultList();
    }

    @Override
    public List<CommentBook> getAll(){
        TypedQuery<CommentBook> query;
        query = em.createQuery("select cb from CommentBook cb left join fetch cb.book ", CommentBook.class);
        return query.getResultList();
    }

    @Override
    public CommentBook save(CommentBook comment) {
        if (comment.getId() <= 0){
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void remove(CommentBook comment) {
        if (em.contains(comment)){
            em.remove(comment);
        }
    }
}
