package com.library.library_management.services;

import com.library.library_management.entities.User;
import com.library.library_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User with ID " + id + " was not found."));

        if (user.getBorrowings() != null && !user.getBorrowings().isEmpty()) {
            throw new IllegalStateException("Cannot delete user with active borrowings.");
        }

        userRepository.delete(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User with ID" + id + "was not found."));
    }

    public void update(User user) {
        validateUser(user);
        userRepository.findById(user.getId()).orElseThrow(() ->
                new IllegalArgumentException("User with ID " + user.getId() + " was not found."));
        userRepository.save(user);
    }

    public void insert(User user) {
        validateUser(user);
        userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }

        User testUser1 = userRepository.getByEmail(user.getEmail());
        if (testUser1 != null && !testUser1.getId().equals(user.getId())) {
            throw new IllegalStateException("User with email " + user.getEmail() + " already exists.");
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty.");
        }

        User testUser2 = userRepository.getByEmail(user.getEmail());
        if (testUser2 != null && !testUser2.getId().equals(user.getId())) {
            throw new IllegalStateException("User with email " + user.getEmail() + " already exists.");
        }
    }
}
