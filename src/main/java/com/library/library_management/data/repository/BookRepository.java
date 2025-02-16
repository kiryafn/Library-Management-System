package com.library.library_management.data.repository;

import com.library.library_management.data.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT DISTINCT b FROM Book b JOIN Copy c ON b.id = c.book.id WHERE c.status = :status")
    Book findBookByCopiesStatus(String status);

    boolean existsByIsbn(String isbn);

    Book findByIsbn(String isbn);
}
