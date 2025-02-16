package com.library.library_management.data.dao;

import com.library.library_management.data.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link User} entities in the database.
 *
 * <p>This class provides an implementation of the {@link DAO} interface, enabling CRUD
 * operations on {@link User} entities. It also includes additional methods for querying
 * specific {@link User} records by properties, such as email.
 *
 * <p>The class relies on JPA's {@link EntityManager} for interacting with the persistence
 * context and is annotated with {@link Repository} to indicate its role in the database layer.
 * Write operations are transactional, ensuring consistency and data integrity.
 */
@Repository
public class PersonDAO implements DAO<User> {

    /**
     * The {@link EntityManager} instance used to interact with the database.
     * It is injected by the persistence context.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Inserts a new {@link User} entity into the database.
     *
     * <p>The provided {@link User} instance is persisted into the database as a new record.
     * This method is executed within a transactional context.
     *
     * @param user the {@link User} entity to be inserted
     */
    @Override
    @Transactional
    public void insert(User user) {
        entityManager.persist(user);
    }

    /**
     * Deletes a {@link User} entity from the database by its ID.
     *
     * <p>If no {@link User} entity exists with the specified ID, no action is performed.
     * This operation is transactional.
     *
     * @param id the ID of the {@link User} entity to be deleted
     */
    @Override
    @Transactional
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    /**
     * Updates an existing {@link User} entity in the database.
     *
     * <p>This method updates the database record corresponding to the provided in-memory
     * {@link User} entity. It operates within a transactional context to ensure atomicity.
     *
     * @param user the {@link User} entity with updated values
     */
    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    /**
     * Retrieves a {@link User} entity by its ID.
     *
     * <p>The method searches the database for a {@link User} entity with the specified ID.
     *
     * @param id the ID of the {@link User} entity to retrieve
     * @return the {@link User} entity if found, or {@code null} if no entity exists with the given ID
     */
    @Override
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Retrieves all {@link User} entities stored in the database.
     *
     * <p>The method executes a JPQL query to retrieve all records of {@link User} entities.
     *
     * @return a {@link List} of all {@link User} entities
     */
    @Override
    public List<User> getAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    /**
     * Counts the total number of {@link User} entities in the database.
     *
     * <p>This method executes a JPQL query to calculate the total record count of {@link User} entities.
     *
     * @return the total number of {@link User} entities as a {@code long} value
     */
    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
        return count.intValue();
    }

    /**
     * Finds a {@link User} entity by its email address.
     *
     * <p>This method executes a JPQL query to search for a {@link User} entity where
     * the email matches the provided string.
     *
     * @param email the email address of the {@link User} entity to retrieve
     * @return the {@link User} entity with the specified email, or {@code null} if no such entity exists
     */
    public User getByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}