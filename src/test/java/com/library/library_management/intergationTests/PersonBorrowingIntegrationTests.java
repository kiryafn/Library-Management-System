package com.library.library_management.intergationTests;

import com.library.library_management.data.dao.BorrowingDAO;
import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.dao.CopyDAO;
import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Borrowing;
import com.library.library_management.data.entities.Person;
import com.library.library_management.data.entities.Copy;
import com.library.library_management.data.entities.Book;
import com.library.library_management.data.entities.Publisher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PersonBorrowingIntegrationTests {

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private BorrowingDAO borrowingDAO;

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
        // Создаем издателя
        publisher = new Publisher("Test Publisher", "123 Test Address", "123456789");
        publisherDAO.insert(publisher);

        // Создаем книгу
        book = new Book("Integration Test Book", "Test Author", publisher, 2023, "1234567890123");
        bookDAO.insert(book);

        // Создаем копию книги
        copy = new Copy(book, 1, "Available");
        copyDAO.insert(copy);

        // Создаем пользователя
        person = new Person("Test User", "test.user@example.com", "123456789", "123 User Address");
        personDAO.insert(person);

        // Создаем заимствование
        borrowing = new Borrowing(person, copy, LocalDate.now(), null);
    }

    @AfterEach
    public void tearDown() {
        // Удаляем все данные после каждого теста
        borrowingDAO.getAll().forEach(b -> borrowingDAO.delete(b.getId()));
        copyDAO.getAll().forEach(c -> copyDAO.delete(c.getId()));
        bookDAO.getAll().forEach(b -> bookDAO.delete(b.getId()));
        personDAO.getAll().forEach(p -> personDAO.delete(p.getId()));
        publisherDAO.getAll().forEach(p -> publisherDAO.delete(p.getId()));
    }

    @Test
    public void testInsertBorrowingForPerson() {
        // Добавляем заимствование
        borrowingDAO.insert(borrowing);

        // Проверяем, что заимствование связано с пользователем
        Borrowing fetchedBorrowing = borrowingDAO.getById(borrowing.getId());
        assertNotNull(fetchedBorrowing);
        assertEquals(person.getId(), fetchedBorrowing.getPerson().getId());
        assertEquals(copy.getId(), fetchedBorrowing.getCopy().getId());
    }

    @Test
    public void testGetBorrowingsByPerson() {
        // Добавляем заимствование
        borrowingDAO.insert(borrowing);

        // Проверяем список заимствований для пользователя
        List<Borrowing> borrowings = borrowingDAO.getByUserId(person.getId().toString());
        assertNotNull(borrowings);
        assertEquals(1, borrowings.size());
        assertEquals(person.getId(), borrowings.get(0).getPerson().getId());
    }

    @Test
    public void testUpdateBorrowingForPerson() {
        // Добавляем заимствование
        borrowingDAO.insert(borrowing);

        // Обновляем дату возвращения книги
        borrowing.setReturnDate(LocalDate.now().plusDays(7));
        borrowingDAO.update(borrowing);

        Borrowing updatedBorrowing = borrowingDAO.getById(borrowing.getId());
        assertNotNull(updatedBorrowing.getReturnDate());
        assertEquals(LocalDate.now().plusDays(7), updatedBorrowing.getReturnDate());
    }

    @Test
    public void testGetPersonsWithMultipleBorrowings() {
        // Создаем второе заимствование
        Borrowing secondBorrowing = new Borrowing(person, copy, LocalDate.now().minusDays(1), LocalDate.now());
        borrowingDAO.insert(borrowing);
        borrowingDAO.insert(secondBorrowing);

        // Проверяем заимствования
        List<Borrowing> borrowings = borrowingDAO.getByUserId(person.getId().toString());
        assertNotNull(borrowings);
        assertEquals(2, borrowings.size());
    }
}