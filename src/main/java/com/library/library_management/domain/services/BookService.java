package com.library.library_management.domain.services;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dto.BookWithAvailableCopiesDTO;
import com.library.library_management.data.entities.Book;
import com.library.library_management.data.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void insert(Book book) {
        validateBook(book);
        bookRepository.save(book);
    }

    public void update(Book book) {
        validateBook(book);
        bookRepository.save(book);
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Книга с id " + id + " не найдена."));

        if (book.getCopies() != null && book.getCopies().stream()
                .anyMatch(copy -> "ACTIVE".equalsIgnoreCase(copy.getStatus()))) {
            throw new IllegalStateException("Невозможно удалить книгу, у которой есть активные копии.");
        }

        bookRepository.delete(book);
    }

    private void validateBook(Book book) {
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            throw new IllegalArgumentException("ISBN can`t be empty.");
        }

        Book existingBook = bookRepository.findByIsbn(book.getIsbn());
        if (existingBook != null && !existingBook.getId().equals(book.getId())) {
            throw new IllegalStateException("Book with ISBN " + book.getIsbn() + " already exists.");
        }

        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title can`t be empty.");
        }
        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Author can`t be empty.");
        }

    }
}