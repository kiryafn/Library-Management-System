package com.library.library_management.data.repository;

import com.library.library_management.data.entities.Book;
import com.library.library_management.data.entities.Copy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CopyRepository extends JpaRepository<Copy, Long>{
    List<Copy> findByBook(Book book);

    List<Copy> findByStatus(String status);
}
