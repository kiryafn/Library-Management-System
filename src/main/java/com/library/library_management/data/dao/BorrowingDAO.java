package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Borrowing;
import com.library.library_management.data.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link Borrowing} entities in the database.
 *
 * <p>This class provides CRUD operations along with additional queries specific
 * to the {@link Borrowing} entity. It uses JPA's {@link EntityManager} for all
 * database interactions.
 *
 * <p>Database operations that modify data are annotated with {@link Transactional},
 * ensuring they are executed within a transaction context to support data integrity
 * and consistency.
 */
@Repository
public class BorrowingDAO implements DAO<Borrowing> {

    /**
     * The {@link EntityManager} used for interacting with the persistence context.
     * This field is injected automatically by the persistence framework.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Inserts a new {@link Borrowing} record into the database.
     *
     * @param borrowing the {@link Borrowing} entity to be persisted
     */
    @Override
    @Transactional
    public void insert(Borrowing borrowing) {
        entityManager.persist(borrowing);
    }

    /**
     * Deletes a {@link Borrowing} record from the database by its ID.
     *
     * <p>If the specified {@link Borrowing} entity does not exist, no action is taken.
     *
     * @param id the ID of the {@link Borrowing} entity to be deleted
     */
    @Override
    @Transactional
    public void delete(int id) {
        Borrowing borrowing = entityManager.find(Borrowing.class, id);
        if (borrowing != null) {
            entityManager.remove(borrowing);
        }
    }

    /**
     * Updates an existing {@link Borrowing} record in the database.
     *
     * <p>The {@link Borrowing} entity must already exist in the database for this
     * method to succeed.
     *
     * @param borrowing the {@link Borrowing} entity with updated values
     */
    @Override
    @Transactional
    public void update(Borrowing borrowing) {
        entityManager.merge(borrowing);
    }

    /**
     * Retrieves a {@link Borrowing} entity by its ID.
     *
     * @param id the ID of the {@link Borrowing} entity to be retrieved
     * @return the {@link Borrowing} entity if found, or {@code null} otherwise
     */
    @Override
    public Borrowing getById(int id) {
        return entityManager.find(Borrowing.class, id);
    }

    /**
     * Retrieves all {@link Borrowing} entities from the database.
     *
     * @return a list of all {@link Borrowing} entities
     */
    @Override
    public List<Borrowing> getAll() {
        return entityManager.createQuery("SELECT b FROM Borrowing b", Borrowing.class).getResultList();
    }

    /**
     * Counts the total number of {@link Borrowing} entities in the database.
     *
     * @return the total count of {@link Borrowing} entities
     */
    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(b) FROM Borrowing b", Long.class).getSingleResult();
        return count.intValue();
    }

    /**
     * Retrieves a list of {@link Borrowing} entities associated with a specific user ID.
     *
     * <p>The user ID is associated with the {@link User}
     * entity that initiated the borrowing.
     *
     * <p>Handles invalid user IDs gracefully by returning an empty list if
     * the ID cannot be parsed to an integer.
     *
     * @param personId the ID of the person as a {@link String}
     * @return a list of {@link Borrowing} entities linked to the specified user ID,
     *         or an empty list if the ID is invalid or no records are found
     */
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