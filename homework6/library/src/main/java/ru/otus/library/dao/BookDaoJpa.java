package ru.otus.library.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJpa implements BookDao {
    @PersistenceContext
    private final EntityManager em;

    public BookDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", getExtendEntityGraph());
        return em.find(Book.class, id, properties);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query;
        query = em.createQuery("select b from Book b left join fetch b.comments",
                Book.class);
        query.setHint("javax.persistence.fetchgraph", getEntityGraph());
        return query.getResultList();
    }

    @Override
    public Book getByName(String name) {
        TypedQuery<Book> query;
        query = em.createQuery("select b from Book b " +
                "where b.name = :name", Book.class);
        query.setParameter("name", name);
        query.setHint("javax.persistence.fetchgraph", getEntityGraph());
        return query.getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0){
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void remove(Book book) {
        if (em.contains(book)) {
            em.remove(book);
        }
    }

    private EntityGraph<?> getEntityGraph() {
        EntityGraph<?> entityGraph;
        entityGraph = em.getEntityGraph("lib-book-author-genre-eg");
        return entityGraph;
    }

    private EntityGraph<?> getExtendEntityGraph() {
        EntityGraph<?> entityGraph;
        entityGraph = em.getEntityGraph("lib-book-author-genre-comments-eg");
        return entityGraph;
    }
}
