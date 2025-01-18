package com.library.library_management;

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
@ActiveProfiles("test") // Uses "test" profile for the database
public class CopyTests {

    @Autowired
    private CopyDAO copyDAO;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PublisherDAO publisherDAO;

    private Publisher publisher;
    private Book book;
    private Copy copy;

    @BeforeEach
    void setUp() {
        // Create a Publisher (required for Book)
        publisher = new Publisher("Test Publisher", "123 Test St", "12345");
        publisherDAO.insert(publisher);

        // Create a Book (required for Copy)
        book = new Book("Test Book", "John Doe", publisher, 2023, "123-456-789");
        bookDAO.insert(book);

        // Create a Copy for the Book
        copy = new Copy(book, 1, "Available");
    }

    @AfterEach
    void tearDown() {
        // Clean up database after tests
        copyDAO.getAll().forEach(c -> copyDAO.delete(c.getId()));
        bookDAO.getAll().forEach(b -> bookDAO.delete(b.getId()));
        publisherDAO.getAll().forEach(p -> publisherDAO.delete(p.getId()));
    }

    /**
     * Test basic context loading and DAO initialization.
     */
    @Test
    void contextLoads() {
        assertNotNull(copyDAO, "CopyDAO should be initialized.");
        assertNotNull(bookDAO, "BookDAO should be initialized.");
    }

    /**
     * Test the Copy entity's constructor and field setting.
     */
    @Test
    void testEntityConstructorsAndFields() {
        assertNotNull(copy.getBook());
        assertEquals(1, copy.getCopyNumber());
        assertEquals("Available", copy.getStatus());
    }

    /**
     * Test utility methods in Copy entity for status updates.
     */
    @Test
    void testStatusUpdateMethods() {
        copy.markAsBorrowed();
        assertEquals("Borrowed", copy.getStatus());

        copy.markAsLost();
        assertEquals("Lost", copy.getStatus());

        copy.markAsAvailable();
        assertEquals("Available", copy.getStatus());
    }

    /**
     * Test DAO insert and retrieve copy by ID.
     */
    @Test
    void testInsertAndRetrieveCopy() {
        copyDAO.insert(copy);

        Copy fetchedCopy = copyDAO.getById(copy.getId());
        assertNotNull(fetchedCopy);
        assertEquals(copy.getCopyNumber(), fetchedCopy.getCopyNumber());
        assertEquals(copy.getStatus(), fetchedCopy.getStatus());
    }

    /**
     * Test update functionality for a Copy entity.
     */
    @Test
    void testUpdateCopy() {
        copyDAO.insert(copy);

        copy.setStatus("Borrowed");
        copyDAO.update(copy);

        Copy updatedCopy = copyDAO.getById(copy.getId());
        assertEquals("Borrowed", updatedCopy.getStatus());
    }

    /**
     * Test delete functionality for a Copy entity.
     */
    @Test
    void testDeleteCopy() {
        copyDAO.insert(copy);

        assertNotNull(copyDAO.getById(copy.getId()), "Copy should be present before deletion.");

        copyDAO.delete(copy.getId());

        assertNull(copyDAO.getById(copy.getId()), "Copy should be removed after deletion.");
    }

    /**
     * Test retrieving all copies via DAO.
     */
    @Test
    void testGetAllCopies() {
        Copy copy2 = new Copy(book, 2, "Borrowed");
        copyDAO.insert(copy);
        copyDAO.insert(copy2);

        List<Copy> copies = copyDAO.getAll();
        assertEquals(2, copies.size());
    }

    /**
     * Test the getCount method for copies.
     */
    @Test
    void testGetCount() {
        long initialCount = copyDAO.getCount();

        Copy copy2 = new Copy(book, 2, "Borrowed");
        copyDAO.insert(copy);
        copyDAO.insert(copy2);

        assertEquals(initialCount + 2, copyDAO.getCount());
    }

    /**
     * Test invalid creation of a Copy without a valid Book.
     */
    @Test
    void testInsertCopyWithNoBook() {
        Copy invalidCopy = new Copy(new Book(), 1, "Available");
        assertThrows(Exception.class, () -> copyDAO.insert(invalidCopy), "Should throw an error for missing Book foreign key.");
    }
}