package com.library.library_management;

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
@ActiveProfiles("test") // Uses "test" profile for the database
public class PublisherTests {

    @Autowired
    private PublisherDAO publisherDAO;

    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        publisher = new Publisher("Test Publisher", "123 Test St", "1234567890");
    }

    @AfterEach
    public void tearDown() {
        publisherDAO.getAll().forEach(p -> publisherDAO.delete(p.getId()));
    }

    @Test
    public void testInsertAndGetById() {
        publisherDAO.insert(publisher);

        Publisher fetchedPublisher = publisherDAO.getById(publisher.getId());
        assertNotNull(fetchedPublisher);
        assertEquals("Test Publisher", fetchedPublisher.getName());
    }

    @Test
    public void testGetAll() {
        publisherDAO.insert(publisher);

        List<Publisher> publishers = publisherDAO.getAll();
        assertEquals(1, publishers.size());
    }

    @Test
    public void testUpdate() {
        publisherDAO.insert(publisher);

        publisher.setName("Updated Publisher");
        publisherDAO.update(publisher);

        Publisher updatedPublisher = publisherDAO.getById(publisher.getId());
        assertEquals("Updated Publisher", updatedPublisher.getName());
    }

    @Test
    public void testDelete() {
        publisherDAO.insert(publisher);

        assertNotNull(publisherDAO.getById(publisher.getId()));

        publisherDAO.delete(publisher.getId());

        assertNull(publisherDAO.getById(publisher.getId()));
    }
}