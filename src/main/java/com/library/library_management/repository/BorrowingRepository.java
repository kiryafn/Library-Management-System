package com.library.library_management.repository;

import com.library.library_management.entities.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRepository extends CrudRepository<Borrowing, Long> {
    public List<Borrowing> getByUserId(Long userId);
}
