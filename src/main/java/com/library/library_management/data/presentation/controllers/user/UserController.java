package com.library.library_management.data.presentation.controllers.user;

import com.library.library_management.data.dao.BookDAO;
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

@Controller
@RequestMapping("/user")
public class UserController {

    private final BorrowingService borrowingService;
    private final BookService bookService;
    private final BookDAO bookDAO;

    @Autowired
    public UserController(BorrowingService borrowingService, BookDAO bookDAO, BookService bookService) {
        this.borrowingService = borrowingService;
        this.bookDAO = bookDAO;
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
        public String showUserInfo(@PathVariable("id") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/main.html";
    }

    @GetMapping("/books")
    public String showAllBooks(Model model){
        List<Book> books = bookDAO.getAll();
        model.addAttribute("books", books);
        return "user/views/view-book.html";
    }

    @GetMapping("/available-books")
    public String showAvaibleBooks(Model model){
        List<BookWithAvailableCopiesDTO> books = bookService.getBooksWithAvailableCopies();
        model.addAttribute("books", books);
        return "user/views/view-available-books";
    }

    @GetMapping("/{id}/borrowings")
    public String showUsersBorrowings(@PathVariable("id") String userId, Model model){
        List<Borrowing> borrowings = borrowingService.findBorrowingsByUserId(userId);
        model.addAttribute("borrowings", borrowings);
        model.addAttribute("userId", userId);
        return "user/views/view-borrowings.html";
    }
}