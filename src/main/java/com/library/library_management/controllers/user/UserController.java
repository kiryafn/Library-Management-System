/*
package com.library.library_management.data.presentation.controllers.user;


import com.library.library_management.entities.Book;
import com.library.library_management.entities.Borrowing;
import com.library.library_management.services.BookService;
import com.library.library_management.services.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

*/
/**
 * Controller class for managing user-related operations in the library management system.
 *
 * <p>The {@code UserController} handles user-facing functionalities such as displaying user information,
 * listing all books, showing available books for borrowing, and providing details on a user's borrowings.
 * It interacts with the service and DAO layers to retrieve data and passes it to the appropriate view templates.</p>
 *
 * <p>This class is marked with the {@link Controller} annotation for integration with the Spring MVC framework,
 * processing requests prefixed with {@code /user}.</p>
 *//*

@Controller
@RequestMapping("/user")
public class UserController {

    */
/**
     * Service for managing borrowing-related operations.
     *//*

    private final BorrowingService borrowingService;

    */
/**
     * Service for managing book-related operations and availability information.
     *//*

    private final BookService bookService;


    @Autowired
    public UserController(BorrowingService borrowingService, BookService bookService) {
        this.borrowingService = borrowingService;
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public String showUserInfo(@PathVariable("id") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/main.html";
    }

    @GetMapping("/books")
    public String showAllBooks(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "user/views/view-book.html";
    }


   // @GetMapping("/available-books")
   // public String showAvailableBooks(Model model) {
        //List<BookWithAvailableCopiesDTO> books = bookService.getBooksWithAvailableCopies();
        //model.addAttribute("books", books);
        //return "user/views/view-available-books";
   // }

    @GetMapping("/{id}/borrowings")
    public String showUsersBorrowings(@PathVariable("id") Long userId, Model model) {
        List<Borrowing> borrowings = borrowingService.getByUserId(userId);
        model.addAttribute("borrowings", borrowings);
        model.addAttribute("userId", userId);
        return "user/views/view-borrowings.html";
    }
}*/


