package com.library.library_management.services;

import com.library.library_management.entities.Borrowing;
import com.library.library_management.entities.Copy;
import com.library.library_management.entities.CopyStatus;
import com.library.library_management.entities.User;
import com.library.library_management.repository.BorrowingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;

    private final CopyService copyService;

    public BorrowingService(BorrowingRepository borrowingRepository, CopyService copyService) {
        this.borrowingRepository = borrowingRepository;
        this.copyService = copyService;
    }

    @Transactional
    public void insert(Borrowing borrowing) {
        validateBorrowing(borrowing);

        Copy copy = copyService.getById(borrowing.getCopy().getId());
        if (copy != null && CopyStatus.Available.getName().equals(copy.getStatus())) {
            copy.setStatus(CopyStatus.Borrowed.getName());
            copyService.update(copy);
        } else {
            throw new IllegalStateException("The copy is not available for borrowing.");
        }

        borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getAll() {
        List<Borrowing> borrowings = new ArrayList<>();
        borrowingRepository.findAll().forEach(borrowings::add);
        return borrowings;
    }

    public Borrowing getById(Long id) {
        return borrowingRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Borrowing with ID" + id + "was not found."));
    }

    @Transactional
    public void update(Borrowing borrowing) {
        validateBorrowing(borrowing);

        Borrowing existing = getById(borrowing.getId());

        if (borrowing.getReturnDate() != null) {
            Copy copy = borrowing.getCopy();
            if (copy != null) {
                copy.setStatus(CopyStatus.Available.getName());
                copyService.update(copy);
            }
        }

        borrowingRepository.save(borrowing);
    }

    public void delete(Long id) {
        Borrowing borrowing = getById(id);

        if (borrowing.getReturnDate() == null) {
            throw new IllegalStateException("Cannot delete borrowing that has not been returned.");
        }

        borrowingRepository.delete(borrowing);
    }

    public List<Borrowing> getByUserId(Long userId) {
        return borrowingRepository.getByUserId(userId);
    }

    private void validateBorrowing(Borrowing borrowing) {
        if (borrowing == null) {
            throw new IllegalArgumentException("Borrowing cannot be null.");
        }

        if (borrowing.getUser() == null || borrowing.getUser().getId() == null) {
            throw new IllegalArgumentException("Borrowing must have a valid user.");
        }

        if (borrowing.getCopy() == null || borrowing.getCopy().getId() == null) {
            throw new IllegalArgumentException("Borrowing must have a valid copy.");
        }

        if (borrowing.getBorrowDate() == null) {
            throw new IllegalArgumentException("Borrowing must have a borrow date.");
        }

        if (borrowing.getBorrowDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Borrow date cannot be in the future.");
        }

        if (borrowing.getReturnDate() != null && borrowing.getReturnDate().isBefore(borrowing.getBorrowDate())) {
            throw new IllegalArgumentException("Return date cannot be before borrow date.");
        }
    }
}