package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Book;
import com.library.library_management.data.entities.CopyStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link Book} entities in the database.
 *
 * <p>This class provides CRUD operations, as well as additional queries specific to the {@link Book} entity.
 * It uses JPA's {@link EntityManager} for interacting with the database.
 *
 * <p>All database operations in this class are annotated with {@link Transactional},
 * ensuring that changes are committed or rolled back automatically.
 */
@Repository
public class BookDAO implements DAO<Book> {

    /**
     * The {@link EntityManager} instance used for database operations.
     * It is injected by the persistence context.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Inserts a new {@link Book} record into the database.
     *
     * @param book the {@link Book} entity to be inserted
     */
    @Override
    @Transactional
    public void insert(Book book) {
        entityManager.persist(book);
    }

    /**
     * Deletes a {@link Book} record based on the given ID.
     *
     * <p>If the book with the specified ID does not exist, no action is taken.
     *
     * @param id the ID of the {@link Book} entity to be deleted
     */
    @Override
    @Transactional
    public void delete(int id) {
        Book book = entityManager.find(Book.class, id);
        if (book != null) {
            entityManager.remove(book);
        }
    }

    /**
     * Updates an existing {@link Book} record in the database.
     *
     * @param book the {@link Book} entity with updated values
     */
    @Override
    @Transactional
    public void update(Book book) {
        entityManager.merge(book);
    }

    /**
     * Retrieves a {@link Book} entity by its ID.
     *
     * @param id the ID of the {@link Book} entity to retrieve
     * @return the {@link Book} entity if found, or {@code null} otherwise
     */
    @Override
    public Book getById(int id) {
        return entityManager.find(Book.class, id);
    }

    /**
     * Retrieves all {@link Book} entities from the database.
     *
     * @return a list of all {@link Book} entities
     */
    @Override
    public List<Book> getAll() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    /**
     * Counts the total number of {@link Book} entities in the database.
     *
     * @return the total count of {@link Book} entities
     */
    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(b) FROM Book b", Long.class).getSingleResult();
        return count.intValue();
    }

    /**
     * Finds all {@link Book} entities with available copies.
     *
     * <p>This query selects books and counts their associated copies that have a status
     * of {@link CopyStatus#Available}.
     *
     * @return a list of arrays where each array contains:
     *         <ul>
     *           <li>the {@link Book} entity</li>
     *           <li>the number of available copies for that {@link Book}</li>
     *         </ul>
     */
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