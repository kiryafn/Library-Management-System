package com.library.library_management.repository;

import com.library.library_management.entities.Book;
import com.library.library_management.entities.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {
    List<Copy> findByBook(Book book);

    List<Copy> findByStatus(String status);

    Optional<Copy> findByBookAndCopyNumber(Book book, Integer copyNumber);
}
