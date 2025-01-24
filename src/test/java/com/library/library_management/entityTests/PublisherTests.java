package com.library.library_management.entityTests;

import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Publisher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Enables Spring Boot Test Context
@ActiveProfiles("test") // Uses "test" database profile
public class PublisherTests {

    @Autowired
    private PublisherDAO publisherDAO; // DAO for Publisher operations

    private Publisher publisher; // Test Publisher entity

    @BeforeEach
    public void setUp() {
        // Initialize test Publisher entity
        publisher = new Publisher("Test Publisher", "123 Test St", "1234567890");
    }

    @AfterEach
    public void tearDown() {
        // Clean up after each test
        publisherDAO.getAll().forEach(p -> publisherDAO.delete(p.getId()));
    }

    @Test
    public void testInsertAndGetById() {
        // Test inserting and retrieving a Publisher by ID
        publisherDAO.insert(publisher);

        Publisher fetchedPublisher = publisherDAO.getById(publisher.getId());
        assertNotNull(fetchedPublisher);
        assertEquals("Test Publisher", fetchedPublisher.getName());
    }

    @Test
    public void testGetAll() {
        // Test retrieving all Publishers
        publisherDAO.insert(publisher);

        List<Publisher> publishers = publisherDAO.getAll();
        assertEquals(1, publishers.size());
    }

    @Test
    public void testUpdate() {
        // Test updating Publisher's name
        publisherDAO.insert(publisher);

        publisher.setName("Updated Publisher");
        publisherDAO.update(publisher);

        Publisher updatedPublisher = publisherDAO.getById(publisher.getId());
        assertEquals("Updated Publisher", updatedPublisher.getName());
    }

    @Test
    public void testDelete() {
        // Test deleting a Publisher
        publisherDAO.insert(publisher);

        assertNotNull(publisherDAO.getById(publisher.getId()));

        publisherDAO.delete(publisher.getId());

        assertNull(publisherDAO.getById(publisher.getId()));
    }
}