package com.library.library_management;

import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PublisherDAOTest {

    @Autowired
    private EntityManager entityManager;

    private PublisherDAO publisherDAO;

    @BeforeEach
    void setUp() {
        publisherDAO = new PublisherDAO();
        publisherDAO.entityManager = this.entityManager;
    }

    @Test
    void testInsert() {
        Publisher publisher = new Publisher();
        publisher.setName("Test Publisher");
        publisher.setAddress("123 Test Street");
        publisher.setPhonenumber("123-456-7890");

        publisherDAO.insert(publisher);

        assertNotNull(publisher.getId());
        Publisher savedPublisher = entityManager.find(Publisher.class, publisher.getId());
        assertEquals("Test Publisher", savedPublisher.getName());
    }

    @Test
    void testDelete() {
        Publisher publisher = new Publisher();
        publisher.setName("Test Publisher");
        publisher.setAddress("123 Test Street");
        publisher.setPhonenumber("123-456-7890");

        entityManager.persist(publisher);
        int id = publisher.getId();

        publisherDAO.delete(id);

        assertNull(entityManager.find(Publisher.class, id));
    }

    @Test
    void testUpdate() {
        Publisher publisher = new Publisher();
        publisher.setName("Old Name");
        publisher.setAddress("123 Test Street");
        publisher.setPhonenumber("123-456-7890");

        entityManager.persist(publisher);

        publisher.setName("Updated Name");
        publisherDAO.update(publisher);

        Publisher updatedPublisher = entityManager.find(Publisher.class, publisher.getId());
        assertEquals("Updated Name", updatedPublisher.getName());
    }

    @Test
    void testGetById() {
        Publisher publisher = new Publisher();
        publisher.setName("Retrieved Publisher");
        publisher.setAddress("123 Test Street");
        publisher.setPhonenumber("123-456-7890");

        entityManager.persist(publisher);

        Publisher retrievedPublisher = publisherDAO.getById(publisher.getId());
        assertNotNull(retrievedPublisher);
        assertEquals("Retrieved Publisher", retrievedPublisher.getName());
    }

    @Test
    void testGetAll() {
        Publisher publisher1 = new Publisher();
        publisher1.setName("Publisher 1");
        publisher1.setAddress("Address 1");
        publisher1.setPhonenumber("123-456-7890");

        Publisher publisher2 = new Publisher();
        publisher2.setName("Publisher 2");
        publisher2.setAddress("Address 2");
        publisher2.setPhonenumber("987-654-3210");

        entityManager.persist(publisher1);
        entityManager.persist(publisher2);

        List<Publisher> publishers = publisherDAO.getAll();
        assertEquals(2, publishers.size());
    }

    @Test
    void testGetCount() {
        Publisher publisher1 = new Publisher();
        publisher1.setName("Publisher 1");
        publisher1.setAddress("Address 1");
        publisher1.setPhonenumber("123-456-7890");

        Publisher publisher2 = new Publisher();
        publisher2.setName("Publisher 2");
        publisher2.setAddress("Address 2");
        publisher2.setPhonenumber("987-654-3210");

        entityManager.persist(publisher1);
        entityManager.persist(publisher2);

        long count = publisherDAO.getCount();
        assertEquals(2, count);
    }
}