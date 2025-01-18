package com.library.library_management;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Book;
import com.library.library_management.data.entities.Publisher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Enables Spring Boot Test Context
@ActiveProfiles("test") // Uses "test" profile for the database
public class BookTests {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PublisherDAO publisherDAO;

    private Book book;
    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        publisher = new Publisher("Test Publisher", "123 Test St", "1234567890");
        publisherDAO.insert(publisher);

        book = new Book("Test Book", "Test Author", publisher, 2023, "1234567890123");
    }

    @AfterEach
    public void tearDown() {
        bookDAO.getAll().forEach(b -> bookDAO.delete(b.getId()));
        publisherDAO.getAll().forEach(p -> publisherDAO.delete(p.getId()));
    }

    @Test
    public void testInsertAndGetById() {
        bookDAO.insert(book);

        Book fetchedBook = bookDAO.getById(book.getId());
        assertNotNull(fetchedBook);
        assertEquals(book.getTitle(), fetchedBook.getTitle());
    }

    @Test
    public void testGetAll() {
        bookDAO.insert(book);

        assertEquals(1, bookDAO.getAll().size());
    }

    @Test
    public void testUpdate() {
        bookDAO.insert(book);

        book.setTitle("Updated Book Title");
        bookDAO.update(book);

        Book updatedBook = bookDAO.getById(book.getId());
        assertEquals("Updated Book Title", updatedBook.getTitle());
    }

    @Test
    public void testDelete() {
        bookDAO.insert(book);

        assertNotNull(bookDAO.getById(book.getId()));

        bookDAO.delete(book.getId());

        assertNull(bookDAO.getById(book.getId()));
    }
}