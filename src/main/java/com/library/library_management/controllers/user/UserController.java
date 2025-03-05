package com.library.library_management.controllers.user;


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
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final BorrowingService borrowingService;

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


    @GetMapping("/available-books")
    public String showAvailableBooks(Model model) {
        Map<Book, Integer> books = bookService.getAvailableBooksWithNumberOfCopies();
        model.addAttribute("books", books);
        return "user/views/view-available-books";
   }

    @GetMapping("/{id}/borrowings")
    public String showUsersBorrowings(@PathVariable("id") Long userId, Model model) {
        List<Borrowing> borrowings = borrowingService.getByUserId(userId);
        model.addAttribute("borrowings", borrowings);
        model.addAttribute("userId", userId);
        return "user/views/view-borrowings.html";
    }
}


