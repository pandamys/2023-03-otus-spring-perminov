package ru.otus.library.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;
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
        EntityGraph<?> entityGraph;
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", getEntityGraph());
        return em.find(CommentBook.class, id, properties);
    }

    @Override
    public List<CommentBook> getAll(Book book) {
        TypedQuery<CommentBook> query;
        query = em.createQuery("select cb from CommentBook cb where cb.id = :id", CommentBook.class);
        query.setParameter("id", book.getId());
        query.setHint("javax.persistence.fetchgraph", getEntityGraph());
        return query.getResultList();
    }

    @Override
    public List<CommentBook> getAll(){
        TypedQuery<CommentBook> query;
        query = em.createQuery("select cb from CommentBook cb", CommentBook.class);
        query.setHint("javax.persistence.fetchgraph", getEntityGraph());
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
        Query query;
        query = em.createQuery("delete " +
                "from CommentBook cb " +
                "where cb.id = :id");
        query.setParameter("id", comment.getId());
        query.executeUpdate();
    }

    private EntityGraph<?> getEntityGraph() {
        EntityGraph<?> entityGraph;
        entityGraph = em.getEntityGraph("lib-comment-book-eg");
        return entityGraph;
    }
}
