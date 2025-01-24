package com.library.library_management.entityTests;

import com.library.library_management.data.dao.*;
import com.library.library_management.data.entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Enables Spring Boot Test Context
@ActiveProfiles("test") // Use "test" profile for database configuration
public class BorrowingTests {

    @Autowired
    private BorrowingDAO borrowingDAO; // DAO for Borrowing operations

    @Autowired
    private PersonDAO personDAO; // DAO for Person operations

    @Autowired
    private CopyDAO copyDAO; // DAO for Copy operations

    @Autowired
    private BookDAO bookDAO; // DAO for Book operations

    @Autowired
    private PublisherDAO publisherDAO; // DAO for Publisher operations

    private Person person; // Test Person object
    private Copy copy; // Test Copy object
    private Book book; // Test Book object
    private Borrowing borrowing; // Test Borrowing object
    private Publisher publisher; // Test Publisher object

    @BeforeEach
    public void setUp() {
        // Prepare data: Publisher, Book, Person, Copy, and Borrowing
        publisher = new Publisher("Test Publisher", "123 Test St", "12345");
        publisherDAO.insert(publisher);

        book = new Book("Test Book", "Test Author", publisher, 2023, "123456789");
        bookDAO.insert(book);

        person = new Person("John Doe", "john.doe@example.com", "123456789", "123 Test St");
        personDAO.insert(person);

        copy = new Copy(book, 1, "Available");
        copyDAO.insert(copy);

        borrowing = new Borrowing(person, copy, LocalDate.now(), null);
    }

    @AfterEach
    public void tearDown() {
        // Clean up after tests
        borrowingDAO.getAll().forEach(b -> borrowingDAO.delete(b.getId()));
        copyDAO.getAll().forEach(c -> copyDAO.delete(c.getId()));
        bookDAO.getAll().forEach(b -> bookDAO.delete(b.getId()));
        personDAO.getAll().forEach(p -> personDAO.delete(p.getId()));
    }

    @Test
    public void testInsertAndGetById() {
        // Test inserting and retrieving Borrowing by ID
        borrowingDAO.insert(borrowing);

        Borrowing fetchedBorrowing = borrowingDAO.getById(borrowing.getId());
        assertNotNull(fetchedBorrowing);
        assertEquals(borrowing.getPerson().getName(), fetchedBorrowing.getPerson().getName());
        assertEquals(borrowing.getCopy().getCopyNumber(), fetchedBorrowing.getCopy().getCopyNumber());
    }

    @Test
    public void testGetAll() {
        // Test retrieving all Borrowings
        borrowingDAO.insert(borrowing);

        List<Borrowing> borrowings = borrowingDAO.getAll();
        assertEquals(1, borrowings.size());
    }

    @Test
    public void testUpdate() {
        // Test updating Borrowing's return date
        borrowingDAO.insert(borrowing);

        borrowing.setReturnDate(LocalDate.now().plusDays(7));
        borrowingDAO.update(borrowing);

        Borrowing updatedBorrowing = borrowingDAO.getById(borrowing.getId());
        assertNotNull(updatedBorrowing.getReturnDate());
        assertEquals(LocalDate.now().plusDays(7), updatedBorrowing.getReturnDate());
    }

    @Test
    public void testDelete() {
        // Test deleting Borrowing
        borrowingDAO.insert(borrowing);

        assertNotNull(borrowingDAO.getById(borrowing.getId()));

        borrowingDAO.delete(borrowing.getId());

        assertNull(borrowingDAO.getById(borrowing.getId()));
    }
}