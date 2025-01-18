package com.library.library_management.domain.services;

import com.library.library_management.data.dao.BorrowingDAO;
import com.library.library_management.data.dao.CopyDAO;
import com.library.library_management.data.entities.Copy;
import com.library.library_management.data.entities.Person;
import org.springframework.stereotype.Service;
import java.util.List;
import com.library.library_management.data.entities.Borrowing;

/**
 * Service class for managing borrowing operations in the library management system.
 *
 * <p>The {@code BorrowingService} handles all business logic related to the borrowing process,
 * including verifying borrowing rules, fetching borrowing records, and updating the
 * availability status of book copies.</p>
 *
 * <p>This class interacts with the {@link BorrowingDAO} and {@link CopyDAO} to perform
 * database operations and enforce system rules such as borrowing limits and availability checks.</p>
 *
 * <p>Annotated with {@link Service}, this class is a part of the Spring service layer that encapsulates
 * business logic and coordinates between data access and higher-level application layers.</p>
 */
@Service
public class BorrowingService {

    /**
     * Data Access Object for performing database operations related to borrowings.
     */
    private final BorrowingDAO borrowingDAO;

    /**
     * Data Access Object for performing database operations related to book copies.
     */
    private final CopyDAO copyDAO;

    /**
     * Constructs a new {@code BorrowingService} and injects the required dependencies.
     *
     * @param borrowingDAO the DAO used to manage borrowing-related operations
     * @param copyDAO the DAO used to manage copy-related operations
     */
    public BorrowingService(BorrowingDAO borrowingDAO, CopyDAO copyDAO) {
        this.borrowingDAO = borrowingDAO;
        this.copyDAO = copyDAO;
    }

    /**
     * Retrieves all borrowing records associated with a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a list of {@link Borrowing} records associated with the specified user
     */
    public List<Borrowing> findBorrowingsByUserId(String userId) {
        return borrowingDAO.getByUserId(userId);
    }

    /**
     * Retrieves all borrowing records in the system.
     *
     * @return a list of all {@link Borrowing} records stored in the database
     */
    public List<Borrowing> findAllBorrowings() {
        return borrowingDAO.getAll();
    }

    /**
     * Adds a new borrowing record to the system, applying all necessary validation and rule enforcement.
     *
     * <p>This method verifies the following rules before completing the borrowing operation:</p>
     * <ul>
     *   <li>The book copy must be available for borrowing.</li>
     *   <li>A user cannot borrow more than five books simultaneously.</li>
     *   <li>A user cannot borrow the same book twice simultaneously (without returning the first copy).</li>
     * </ul>
     *
     * <p>If all conditions are met, the borrowing record is persisted and the availability status
     * of the book copy is updated to reflect the borrowing.</p>
     *
     * @param borrowing the {@link Borrowing} instance representing the borrowing operation
     * @throws IllegalStateException if any of the borrowing rules are violated
     */
    public void addBorrowing(Borrowing borrowing) {
        Copy copy = borrowing.getCopy();
        Person person = borrowing.getPerson();

        if (!copy.isAvailable()) {
            throw new IllegalStateException("The copy is not available for borrowing.");
        }

        // Fetch active borrowings for the user
        List<Borrowing> activeBorrowings = borrowingDAO.getByUserId(String.valueOf(person.getId()));

        // Rule: User cannot borrow more than 5 books at the same time
        long activeBorrowingCount = activeBorrowings.stream()
                .filter(b -> b.getReturnDate() == null) // Count only non-returned books
                .count();
        if (activeBorrowingCount >= 5) {
            throw new IllegalStateException("User cannot borrow more than 5 books at a time.");
        }

        // Rule: User cannot borrow the same book twice if it hasn't been returned
        boolean hasSameBookBorrowed = activeBorrowings.stream()
                .anyMatch(b -> b.getCopy().getBook().getId().equals(copy.getBook().getId()) && b.getReturnDate() == null);
        if (hasSameBookBorrowed) {
            throw new IllegalStateException("User has already borrowed a copy of this book and hasn't returned it yet.");
        }

        // Update the status of the copy and save the borrowing record
        copy.markAsBorrowed();
        copyDAO.update(copy); // Save the updated status in the database
        borrowingDAO.insert(borrowing); // Save the new borrowing record
    }
}