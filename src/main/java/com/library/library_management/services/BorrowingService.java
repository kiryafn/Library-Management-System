package com.library.library_management.services;

import com.library.library_management.entities.Borrowing;
import com.library.library_management.entities.Copy;
import com.library.library_management.entities.CopyStatus;
import com.library.library_management.repository.BorrowingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Copy copy = copyService.getById(borrowing.getCopy().getId());
        if (copy != null) {
            copy.setStatus("Borrowed");
            copyService.update(copy);
        }

        borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getAll() {
        return borrowingRepository.findAll();
    }

    public Borrowing getById(Long id) {
        return borrowingRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Borrowing with ID" + id + "was not found."));
    }

    public void update(Borrowing borrowing) {
        if (borrowing.getReturnDate() != null && !CopyStatus.Lost.getName().equals(borrowing.getCopy().getStatus())) {
            borrowing.getCopy().setStatus(CopyStatus.Available.getName());
            copyService.update(borrowing.getCopy());
        }
        borrowingRepository.save(borrowing);
    }

    public void delete(Long id) {
        Borrowing borrowing = borrowingRepository.getById(id);

        if (borrowing.getReturnDate() != null && borrowing.getCopy().getStatus() != CopyStatus.Lost.getName()) {
            borrowingRepository.deleteById(id);
        }
    }

    public List<Borrowing> getByUserId(Long userId) {
        return borrowingRepository.getByUserId(userId);
    }
}