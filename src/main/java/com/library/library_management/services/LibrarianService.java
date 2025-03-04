package com.library.library_management.services;

import com.library.library_management.entities.Librarian;
import com.library.library_management.repository.LibrarianRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianService {

    private LibrarianRepository librarianRepository;

    public LibrarianService(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public List<Librarian> getAll() {
        return librarianRepository.findAll();
    }

    public void delete(Long id) {
        librarianRepository.deleteById(id);
    }

    public Librarian getById(Long id) {
        return librarianRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Librarian with ID" + id + "was not found."));
    }

    public void update(Librarian librarian) {
        librarianRepository.save(librarian);
    }

    public void insert(Librarian librarian) {
        librarianRepository.save(librarian);
    }
}
