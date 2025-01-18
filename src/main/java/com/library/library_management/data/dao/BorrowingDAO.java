package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Borrowing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BorrowingDAO implements DAO<Borrowing> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(Borrowing borrowing) {
        entityManager.persist(borrowing);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Borrowing borrowing = entityManager.find(Borrowing.class, id);
        if (borrowing != null) {
            entityManager.remove(borrowing);
        }
    }

    @Override
    @Transactional
    public void update(Borrowing borrowing) {
        entityManager.merge(borrowing);
    }

    @Override
    public Borrowing getById(int id) {
        return entityManager.find(Borrowing.class, id);
    }

    @Override
    public List<Borrowing> getAll() {
        return entityManager.createQuery("SELECT b FROM Borrowing b", Borrowing.class).getResultList();
    }

    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(b) FROM Borrowing b", Long.class).getSingleResult();
        return count.intValue();
    }

    public List<Borrowing> getByUserId(String personId) {
        try {
            Integer parsedId = Integer.parseInt(personId);

            return entityManager.createQuery("SELECT b FROM Borrowing b WHERE b.person.id = :personId", Borrowing.class)
                    .setParameter("personId", parsedId)
                    .getResultList();
        } catch (NumberFormatException e) {
            return List.of();
        }
    }
}