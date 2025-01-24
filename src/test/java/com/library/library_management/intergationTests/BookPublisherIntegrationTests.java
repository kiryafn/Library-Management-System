package com.library.library_management.intergationTests;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Используем Spring Boot Test Context
@ActiveProfiles("test") // Тестовый профиль базы данных
public class BookPublisherIntegrationTests {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PublisherDAO publisherDAO;

    private Publisher publisher;
    private Book book;

    @BeforeEach
    void setUp() {
        // Создаем издателя
        publisher = new Publisher("Integration Publisher", "123 Integration St", "1234567890");
        publisherDAO.insert(publisher);

        // Создаем книгу, связанную с издателем
        book = new Book("Integration Test Book", "Test Author", publisher, 2023, "9876543210123");
    }

    @AfterEach
    void tearDown() {
        // Удаляем все данные после каждого теста
        bookDAO.getAll().forEach(b -> bookDAO.delete(b.getId()));
        publisherDAO.getAll().forEach(p -> publisherDAO.delete(p.getId()));
    }

    @Test
    void testInsertPublisherAndBook() {
        // Вставляем книгу
        bookDAO.insert(book);

        // Проверяем, что издатель и книга вставлены корректно
        Book fetchedBook = bookDAO.getById(book.getId());
        assertNotNull(fetchedBook);
        assertNotNull(fetchedBook.getPublisher());
        assertEquals(publisher.getId(), fetchedBook.getPublisher().getId());
        assertEquals("Integration Publisher", fetchedBook.getPublisher().getName());
    }

    @Test
    void testPublisherWithMultipleBooks() {
        // Создаем и добавляем несколько книг для одного издателя
        Book book1 = new Book("Book One", "Author One", publisher, 2021, "1111111111111");
        Book book2 = new Book("Book Two", "Author Two", publisher, 2022, "2222222222222");

        bookDAO.insert(book);
        bookDAO.insert(book1);
        bookDAO.insert(book2);

        // Проверяем список книг для издателя
        Publisher fetchedPublisher = publisherDAO.getById(publisher.getId());
        List<Book> books = bookDAO.getAll();
        assertEquals(3, books.size());
        assertTrue(books.stream().allMatch(b -> b.getPublisher().getId().equals(publisher.getId())));

        assertNotNull(fetchedPublisher);
    }

    @Test
    void testUpdatePublisherDetails() {
        // Вставляем издателя и обновляем его данные
        publisher.setName("Updated Publisher Name");
        publisher.setAddress("Updated Address");
        publisherDAO.update(publisher);

        Publisher updatedPublisher = publisherDAO.getById(publisher.getId());
        assertEquals("Updated Publisher Name", updatedPublisher.getName());
        assertEquals("Updated Address", updatedPublisher.getAddress());
    }

    @Test
    void testRetrieveBooksByPublisher() {
        // Создаем несколько книг
        Book book1 = new Book("Another Book", "Another Author", publisher, 2022, "3333333333333");

        bookDAO.insert(book);
        bookDAO.insert(book1);

        // Проверяем книги, связанные с издателем
        List<Book> books = bookDAO.getAll();
        assertEquals(2, books.size());
        assertTrue(books.stream().allMatch(b -> b.getPublisher().getId().equals(publisher.getId())));
    }
}