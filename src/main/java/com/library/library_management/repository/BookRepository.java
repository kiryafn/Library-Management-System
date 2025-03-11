package com.library.library_management.repository;

import com.library.library_management.entities.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Query("SELECT DISTINCT b FROM Book b JOIN Copy c ON b.id = c.book.id WHERE c.status = :status")
    List<Book> findBookByCopiesStatus(String status);

    boolean existsByIsbn(String isbn);

    Book findByIsbn(String isbn);
}
