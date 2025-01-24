package com.library.library_management.intergationTests;

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

@SpringBootTest // Используется для загрузки контекста Spring Boot
@ActiveProfiles("test") // Профиль "тестовой" базы данных
public class BookCopyIntegrationTests {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private CopyDAO copyDAO;

    @Autowired
    private PublisherDAO publisherDAO;

    private Publisher publisher;
    private Book book;
    private Copy copy;

    @BeforeEach
    void setUp() {
        // Создаем издателя для книги
        publisher = new Publisher("Integration Publisher", "Integration Address", "9876543210");
        publisherDAO.insert(publisher);

        // Создаем книгу
        book = new Book("Integration Test Book", "Test Author", publisher, 2022, "9876543210123");
        bookDAO.insert(book);

        // Создаем копию для книги
        copy = new Copy(book, 1, "Available");
    }

    @AfterEach
    void tearDown() {
        // Очищаем базу данных после каждого теста
        copyDAO.getAll().forEach(c -> copyDAO.delete(c.getId()));
        bookDAO.getAll().forEach(b -> bookDAO.delete(b.getId()));
        publisherDAO.getAll().forEach(p -> publisherDAO.delete(p.getId()));
    }

    @Test
    void testInsertBookAndCopy() {
        // Проверяем вставку книги и копии
        copyDAO.insert(copy);

        Copy fetchedCopy = copyDAO.getById(copy.getId());
        assertNotNull(fetchedCopy);
        assertNotNull(fetchedCopy.getBook());
        assertEquals(book.getId(), fetchedCopy.getBook().getId());
    }

    @Test
    void testBookWithMultipleCopies() {
        // Проверяем работу с несколькими копиями
        Copy copy1 = new Copy(book, 2, "Borrowed");
        Copy copy2 = new Copy(book, 3, "Reserved");

        copyDAO.insert(copy);
        copyDAO.insert(copy1);
        copyDAO.insert(copy2);

        // Проверяем, что созданы три копии
        List<Copy> copies = copyDAO.getAll();
        assertEquals(3, copies.size());

        // Проверяем взаимосвязь книги и копий
        assertTrue(copies.stream().allMatch(c -> c.getBook().getId().equals(book.getId())));
    }

    @Test
    void testDeleteBookAndCascadeCopies() {
        // Вставляем книгу и ее копии
        copyDAO.insert(copy);
        bookDAO.delete(book.getId());

        // Проверяем, что копия удалена каскадно
        Copy fetchedCopy = copyDAO.getById(copy.getId());
        assertNull(fetchedCopy, "Копия должна быть автоматически удалена после удаления книги.");
    }

    @Test
    void testUpdateCopyStatus() {
        // Проверяем обновление статуса копии
        copyDAO.insert(copy);

        copy.setStatus("Borrowed");
        copyDAO.update(copy);

        Copy updatedCopy = copyDAO.getById(copy.getId());
        assertEquals("Borrowed", updatedCopy.getStatus());
    }

    @Test
    void testRetrieveCopiesByBook() {
        // Добавляем несколько копий и проверяем связь с книгой
        Copy copy1 = new Copy(book, 2, "Available");
        copyDAO.insert(copy);
        copyDAO.insert(copy1);

        List<Copy> bookCopies = bookDAO.getById(book.getId()).getCopies();
        assertEquals(2, bookCopies.size());
    }
}