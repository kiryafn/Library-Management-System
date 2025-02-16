package com.library.library_management.data.repository;

import com.library.library_management.data.entities.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
}
