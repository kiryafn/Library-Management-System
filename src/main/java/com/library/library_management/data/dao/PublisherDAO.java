package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Publisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link Publisher} entities in the database.
 *
 * <p>This class implements the {@link DAO} interface, providing basic CRUD operations
 * for the {@link Publisher} entity. It also includes methods to retrieve a list of all
 * publishers and count the total number of publisher records in the database.
 *
 * <p>The class relies on JPA's {@link EntityManager} for database interactions, and the
 * {@link Transactional} annotation ensures that write operations (insert, update, and delete)
 * are executed within a transactional context.
 */
@Repository
public class PublisherDAO implements DAO<Publisher> {

    /**
     * The {@link EntityManager} instance used to interact with the persistence context.
     * This field is injected automatically by the application framework.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Inserts a new {@link Publisher} record into the database.
     *
     * <p>The provided {@link Publisher} instance is persisted as a new record in the database.
     * This method runs within a transactional context to ensure operation atomicity.
     *
     * @param publisher the {@link Publisher} entity to insert
     */
    @Override
    @Transactional
    public void insert(Publisher publisher) {
        entityManager.persist(publisher);
    }

    /**
     * Deletes a {@link Publisher} record from the database by its ID.
     *
     * <p>If no {@link Publisher} entity with the specified ID exists, no action is taken.
     * The operation is wrapped in a transactional context to ensure that it is either
     * fully committed or rolled back in case of an error.
     *
     * @param id the ID of the {@link Publisher} entity to delete
     */
    @Override
    @Transactional //method should be done in transaction (but with annotation its done automatically)(auto commit or rollback)
    public void delete(int id) {
        Publisher publisher = entityManager.find(Publisher.class, id);
        if (publisher != null) {
            entityManager.remove(publisher);
        }
    }

    /**
     * Updates an existing {@link Publisher} record in the database.
     *
     * <p>This method synchronizes the state of the provided in-memory {@link Publisher} entity
     * with the corresponding database record. It operates within a transactional context.
     *
     * @param publisher the {@link Publisher} entity with updated values
     */
    @Override
    @Transactional
    public void update(Publisher publisher) {
        entityManager.merge(publisher);
    }

    /**
     * Retrieves a {@link Publisher} entity from the database by its ID.
     *
     * <p>The method searches the database for a {@link Publisher} entity with the specified ID.
     *
     * @param id the ID of the {@link Publisher} entity to retrieve
     * @return the {@link Publisher} entity if found, or {@code null} if no entity exists with the given ID
     */
    @Override
    public Publisher getById(int id) {
        return entityManager.find(Publisher.class, id);
    }

    /**
     * Retrieves all {@link Publisher} entities from the database.
     *
     * <p>The method executes a JPQL query to fetch all {@link Publisher} records from the database.
     *
     * @return a list of all {@link Publisher} entities
     */
    @Override
    public List<Publisher> getAll() {
        return entityManager.createQuery("SELECT p FROM Publisher p", Publisher.class).getResultList();
    }

    /**
     * Counts the total number of {@link Publisher} entities in the database.
     *
     * <p>A JPQL query is executed to calculate the total number of publisher records.
     *
     * @return the total count of {@link Publisher} entities as a {@code long} value
     */
    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(p) FROM Publisher p", Long.class).getSingleResult();
        return count.intValue();
    }
}