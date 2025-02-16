package com.library.library_management.domain.services;

import com.library.library_management.data.entities.Borrowing;
import com.library.library_management.data.entities.Copy;
import com.library.library_management.data.repository.BorrowingRepository;
import com.library.library_management.data.repository.CopyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowingService {

    /**
     * DAO for managing {@link Borrowing} entities in the database.
     */
    private final BorrowingRepository borrowingRepository;

    /**
     * DAO for managing {@link Copy} entities in the database.
     */
    private final CopyRepository copyRepository;

    public BorrowingService(BorrowingRepository borrowingRepository, CopyRepository copyRepository) {
        this.borrowingRepository = borrowingRepository;
        this.copyRepository = copyRepository;
    }

    @Transactional
    public void addBorrowing(Borrowing borrowing) {
        borrowingRepository.insert(borrowing);

        Copy copy = copyDAO.getById(borrowing.getCopy().getId());
        if (copy != null) {
            copy.setStatus("Borrowed");
            copyDAO.update(copy);
        }
    }
}