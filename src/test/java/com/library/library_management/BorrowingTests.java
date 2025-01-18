package com.library.library_management;

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
@ActiveProfiles("test")
public class BorrowingTests {

    @Autowired
    private BorrowingDAO borrowingDAO;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private CopyDAO copyDAO;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PublisherDAO publisherDAO;

    private Person person;
    private Copy copy;
    private Book book;
    private Borrowing borrowing;
    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        publisher = new Publisher("Test Publisher", "123 Test St", "12345");
        publisherDAO.insert(publisher);

        // Создаем объект Book для связи с Copy
        book = new Book("Test Book", "Test Author", publisher, 2023, "123456789");
        bookDAO.insert(book);

        // Создаем объект Person
        person = new Person("John Doe", "john.doe@example.com", "123456789", "123 Test St");
        personDAO.insert(person);

        // Создаем Copy, связанный с Book
        copy = new Copy(book, 1, "Available");
        copyDAO.insert(copy);

        // Создаем Borrowing
        borrowing = new Borrowing(person, copy, LocalDate.now(), null);
    }

    @AfterEach
    public void tearDown() {
        borrowingDAO.getAll().forEach(b -> borrowingDAO.delete(b.getId()));
        copyDAO.getAll().forEach(c -> copyDAO.delete(c.getId()));
        bookDAO.getAll().forEach(b -> bookDAO.delete(b.getId()));
        personDAO.getAll().forEach(p -> personDAO.delete(p.getId()));
    }

    @Test
    public void testInsertAndGetById() {
        borrowingDAO.insert(borrowing);

        Borrowing fetchedBorrowing = borrowingDAO.getById(borrowing.getId());
        assertNotNull(fetchedBorrowing);
        assertEquals(borrowing.getPerson().getName(), fetchedBorrowing.getPerson().getName());
        assertEquals(borrowing.getCopy().getCopyNumber(), fetchedBorrowing.getCopy().getCopyNumber());
    }

    @Test
    public void testGetAll() {
        borrowingDAO.insert(borrowing);

        List<Borrowing> borrowings = borrowingDAO.getAll();
        assertEquals(1, borrowings.size());
    }

    @Test
    public void testUpdate() {
        borrowingDAO.insert(borrowing);

        borrowing.setReturnDate(LocalDate.now().plusDays(7));
        borrowingDAO.update(borrowing);

        Borrowing updatedBorrowing = borrowingDAO.getById(borrowing.getId());
        assertNotNull(updatedBorrowing.getReturnDate());
        assertEquals(LocalDate.now().plusDays(7), updatedBorrowing.getReturnDate());
    }

    @Test
    public void testDelete() {
        borrowingDAO.insert(borrowing);

        assertNotNull(borrowingDAO.getById(borrowing.getId()));

        borrowingDAO.delete(borrowing.getId());

        assertNull(borrowingDAO.getById(borrowing.getId()));
    }
}