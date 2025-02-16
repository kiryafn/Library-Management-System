package com.library.library_management.domain.services;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dto.BookWithAvailableCopiesDTO;
import com.library.library_management.data.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    /**
     * Data Access Object for performing database operations on books.
     */
    private final BookDAO bookDAO;

    /**
     * Constructs a new {@code BookService} and injects the required {@link BookDAO}.
     *
     * @param bookDAO the DAO used for performing book-related database operations
     */
    @Autowired
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    /**
     * Retrieves a list of books along with the number of available copies for each book.
     *
     * <p>This method uses {@link BookDAO#findBooksWithAvailableCopies()} to fetch raw data from the database,
     * which includes a list of objects containing book details and their available copies. The data is then
     * transformed into a list of {@link BookWithAvailableCopiesDTO} objects for easier use in other parts of
     * the application.</p>
     *
     * @return a list of {@link BookWithAvailableCopiesDTO} objects, where each object contains
     *         a {@link Book} entity and the count of its available copies
     */
    public List<BookWithAvailableCopiesDTO> getBooksWithAvailableCopies() {
        List<Object[]> results = bookDAO.findBooksWithAvailableCopies();
        return results.stream()
                .map(result -> new BookWithAvailableCopiesDTO((Book) result[0], ((Long) result[1]).intValue()))
                .toList();
    }
}