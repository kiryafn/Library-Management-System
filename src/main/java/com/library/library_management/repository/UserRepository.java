package com.library.library_management.repository;

import com.library.library_management.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User getByEmail(String email);

    User getByPhoneNumber(String phoneNumber);
}
