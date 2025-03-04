package com.library.library_management.services;

import com.library.library_management.entities.User;
import com.library.library_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User with ID" + id + "was not found."));
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void insert(User user) {
        userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}
