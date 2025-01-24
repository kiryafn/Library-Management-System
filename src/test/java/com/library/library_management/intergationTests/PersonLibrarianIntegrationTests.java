package com.library.library_management.intergationTests;

import com.library.library_management.data.dao.LibrarianDAO;
import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.entities.Librarian;
import com.library.library_management.data.entities.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Включает Spring Boot Test Context
@ActiveProfiles("test") // Устанавливает тестовую конфигурацию базы данных
public class PersonLibrarianIntegrationTests {

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private LibrarianDAO librarianDAO;

    private Person person;
    private Librarian librarian;

    @BeforeEach
    public void setUp() {
        // Создаем и сохраняем объект Person
        person = new Person("Alice Smith", "alice.smith@example.com", "987654321", "456 Example St");
        personDAO.insert(person);

        // Создаем объект Librarian
        librarian = new Librarian(person, LocalDate.now(), "Library Manager");
    }

    @AfterEach
    public void tearDown() {
        // Удаляем все записи из Librarian и Person
        librarianDAO.getAll().forEach(l -> librarianDAO.delete(l.getId()));
        personDAO.getAll().forEach(p -> personDAO.delete(p.getId()));
    }

    @Test
    public void testLinkPersonToLibrarian() {
        // Сохраняем Librarian, связанного с Person
        librarianDAO.insert(librarian);

        // Проверяем, что Librarian был сохранен корректно
        Librarian fetchedLibrarian = librarianDAO.getById(librarian.getId());
        assertNotNull(fetchedLibrarian);
        assertEquals(person.getId(), fetchedLibrarian.getPerson().getId());
        assertEquals("Library Manager", fetchedLibrarian.getPosition());

        // Проверяем, что у Person существует связь с Librarian
        Person fetchedPerson = personDAO.getById(person.getId());
        assertNotNull(fetchedPerson.getLibrarian());
        assertEquals(librarian.getId(), fetchedPerson.getLibrarian().getId());
    }

    @Test
    public void testUpdateLibrarianDetails() {
        // Сохраняем Librarian
        librarianDAO.insert(librarian);

        // Обновляем позицию Librarian
        librarian.setPosition("Senior Librarian");
        librarianDAO.update(librarian);

        // Проверяем, что изменения сохранены в базе данных
        Librarian updatedLibrarian = librarianDAO.getById(librarian.getId());
        assertNotNull(updatedLibrarian);
        assertEquals("Senior Librarian", updatedLibrarian.getPosition());
    }

    @Test
    public void testCreateMultipleLibrariansLinkedToDifferentPersons() {
        // Сохраняем первого Librarian
        librarianDAO.insert(librarian);

        // Создаем второго Person
        Person secondPerson = new Person("Bob Brown", "bob.brown@example.com", "111222333", "789 Another St");
        personDAO.insert(secondPerson);

        // Создаем второго Librarian для второго Person
        Librarian secondLibrarian = new Librarian(secondPerson, LocalDate.now(), "Assistant Librarian");
        librarianDAO.insert(secondLibrarian);

        // Проверяем, что оба Librarian сохранены
        assertEquals(2, librarianDAO.getAll().size());

        // Проверяем корректность связей
        Librarian firstFetchedLibrarian = librarianDAO.getById(librarian.getId());
        assertNotNull(firstFetchedLibrarian);
        assertEquals(person.getId(), firstFetchedLibrarian.getPerson().getId());

        Librarian secondFetchedLibrarian = librarianDAO.getById(secondLibrarian.getId());
        assertNotNull(secondFetchedLibrarian);
        assertEquals(secondPerson.getId(), secondFetchedLibrarian.getPerson().getId());
    }

    @Test
    public void testGetLibrarianFromPerson() {
        // Сохраняем Librarian, связанного с Person
        librarianDAO.insert(librarian);

        // Получаем Person и проверяем связь с Librarian
        Person fetchedPerson = personDAO.getById(person.getId());
        assertNotNull(fetchedPerson.getLibrarian());
        assertEquals(librarian.getId(), fetchedPerson.getLibrarian().getId());
    }
}