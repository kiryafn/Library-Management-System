package com.library.library_management.entityTests;

import com.library.library_management.data.dao.LibrarianDAO;
import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.entities.Librarian;
import com.library.library_management.data.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Enables Spring Boot Test Context
@ActiveProfiles("test") // Uses "test" database profile
public class LibrarianTests {

    @Autowired
    private LibrarianDAO librarianDAO; // DAO for Librarian

    @Autowired
    private PersonDAO personDAO; // DAO for User

    private User user; // Test User
    private Librarian librarian; // Test Librarian

    @BeforeEach
    public void setUp() {
        // Prepare test data: User and Librarian
        user = new User("John Doe", "john.doe@example.com", "123456789", "123 Test St");
        personDAO.insert(user);

        librarian = new Librarian(user, LocalDate.now(), "Head Librarian");
    }

    @AfterEach
    public void tearDown() {
        // Clean up data after tests
        librarianDAO.getAll().forEach(l -> librarianDAO.delete(l.getId()));
        personDAO.getAll().forEach(p -> personDAO.delete(p.getId()));
    }

    @Test
    public void testInsertAndGetById() {
        // Test inserting and retrieving Librarian by ID
        librarianDAO.insert(librarian);

        Librarian fetchedLibrarian = librarianDAO.getById(librarian.getId());
        assertNotNull(fetchedLibrarian);
        assertEquals(user.getName(), fetchedLibrarian.getPerson().getName());
    }

    @Test
    public void testGetAll() {
        // Test retrieving all Librarians
        librarianDAO.insert(librarian);

        assertEquals(1, librarianDAO.getAll().size());
    }

    @Test
    public void testUpdate() {
        // Test updating Librarian's position
        librarianDAO.insert(librarian);

        librarian.setPosition("Assistant Librarian");
        librarianDAO.update(librarian);

        Librarian updatedLibrarian = librarianDAO.getById(librarian.getId());
        assertEquals("Assistant Librarian", updatedLibrarian.getPosition());
    }

    @Test
    public void testDelete() {
        // Test deleting Librarian
        librarianDAO.insert(librarian);

        assertNotNull(librarianDAO.getById(librarian.getId()));

        librarianDAO.delete(librarian.getId());

        assertNull(librarianDAO.getById(librarian.getId()));
    }
}