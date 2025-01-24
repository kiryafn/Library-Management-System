package com.library.library_management.domain.services;

import com.library.library_management.data.dao.BorrowingDAO;
import com.library.library_management.data.dao.CopyDAO;
import com.library.library_management.data.entities.Borrowing;
import com.library.library_management.data.entities.Copy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer responsible for handling operations related to borrowing books in the library system.
 * <p>
 * This service interacts with Data Access Objects (DAOs) to manage borrowing records and update the status of copies
 * in the library database.
 * </p>
 *
 * <p>
 * It ensures transactional integrity during borrowing operations, such as adding a borrowing record and updating the
 * status of the borrowed copy.
 * </p>
 *
 * <strong>Responsibilities:</strong>
 * <ul>
 *     <li>Insert new borrowing records into the database</li>
 *     <li>Update the status of book copies when borrowed</li>
 * </ul>
 *
 * <p>
 * This class is annotated with {@link Service} to indicate it is a Spring-managed service component.
 * </p>
 *
 * @see BorrowingDAO
 * @see CopyDAO
 * @see Borrowing
 * @see Copy
 */
@Service
public class BorrowingService {

    /**
     * DAO for managing {@link Borrowing} entities in the database.
     */
    private final BorrowingDAO borrowingDAO;

    /**
     * DAO for managing {@link Copy} entities in the database.
     */
    private final CopyDAO copyDAO;

    /**
     * Constructs a new {@code BorrowingService} with the given DAO dependencies.
     *
     * @param borrowingDAO the {@link BorrowingDAO} to handle borrowing-related database operations
     * @param copyDAO the {@link CopyDAO} to handle copy-related database operations
     */
    public BorrowingService(BorrowingDAO borrowingDAO, CopyDAO copyDAO) {
        this.borrowingDAO = borrowingDAO;
        this.copyDAO = copyDAO;
    }

    /**
     * Adds a new borrowing record and updates the status of the corresponding copy to "Borrowed."
     * <p>
     * The method performs the following actions:
     * <ul>
     *     <li>Inserts the {@link Borrowing} entity into the database using {@link BorrowingDAO#insert(Borrowing)}</li>
     *     <li>Retrieves the associated {@link Copy} entity using {@link CopyDAO#getById(int)}</li>
     *     <li>If the {@link Copy} exists, its status is updated to "Borrowed" using {@link CopyDAO#update(Copy)}</li>
     * </ul>
     * </p>
     * <p>
     * This method is transactional. If any operation within this method fails, the entire transaction is rolled back.
     * </p>
     *
     * @param borrowing the {@link Borrowing} entity representing the borrowing details
     * @throws IllegalArgumentException if the {@link Borrowing} or associated {@link Copy} is null
     */
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