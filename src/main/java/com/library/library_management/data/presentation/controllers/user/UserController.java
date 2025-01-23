package com.library.library_management.data.presentation.controllers.user;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dao.BorrowingDAO;
import com.library.library_management.data.dto.BookWithAvailableCopiesDTO;
import com.library.library_management.data.entities.Book;
import com.library.library_management.data.entities.Borrowing;
import com.library.library_management.domain.services.BookService;
import com.library.library_management.domain.services.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller class for managing user-related operations in the library management system.
 *
 * <p>The {@code UserController} handles user-facing functionalities such as displaying user information,
 * listing all books, showing available books for borrowing, and providing details on a user's borrowings.
 * It interacts with the service and DAO layers to retrieve data and passes it to the appropriate view templates.</p>
 *
 * <p>This class is marked with the {@link Controller} annotation for integration with the Spring MVC framework,
 * processing requests prefixed with {@code /user}.</p>
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * Service for managing borrowing-related operations.
     */
    private final BorrowingDAO borrowingDAO;

    /**
     * Service for managing book-related operations and availability information.
     */
    private final BookService bookService;

    /**
     * Data Access Object for retrieving book data from the database.
     */
    private final BookDAO bookDAO;

    /**
     * Constructs a new {@code UserController} and injects the required dependencies.
     *
     * @param borrowingDAO the service used for borrowing-related operations
     * @param bookDAO the DAO used for book-related database operations
     * @param bookService the service used for managing book availability and related operations
     */
    @Autowired
    public UserController(BorrowingDAO borrowingDAO, BookDAO bookDAO, BookService bookService) {
        this.borrowingDAO = borrowingDAO;
        this.bookDAO = bookDAO;
        this.bookService = bookService;
    }

    /**
     * Displays the main page for a specific user.
     *
     * <p>Adds the provided user ID to the {@link Model}, enabling the front-end to display user-specific information.</p>
     *
     * @param userId the unique identifier for the user
     * @param model the {@link Model} object used to pass attributes to the view
     * @return the name of the main user view template
     */
    @GetMapping("/{id}")
    public String showUserInfo(@PathVariable("id") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/main.html";
    }

    /**
     * Displays a list of all books in the library system.
     *
     * <p>Uses the {@link BookDAO#getAll()} method to retrieve a list of all {@link Book} entities.
     * The list is added to the {@link Model} for rendering in the view template.</p>
     *
     * @param model the {@link Model} object used to pass data to the view
     * @return the name of the view template for displaying all books
     */
    @GetMapping("/books")
    public String showAllBooks(Model model) {
        List<Book> books = bookDAO.getAll();
        model.addAttribute("books", books);
        return "user/views/view-book.html";
    }

    /**
     * Displays a list of books that currently have available copies for borrowing.
     *
     * <p>Uses {@link BookService#getBooksWithAvailableCopies()} to retrieve a list of {@link BookWithAvailableCopiesDTO},
     * representing books with their corresponding available copies. The results are added to the {@link Model}
     * and passed to the view template.</p>
     *
     * @param model the {@link Model} object used to pass data to the view
     * @return the name of the view template for displaying books with available copies
     */
    @GetMapping("/available-books")
    public String showAvailableBooks(Model model) {
        List<BookWithAvailableCopiesDTO> books = bookService.getBooksWithAvailableCopies();
        model.addAttribute("books", books);
        return "user/views/view-available-books";
    }

    /**
     * Displays a list of borrowings for a specific user.
     *
     * <p>Retrieves all borrowings for the given user ID using {@link BorrowingDAO#getByUserId(String)}
     * and adds both the borrowings and user ID to the {@link Model}. The results are used to render a detailed view
     * of the user's borrowings.</p>
     *
     * @param userId the unique identifier of the user
     * @param model the {@link Model} object used to pass data to the view
     * @return the name of the view template for displaying a user's borrowings
     */
    @GetMapping("/{id}/borrowings")
    public String showUsersBorrowings(@PathVariable("id") String userId, Model model) {
        List<Borrowing> borrowings = borrowingDAO.getByUserId(userId);
        model.addAttribute("borrowings", borrowings);
        model.addAttribute("userId", userId);
        return "user/views/view-borrowings.html";
    }
}