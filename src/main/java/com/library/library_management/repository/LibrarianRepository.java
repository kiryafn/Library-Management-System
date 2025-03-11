package com.library.library_management.repository;

import com.library.library_management.entities.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends CrudRepository<Librarian, Long> {
    Librarian findByUserEmail(String email);
}
