package ru.otus.library.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;

import java.util.List;

@Repository
public class BookDaoJpa implements BookDao {
    @PersistenceContext
    private final EntityManager em;

    public BookDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query;
        EntityGraph<?> entityGraph = em.getEntityGraph("lib-book-author-genre-eg");
        query = em.createQuery("select b from Book b",
                Book.class);
        query.setHint("javax.persistence.loadgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book getByName(String name) {
        TypedQuery<Book> query;
        query = em.createQuery(
                "select b from Book b " +
                        "join fetch b.comments " +
                        "where b.name = :name",
                        Book.class);
        query.setParameter("name", name);
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
}
