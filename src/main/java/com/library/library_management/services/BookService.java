package com.library.library_management.services;

import com.library.library_management.entities.Book;
import com.library.library_management.entities.CopyStatus;
import com.library.library_management.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public List<Book> getAll() {
        return (List<Book>) bookRepository.findAll();
    }

    private void validateBook(Book book){
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

    public List<Book> getAvailableBooks(){
        return bookRepository.findBookByCopiesStatus(CopyStatus.Available.getName());
    }

    public Map<Book, Integer> getAvailableBooksWithNumberOfCopies(){
        Map<Book, Integer> books = getAvailableBooks()
                .stream().collect(Collectors.toMap(Function.identity(), book -> book.getNumberOfCopies()));

        return books;
    }
}