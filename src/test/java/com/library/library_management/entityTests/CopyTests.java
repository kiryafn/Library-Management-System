package com.library.library_management.entityTests;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dao.CopyDAO;
import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Book;
import com.library.library_management.data.entities.Copy;
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
@ActiveProfiles("test") // Use "test" profile for database configuration
public class CopyTests {

    @Autowired
    private CopyDAO copyDAO; // DAO for handling Copy entity operations

    @Autowired
    private BookDAO bookDAO; // DAO for handling Book entity operations

    @Autowired
    private PublisherDAO publisherDAO; // DAO for handling Publisher entity operations

    private Publisher publisher; // Test Publisher object
    private Book book; // Test Book object
    private Copy copy; // Test Copy object

    @BeforeEach
    void setUp() {
        // Setup test data: Create and insert a Publisher, a Book, and a Copy
        publisher = new Publisher("Test Publisher", "123 Test St", "12345");
        publisherDAO.insert(publisher);

        book = new Book("Test Book", "John Doe", publisher, 2023, "123-456-789");
        bookDAO.insert(book);

        copy = new Copy(book, 1, "Available");
    }

    @AfterEach
    void tearDown() {
        // Clean up all created data after each test
        copyDAO.getAll().forEach(c -> copyDAO.delete(c.getId()));
        bookDAO.getAll().forEach(b -> bookDAO.delete(b.getId()));
        publisherDAO.getAll().forEach(p -> publisherDAO.delete(p.getId()));
    }

    @Test
    void contextLoads() {
        // Ensure the DAO components are properly initialized by the Spring Context
        assertNotNull(copyDAO);
        assertNotNull(bookDAO);
    }

    @Test
    void testEntityConstructorsAndFields() {
        // Verify that Copy fields are correctly set through its constructor
        assertNotNull(copy.getBook());
        assertEquals(1, copy.getCopyNumber());
        assertEquals("Available", copy.getStatus());
    }

    @Test
    void testStatusUpdateMethods() {
        // Test entity methods for updating Copy status
        copy.markAsBorrowed();
        assertEquals("Borrowed", copy.getStatus());

        copy.markAsLost();
        assertEquals("Lost", copy.getStatus());

        copy.markAsAvailable();
        assertEquals("Available", copy.getStatus());
    }

    @Test
    void testInsertAndRetrieveCopy() {
        // Test inserting a Copy and retrieving it by its ID
        copyDAO.insert(copy);

        Copy fetchedCopy = copyDAO.getById(copy.getId());
        assertNotNull(fetchedCopy);
        assertEquals(copy.getCopyNumber(), fetchedCopy.getCopyNumber());
        assertEquals(copy.getStatus(), fetchedCopy.getStatus());
    }

    @Test
    void testUpdateCopy() {
        // Test updating the status of an inserted Copy
        copyDAO.insert(copy);

        copy.setStatus("Borrowed");
        copyDAO.update(copy);

        Copy updatedCopy = copyDAO.getById(copy.getId());
        assertEquals("Borrowed", updatedCopy.getStatus());
    }

    @Test
    void testDeleteCopy() {
        // Test deleting a Copy and ensuring it no longer exists
        copyDAO.insert(copy);

        assertNotNull(copyDAO.getById(copy.getId())); // Check existence before delete

        copyDAO.delete(copy.getId());

        assertNull(copyDAO.getById(copy.getId())); // Verify deletion
    }

    @Test
    void testGetAllCopies() {
        // Test fetching all copies from the database
        Copy copy2 = new Copy(book, 2, "Borrowed");
        copyDAO.insert(copy);
        copyDAO.insert(copy2);

        List<Copy> copies = copyDAO.getAll();
        assertEquals(2, copies.size());
    }

    @Test
    void testGetCount() {
        // Test the getCount method for copies in the database
        long initialCount = copyDAO.getCount();

        Copy copy2 = new Copy(book, 2, "Borrowed");
        copyDAO.insert(copy);
        copyDAO.insert(copy2);

        assertEquals(initialCount + 2, copyDAO.getCount());
    }

    @Test
    void testInsertCopyWithNoBook() {
        // Test behavior when inserting a Copy with no associated Book
        Copy invalidCopy = new Copy(new Book(), 1, "Available");
        assertThrows(Exception.class, () -> copyDAO.insert(invalidCopy)); // Expect an exception due to foreign key constraint
    }
}