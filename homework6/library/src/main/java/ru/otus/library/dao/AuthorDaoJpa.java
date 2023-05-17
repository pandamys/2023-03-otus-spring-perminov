package ru.otus.library.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

@Repository
public class AuthorDaoJpa implements AuthorDao{
    @PersistenceContext
    private EntityManager em;

    public AuthorDaoJpa(EntityManager em){
        this.em = em;
    }

    @Override
    public Author getById(long id) {
        return em.find(Author.class, id);
    }
}
