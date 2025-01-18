package com.library.library_management.domain.services;

import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Publisher;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing operations related to publishers in the library management system.
 *
 * <p>This class provides business logic for handling publisher-related operations, including
 * adding, deleting, and retrieving publishers. It serves as a bridge between the
 * {@link PublisherDAO} data access layer and higher-level application layers.</p>
 *
 * <p>Annotated as a {@link Service}, it is a Spring component responsible for encapsulating
 * the core publisher-related logic. It also uses {@link Transactional} to ensure operations
 * are executed within a transactional context for data consistency.</p>
 */
@Service
@Transactional
public class PublisherService {

    /**
     * Data Access Object for performing database operations on {@link Publisher} entities.
     */
    private final PublisherDAO publisherDAO;

    /**
     * Constructs a new {@code PublisherService} and injects the required {@link PublisherDAO}.
     *
     * @param publisherDAO the DAO used for performing operations related to publishers
     */
    public PublisherService(PublisherDAO publisherDAO) {
        this.publisherDAO = publisherDAO;
    }

    /**
     * Adds a new publisher to the system.
     *
     * <p>This method delegates the task to {@link PublisherDAO#insert(Publisher)}, which persists
     * the provided {@link Publisher} entity into the database.</p>
     *
     * @param publisher the {@link Publisher} entity to be added
     */
    public void addPublisher(Publisher publisher) {
        publisherDAO.insert(publisher);
    }

    /**
     * Deletes an existing publisher from the system.
     *
     * <p>This method deletes a publisher identified by their unique ID by delegating the operation
     * to {@link PublisherDAO#delete(int)}.</p>
     *
     * @param id the unique identifier of the publisher to be deleted
     */
    public void deletePublisher(int id) {
        publisherDAO.delete(id);
    }

    /**
     * Retrieves all publishers in the system.
     *
     * <p>This method fetches a list of all {@link Publisher} entities from the database by invoking
     * {@link PublisherDAO#getAll()}.</p>
     *
     * @return a list of all {@link Publisher} entities in the system
     */
    public List<Publisher> getAllPublishers() {
        return publisherDAO.getAll();
    }
}