package com.library.library_management.services;

import com.library.library_management.entities.Librarian;
import com.library.library_management.repository.LibrarianRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibrarianService {

    private final LibrarianRepository librarianRepository;

    public LibrarianService(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public List<Librarian> getAll() {
        List<Librarian> librarians = new ArrayList<>();
        librarianRepository.findAll().forEach(librarians::add);
        return librarians;
    }

    public void delete(Long id) {
        librarianRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Librarian with ID " + id + " was not found."));
        librarianRepository.deleteById(id);
    }

    public Librarian getById(Long id) {
        return librarianRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Librarian with ID" + id + "was not found."));
    }

    public void update(Librarian librarian) {
        validateLibrarian(librarian);
        librarianRepository.findById(librarian.getId()).orElseThrow(() ->
                new IllegalArgumentException("Librarian with ID " + librarian.getId() + " was not found."));
        librarianRepository.save(librarian);
    }
    public void insert(Librarian librarian) {
        validateLibrarian(librarian);
        librarianRepository.save(librarian);
    }

    private void validateLibrarian(Librarian librarian) {
        if (librarian == null) {
            throw new IllegalArgumentException("Librarian cannot be null.");
        }

        if (librarian.getUser() == null) {
            throw new IllegalArgumentException("A librarian must have an associated user.");
        }

        if (librarian.getUser().getEmail() == null || librarian.getUser().getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be empty.");
        }

        Librarian existingLibrarian = librarianRepository.findByUserEmail(librarian.getUser().getEmail());
        if (existingLibrarian != null && !existingLibrarian.getId().equals(librarian.getId())) {
            throw new IllegalStateException(
                    "A librarian associated with email " + librarian.getUser().getEmail() + " already exists."
            );
        }

        if (librarian.getEmploymentDate() == null) {
            throw new IllegalArgumentException("Employment date cannot be null.");
        }

        if (librarian.getEmploymentDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Employment date cannot be in the future.");
        }

        if (librarian.getPosition() == null || librarian.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("Position cannot be empty.");
        }
    }
}
