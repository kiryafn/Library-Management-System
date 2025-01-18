package com.library.library_management.data.dao;

import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Generic Data Access Object (DAO) interface that defines common operations
 * for managing entities in the database.
 *
 * <p>This interface provides a generic structure for performing basic CRUD
 * (Create, Read, Update, Delete) operations on any entity type. It also includes
 * methods for retrieving a list of all entities and counting the total number of records.
 *
 * <p>Implementing classes are expected to provide concrete logic for these operations,
 * often using tools like JPA {@link jakarta.persistence.EntityManager}.
 *
 * @param <Entity> the type of the entity managed by this DAO
 */
public interface DAO<Entity> {

    /**
     * Inserts a new entity record into the database.
     *
     * <p>Implementations of this method should ensure that the provided entity is persisted
     * into the database in a transactional manner.
     *
     * @param t the entity to be inserted into the database
     */
    void insert(Entity t);

    /**
     * Deletes an entity record from the database based on its unique identifier.
     *
     * <p>Implementations of this method should ensure the provided entity is deleted
     * if it exists; otherwise, no action is taken.
     *
     * @param id the unique identifier of the entity to be deleted
     */
    void delete(int id);

    /**
     * Updates an existing entity record in the database.
     *
     * <p>This method is used for synchronizing changes made to an entity in memory
     * with its corresponding record in the database.
     *
     * @param t the entity with updated values to be saved in the database
     */
    void update(Entity t);

    /**
     * Retrieves an entity record by its unique identifier.
     *
     * @param id the unique identifier of the entity to retrieve
     * @return the entity if found, or {@code null} if no entity exists with the specified ID
     */
    Entity getById(int id);

    /**
     * Retrieves all entity records from the database.
     *
     * <p>This method should return a list containing all the records of the specified
     * entity type found in the database.
     *
     * @return a list of all entities managed by this DAO
     */
    List<Entity> getAll();

    /**
     * Counts the total number of entity records in the database.
     *
     * <p>This method provides a way to determine the number of records of the specified
     * entity type currently stored in the database.
     *
     * @return the total count of entity records
     */
    long getCount();
}