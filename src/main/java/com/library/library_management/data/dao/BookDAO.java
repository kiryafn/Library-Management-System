package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Book;
import com.library.library_management.data.entities.CopyStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAO implements DAO<Book> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(Book book) {
        entityManager.persist(book);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Book book = entityManager.find(Book.class, id);
        if (book != null) {
            entityManager.remove(book);
        }
    }

    @Override
    @Transactional
    public void update(Book book) {
        entityManager.merge(book);
    }

    @Override
    public Book getById(int id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(b) FROM Book b", Long.class).getSingleResult();
        return count.intValue();
    }

    public List<Object[]> findBooksWithAvailableCopies() {
        return entityManager.createQuery(
                        "SELECT b, COUNT(c) AS COPIES " +
                                "FROM Book b " +
                                "JOIN b.copies c " +
                                "WHERE c.status = :status " +
                                "GROUP BY b", Object[].class)
                .setParameter("status", CopyStatus.Available.getName())
                .getResultList();
    }
}