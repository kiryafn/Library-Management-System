package com.library.library_management.services;

import com.library.library_management.entities.Book;
import com.library.library_management.entities.Borrowing;
import com.library.library_management.entities.Copy;
import com.library.library_management.entities.CopyStatus;
import com.library.library_management.repository.CopyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CopyService {

    private final CopyRepository copyRepository;

    public CopyService(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    public Copy insert(Copy copy) {
        validateCopy(copy);
        return copyRepository.save(copy);
    }

    public Copy update(Copy copy) {
        validateCopy(copy);
        return copyRepository.save(copy);
    }

    public void delete(Long copyId) {
        Copy copy = copyRepository.findById(copyId).orElseThrow(() ->
                new IllegalArgumentException("Copy with ID" + copyId + "was not found."));

        if (!CopyStatus.Available.getName().equals(copy.getStatus())) {
            throw new IllegalStateException("You can not remove a copy that is not available.");
        }

        copyRepository.delete(copy);
    }

    public Copy getById(Long copyId) {
        return copyRepository.findById(copyId)
                .orElseThrow(() -> new IllegalArgumentException("Copy with ID " + copyId +" was not found."));
    }

    public List<Copy> getByBook(Book book) {
        return copyRepository.findByBook(book);
    }

    public List<Copy> getByStatus(String status) {
        return copyRepository.findByStatus(status);
    }

    public List<Copy> getAll() {
        List<Copy> copies = new ArrayList<>();
        copyRepository.findAll().forEach(copies::add);
        return copies;
    }

    private void validateCopy(Copy copy) {
        if (copy.getBook() == null || copy.getBook().getId() == null) {
            throw new IllegalArgumentException("The copy should be attached to the book.");
        }

        if (copy.getCopyNumber() == null || copy.getCopyNumber() <= 0) {
            throw new IllegalArgumentException("The copy should have a positive number.");
        }

        if (copy.getStatus() == null || copy.getStatus().isEmpty()) {
            throw new IllegalArgumentException("A copy must have a status.");
        }

        Optional<Copy> existingCopy = copyRepository.findByBookAndCopyNumber(copy.getBook(), copy.getCopyNumber());
        if (existingCopy.isPresent() && !existingCopy.get().getId().equals(copy.getId())) {
            throw new IllegalStateException("A copy with the number " + copy.getCopyNumber() +" already exists for this book.");
        }
    }
}
