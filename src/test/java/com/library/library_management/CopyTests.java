package com.library.library_management;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dao.CopyDAO;
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

@SpringBootTest()
@ActiveProfiles("test")
class CopyTests {

    @Autowired
    private CopyDAO copyDAO;
    @Autowired
    private BookDAO bookDAO;

    private Copy copy;
    private Book book;

    @BeforeEach
    void before() {
        // Создаем базовый объект Book для тестирования
        book = new Book();
        book.setTitle("Test Book");
//        book.setAuthor("Test Author");
//        book.setIsbn("1234567890");
//        book.setPublicationYear(2022);
//        book.setPublisher();
        bookDAO.insert(book);

        copy = new Copy(book, 1, "Available");
    }

    @Test
    void testCreateCopy() {
        Copy copy = new Copy();
        copy.setCopyNumber(1);
        copy.setStatus("Available");
        copyDAO.insert(copy);

        Copy retrievedCopy = copyDAO.getById(copy.getId());
        assertNotNull(retrievedCopy);
        assertEquals(1, retrievedCopy.getCopyNumber());
        assertEquals("Available", retrievedCopy.getStatus());
    }


    @Test
    void contextLoads() {
        assertNotNull(copyDAO, "CopyDAO should not be null");
    }

    @Test
    void testConstructor() {
        assertNotNull(copy.getBook());
        assertEquals("Available", copy.getStatus());
        assertEquals(1, copy.getCopyNumber());
    }

    @Test
    void testSetAndGetId() {
        copy.setId(123);
        assertEquals(123, copy.getId());
    }

    @Test
    void testMarkAsBorrowedAndStatus() {
        copy.markAsBorrowed();
        assertEquals("Borrowed", copy.getStatus());
    }

    @Test
    void testMarkAsAvailable() {
        copy.markAsAvailable();
        assertTrue(copy.isAvailable());
    }

    // --- Интеграционные тесты DAO в Spring Boot контексте ---
    
    @Test
    void testInsertAndGetById() {
        // Создаём новый экземпляр Copy
        Copy newCopy = new Copy(book, 5, "Available");

        // Сохраняем объект через DAO
        copyDAO.insert(newCopy);

        // Проверяем, что объект можно извлечь
        Copy fetchedCopy = copyDAO.getById(newCopy.getId());
        assertNotNull(fetchedCopy);
        assertEquals("Available", fetchedCopy.getStatus());

        // Удалите запись после теста
        copyDAO.delete(newCopy.getId());
    }

    @Test
    void testDelete() {
        // Создаём новый экземпляр Copy
        Copy newCopy = new Copy(book, 3, "Borrowed");

        // Сохраняем объект через DAO
        copyDAO.insert(newCopy);
        int copyId = newCopy.getId();

        // Убедимся, что запись присутствует
        Copy fetchedCopy = copyDAO.getById(copyId);
        assertNotNull(fetchedCopy);

        // Удаляем запись
        copyDAO.delete(copyId);

        // Проверяем, что объект отсутствует в базе
        fetchedCopy = copyDAO.getById(copyId);
        assertNull(fetchedCopy);
    }

    @Test
    void testUpdate() {
        // Создаём новый экземпляр Copy
        Copy newCopy = new Copy(book, 7, "Reserved");

        // Сохраняем объект через DAO
        copyDAO.insert(newCopy);

        // Обновляем статус
        newCopy.markAsLost();
        copyDAO.update(newCopy);

        // Проверяем изменения
        Copy updatedCopy = copyDAO.getById(newCopy.getId());
        assertEquals("Lost", updatedCopy.getStatus());

        // Удалите запись после теста
        copyDAO.delete(newCopy.getId());
    }

    @Test
    void testGetAll() {
        Copy copy1 = new Copy(book, 1, "Available");
        Copy copy2 = new Copy(book, 2, "Borrowed");

        // Сохраняем обе копии
        copyDAO.insert(copy1);
        copyDAO.insert(copy2);

        // Проверяем, что можно получить список объектов
        List<Copy> allCopies = copyDAO.getAll();
        assertTrue(allCopies.size() >= 2);

        // Удаляем записи после тестов
        copyDAO.delete(copy1.getId());
        copyDAO.delete(copy2.getId());
    }

    @Test
    void testGetCount() {
        // Начальный подсчёт записей
        long initialCount = copyDAO.getCount();

        // Добавляем экземпляр для увеличения счётчика
        Copy newCopy = new Copy(book, 8, "Available");
        copyDAO.insert(newCopy);

        // Проверяем, что количество увеличилось
        long newCount = copyDAO.getCount();
        assertEquals(initialCount + 1, newCount);

        // Удаляем запись после теста
        copyDAO.delete(newCopy.getId());
    }
}