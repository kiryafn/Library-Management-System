package com.library.library_management.domain.services;

import com.library.library_management.data.dao.BorrowingDAO;
import com.library.library_management.data.dao.CopyDAO;
import com.library.library_management.data.entities.Copy;
import com.library.library_management.data.entities.Person;
import org.springframework.stereotype.Service;
import java.util.List;
import com.library.library_management.data.entities.Borrowing;

@Service
public class BorrowingService {
    private final BorrowingDAO borrowingDAO;
    private final CopyDAO copyDAO;

    public BorrowingService(BorrowingDAO borrowingDAO, CopyDAO copyDAO) {
        this.borrowingDAO = borrowingDAO;
        this.copyDAO = copyDAO;
    }

    public List<Borrowing> findBorrowingsByUserId(String userId) {
        return borrowingDAO.getByUserId(userId);
    }

    public List<Borrowing> findAllBorrowings() {
        return borrowingDAO.getAll();
    }

    public void addBorrowing(Borrowing borrowing) {
        Copy copy = borrowing.getCopy();
        Person person = borrowing.getPerson();

        if (!copy.isAvailable()) {
            throw new IllegalStateException("The copy is not available for borrowing.");
        }

        // Проверка: пользователь не может взять больше 5 книг одновременно
        List<Borrowing> activeBorrowings = borrowingDAO.getByUserId(String.valueOf(person.getId()));
        long activeBorrowingCount = activeBorrowings.stream()
                .filter(b -> b.getReturnDate() == null) // Учитываем только не возвращенные книги
                .count();
        if (activeBorrowingCount >= 5) {
            throw new IllegalStateException("User cannot borrow more than 5 books at a time.");
        }

        // Проверка: пользователь не может взять ту же книгу дважды, если возврат не был оформлен
        boolean hasSameBookBorrowed = activeBorrowings.stream()
                .anyMatch(b -> b.getCopy().getBook().getId().equals(copy.getBook().getId()) && b.getReturnDate() == null);
        if (hasSameBookBorrowed) {
            throw new IllegalStateException("User has already borrowed a copy of this book and hasn't returned it yet.");
        }

        // Все проверки пройдены, выполняем обновление статуса копии и сохраняем заем
        copy.markAsBorrowed();
        copyDAO.update(copy); // Обновляем статус в базе данных
        borrowingDAO.insert(borrowing); // Сохраняем новый заем
    }
}
