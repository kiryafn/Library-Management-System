package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Librarian;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link Librarian} entities in the database.
 *
 * <p>This class provides an implementation of the {@link DAO} interface for the {@link Librarian} entity, enabling
 * basic CRUD operations as well as querying for all librarians or counting records.
 *
 * <p>The {@link EntityManager} is used to perform database interactions, and write operations
 * are wrapped in transactions through the {@link Transactional} annotation to ensure
 * data integrity and consistency.
 */
@Repository
public class LibrarianDAO implements DAO<Librarian> {

    /**
     * The {@link EntityManager} instance used to interact with the persistence context.
     * It is automatically injected via the {@link PersistenceContext} annotation.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Inserts a new {@link Librarian} record into the database.
     *
     * <p>The provided {@link Librarian} entity is persisted into the database. This method
     * is transactional to ensure atomicity and consistency of the operation.
     *
     * @param librarian the {@link Librarian} entity to be inserted
     */
    @Override
    @Transactional
    public void insert(Librarian librarian) {
        entityManager.persist(librarian);
    }

    /**
     * Deletes an existing {@link Librarian} record from the database by its ID.
     *
     * <p>If the entity with the specified ID does not exist in the database, no action
     * is performed. This method operates in a transactional context.
     *
     * @param id the ID of the {@link Librarian} entity to be deleted
     */
    @Override
    @Transactional
    public void delete(int id) {
        Librarian librarian = entityManager.find(Librarian.class, id);
        if (librarian != null) {
            entityManager.remove(librarian);
        }
    }

    /**
     * Updates an existing {@link Librarian} record in the database.
     *
     * <p>This method synchronizes the state of the provided in-memory {@link Librarian}
     * entity with the corresponding database record. Changes are persisted in a
     * transactional way.
     *
     * @param librarian the {@link Librarian} entity with updated values
     */
    @Override
    @Transactional
    public void update(Librarian librarian) {
        entityManager.merge(librarian);
    }

    /**
     * Retrieves a {@link Librarian} entity from the database by its ID.
     *
     * @param id the ID of the {@link Librarian} entity to retrieve
     * @return the {@link Librarian} entity if found, or {@code null} if no entity exists
     *         with the specified ID
     */
    @Override
    public Librarian getById(int id) {
        return entityManager.find(Librarian.class, id);
    }

    /**
     * Retrieves all {@link Librarian} entities from the database.
     *
     * @return a list of {@link Librarian} entities
     */
    @Override
    public List<Librarian> getAll() {
        return entityManager.createQuery("SELECT l FROM Librarian l", Librarian.class).getResultList();
    }

    /**
     * Counts the total number of {@link Librarian} entities in the database.
     *
     * <p>This method executes a JPQL query to calculate the total number of librarians
     * stored in the database.
     *
     * @return the count of {@link Librarian} entities as a {@code long} value
     */
    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(l) FROM Librarian l", Long.class).getSingleResult();
        return count.intValue();
    }
}