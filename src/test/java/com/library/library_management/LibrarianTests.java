package com.library.library_management;

import com.library.library_management.data.dao.LibrarianDAO;
import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.entities.Librarian;
import com.library.library_management.data.entities.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Enables Spring Boot Test Context
@ActiveProfiles("test") // Uses "test" profile for the database
public class LibrarianTests {

    @Autowired
    private LibrarianDAO librarianDAO;

    @Autowired
    private PersonDAO personDAO;

    private Person person;
    private Librarian librarian;

    @BeforeEach
    public void setUp() {
        person = new Person("John Doe", "john.doe@example.com", "123456789", "123 Test St");
        personDAO.insert(person);

        librarian = new Librarian(person, LocalDate.now(), "Head Librarian");
    }

    @AfterEach
    public void tearDown() {
        librarianDAO.getAll().forEach(l -> librarianDAO.delete(l.getId()));
        personDAO.getAll().forEach(p -> personDAO.delete(p.getId()));
    }

    @Test
    public void testInsertAndGetById() {
        librarianDAO.insert(librarian);

        Librarian fetchedLibrarian = librarianDAO.getById(librarian.getId());
        assertNotNull(fetchedLibrarian);
        assertEquals(person.getName(), fetchedLibrarian.getUser().getName());
    }

    @Test
    public void testGetAll() {
        librarianDAO.insert(librarian);

        assertEquals(1, librarianDAO.getAll().size());
    }

    @Test
    public void testUpdate() {
        librarianDAO.insert(librarian);

        librarian.setPosition("Assistant Librarian");
        librarianDAO.update(librarian);

        Librarian updatedLibrarian = librarianDAO.getById(librarian.getId());
        assertEquals("Assistant Librarian", updatedLibrarian.getPosition());
    }

    @Test
    public void testDelete() {
        librarianDAO.insert(librarian);

        assertNotNull(librarianDAO.getById(librarian.getId()));

        librarianDAO.delete(librarian.getId());

        assertNull(librarianDAO.getById(librarian.getId()));
    }
}