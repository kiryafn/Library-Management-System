package com.library.library_management.domain.services;

import com.library.library_management.data.dao.BorrowingDAO;
import com.library.library_management.data.dao.CopyDAO;
import com.library.library_management.data.entities.Borrowing;
import com.library.library_management.data.entities.Copy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowingService {

    private final BorrowingDAO borrowingDAO;
    private final CopyDAO copyDAO;

    public BorrowingService(BorrowingDAO borrowingDAO, CopyDAO copyDAO) {
        this.borrowingDAO = borrowingDAO;
        this.copyDAO = copyDAO;
    }

    @Transactional
    public void addBorrowing(Borrowing borrowing) {
        borrowingDAO.insert(borrowing);

        Copy copy = copyDAO.getById(borrowing.getCopy().getId());
        if (copy != null) {
            copy.setStatus("Borrowed");
            copyDAO.update(copy);
        }
    }
}