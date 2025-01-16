package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Librarian;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LibrarianDAO implements DAO<Librarian> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(Librarian librarian) {
        entityManager.persist(librarian);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Librarian librarian = entityManager.find(Librarian.class, id);
        if (librarian != null) {
            entityManager.remove(librarian);
        }
    }

    @Override
    @Transactional
    public void update(Librarian librarian) {
        entityManager.merge(librarian);
    }

    @Override
    public Librarian getById(int id) {
        return entityManager.find(Librarian.class, id);
    }

    @Override
    public List<Librarian> getAll() {
        return entityManager.createQuery("SELECT l FROM Librarian l", Librarian.class).getResultList();
    }

    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(l) FROM Librarian l", Long.class).getSingleResult();
        return count.intValue();
    }
}