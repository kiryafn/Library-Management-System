package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Copy;
import com.library.library_management.data.entities.CopyStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link Copy} entities in the database.
 *
 * <p>This class provides CRUD operations for the {@link Copy} entity. It uses JPA's
 * {@link EntityManager} to facilitate database interactions. Additionally, the methods
 * annotated with {@link Transactional} ensure data consistency and transactional integrity
 * for write operations.
 *
 * <p>All read and write operations are implemented according to the patterns defined in the
 * {@link DAO} interface.
 */
@Repository
public class CopyDAO implements DAO<Copy> {

    /**
     * The {@link EntityManager} instance used to interact with the database.
     * It is automatically injected by the persistence context.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Inserts a new {@link Copy} record into the database.
     *
     * @param copy the {@link Copy} entity to be persisted
     */
    @Override
    @Transactional
    public void insert(Copy copy) {
        entityManager.persist(copy);
    }

    /**
     * Deletes a {@link Copy} record from the database by its ID.
     *
     * <p>If no {@link Copy} entity with the specified ID exists, no action is performed.
     *
     * @param id the ID of the {@link Copy} entity to be deleted
     */
    @Override
    @Transactional
    public void delete(int id) {
        Copy copy = entityManager.find(Copy.class, id);
        if (copy != null) {
            entityManager.remove(copy);
        }
    }

    /**
     * Updates an existing {@link Copy} record in the database.
     *
     * <p>This method uses the {@link EntityManager#merge(Object)} method to synchronize the
     * persisted entity in the database with the updated in-memory entity.
     *
     * @param copy the {@link Copy} entity with updated values
     */
    @Override
    @Transactional
    public void update(Copy copy) {
        entityManager.merge(copy);
    }

    /**
     * Retrieves a {@link Copy} entity by its ID.
     *
     * @param id the ID of the {@link Copy} entity to retrieve
     * @return the {@link Copy} entity if found, or {@code null} if no entity exists with the given ID
     */
    @Override
    public Copy getById(int id) {
        return entityManager.find(Copy.class, id);
    }

    /**
     * Retrieves all {@link Copy} entities from the database.
     *
     * <p>This method performs a JPQL query to fetch all {@link Copy} entities stored in the database.
     *
     * @return a list of all {@link Copy} entities
     */
    @Override
    public List<Copy> getAll() {
        return entityManager.createQuery("SELECT c FROM Copy c", Copy.class).getResultList();
    }

    /**
     * Counts the total number of {@link Copy} entities in the database.
     *
     * <p>A JPQL query is executed to calculate the total count of {@link Copy} entities.
     *
     * @return the count of {@link Copy} entities as a {@code long} value
     */
    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(c) FROM Copy c", Long.class).getSingleResult();
        return count.intValue();
    }

    public List<Copy> getAllAvailable() {
        String available = CopyStatus.Available.getName();
        return entityManager.createQuery("SELECT c FROM Copy c WHERE c.status = :status", Copy.class)
                .setParameter("status", available)
                .getResultList();
    }
}