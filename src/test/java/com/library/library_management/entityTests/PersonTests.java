package com.library.library_management.entityTests;

import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.entities.Person;
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
public class PersonTests {

    @Autowired
    private PersonDAO personDAO; // DAO for Person operations

    private Person person; // Test Person object

    @BeforeEach
    public void setUp() {
        // Prepare test data: a Person
        person = new Person("John Doe", "john.doe@example.com", "123456789", "123 Test St");
    }

    @AfterEach
    public void tearDown() {
        // Clean up after tests
        personDAO.getAll().forEach(p -> personDAO.delete(p.getId()));
    }

    @Test
    public void testInsertAndGetById() {
        // Test inserting and retrieving Person by ID
        personDAO.insert(person);

        Person fetchedPerson = personDAO.getById(person.getId());
        assertNotNull(fetchedPerson);
        assertEquals(person.getName(), fetchedPerson.getName());
    }

    @Test
    public void testGetAll() {
        // Test retrieving all Persons
        personDAO.insert(person);

        List<Person> persons = personDAO.getAll();
        assertEquals(1, persons.size());
    }

    @Test
    public void testUpdate() {
        // Test updating Person's name
        personDAO.insert(person);

        person.setName("Jane Doe");
        personDAO.update(person);

        Person updatedPerson = personDAO.getById(person.getId());
        assertEquals("Jane Doe", updatedPerson.getName());
    }

    @Test
    public void testDelete() {
        // Test deleting Person
        personDAO.insert(person);

        assertNotNull(personDAO.getById(person.getId()));

        personDAO.delete(person.getId());

        assertNull(personDAO.getById(person.getId()));
    }
}