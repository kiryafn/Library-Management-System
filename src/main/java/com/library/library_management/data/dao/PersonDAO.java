package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link Person} entities in the database.
 *
 * <p>This class provides an implementation of the {@link DAO} interface, enabling CRUD
 * operations on {@link Person} entities. It also includes additional methods for querying
 * specific {@link Person} records by properties, such as email.
 *
 * <p>The class relies on JPA's {@link EntityManager} for interacting with the persistence
 * context and is annotated with {@link Repository} to indicate its role in the database layer.
 * Write operations are transactional, ensuring consistency and data integrity.
 */
@Repository
public class PersonDAO implements DAO<Person> {

    /**
     * The {@link EntityManager} instance used to interact with the database.
     * It is injected by the persistence context.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Inserts a new {@link Person} entity into the database.
     *
     * <p>The provided {@link Person} instance is persisted into the database as a new record.
     * This method is executed within a transactional context.
     *
     * @param person the {@link Person} entity to be inserted
     */
    @Override
    @Transactional
    public void insert(Person person) {
        entityManager.persist(person);
    }

    /**
     * Deletes a {@link Person} entity from the database by its ID.
     *
     * <p>If no {@link Person} entity exists with the specified ID, no action is performed.
     * This operation is transactional.
     *
     * @param id the ID of the {@link Person} entity to be deleted
     */
    @Override
    @Transactional
    public void delete(int id) {
        Person person = entityManager.find(Person.class, id);
        if (person != null) {
            entityManager.remove(person);
        }
    }

    /**
     * Updates an existing {@link Person} entity in the database.
     *
     * <p>This method updates the database record corresponding to the provided in-memory
     * {@link Person} entity. It operates within a transactional context to ensure atomicity.
     *
     * @param person the {@link Person} entity with updated values
     */
    @Override
    @Transactional
    public void update(Person person) {
        entityManager.merge(person);
    }

    /**
     * Retrieves a {@link Person} entity by its ID.
     *
     * <p>The method searches the database for a {@link Person} entity with the specified ID.
     *
     * @param id the ID of the {@link Person} entity to retrieve
     * @return the {@link Person} entity if found, or {@code null} if no entity exists with the given ID
     */
    @Override
    public Person getById(int id) {
        return entityManager.find(Person.class, id);
    }

    /**
     * Retrieves all {@link Person} entities stored in the database.
     *
     * <p>The method executes a JPQL query to retrieve all records of {@link Person} entities.
     *
     * @return a {@link List} of all {@link Person} entities
     */
    @Override
    public List<Person> getAll() {
        return entityManager.createQuery("SELECT u FROM Person u", Person.class).getResultList();
    }

    /**
     * Counts the total number of {@link Person} entities in the database.
     *
     * <p>This method executes a JPQL query to calculate the total record count of {@link Person} entities.
     *
     * @return the total number of {@link Person} entities as a {@code long} value
     */
    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(u) FROM Person u", Long.class).getSingleResult();
        return count.intValue();
    }

    /**
     * Finds a {@link Person} entity by its email address.
     *
     * <p>This method executes a JPQL query to search for a {@link Person} entity where
     * the email matches the provided string.
     *
     * @param email the email address of the {@link Person} entity to retrieve
     * @return the {@link Person} entity with the specified email, or {@code null} if no such entity exists
     */
    public Person getByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM Person u WHERE u.email = :email", Person.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}