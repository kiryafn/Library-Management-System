package com.library.library_management.entityTests;

import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.entities.User;
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
public class UserTests {

    @Autowired
    private PersonDAO personDAO; // DAO for User operations

    private User user; // Test User object

    @BeforeEach
    public void setUp() {
        // Prepare test data: a User
        user = new User("John Doe", "john.doe@example.com", "123456789", "123 Test St");
    }

    @AfterEach
    public void tearDown() {
        // Clean up after tests
        personDAO.getAll().forEach(p -> personDAO.delete(p.getId()));
    }

    @Test
    public void testInsertAndGetById() {
        // Test inserting and retrieving User by ID
        personDAO.insert(user);

        User fetchedUser = personDAO.getById(user.getId());
        assertNotNull(fetchedUser);
        assertEquals(user.getName(), fetchedUser.getName());
    }

    @Test
    public void testGetAll() {
        // Test retrieving all Persons
        personDAO.insert(user);

        List<User> users = personDAO.getAll();
        assertEquals(1, users.size());
    }

    @Test
    public void testUpdate() {
        // Test updating User's name
        personDAO.insert(user);

        user.setName("Jane Doe");
        personDAO.update(user);

        User updatedUser = personDAO.getById(user.getId());
        assertEquals("Jane Doe", updatedUser.getName());
    }

    @Test
    public void testDelete() {
        // Test deleting User
        personDAO.insert(user);

        assertNotNull(personDAO.getById(user.getId()));

        personDAO.delete(user.getId());

        assertNull(personDAO.getById(user.getId()));
    }
}