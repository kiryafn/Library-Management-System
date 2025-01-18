package com.library.library_management.data.presentation.controllers.admin;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PublisherDAO publisherDAO;

    public BookController(BookDAO bookDAO, PublisherDAO publisherDAO) {
        this.bookDAO = bookDAO;
        this.publisherDAO = publisherDAO;
    }

    @GetMapping
    public String showBooks(Model model) {
        List<Book> books = bookDAO.getAll();
        model.addAttribute("books", books);
        return "admin/views/view-book";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("publishers", publisherDAO.getAll());
        return "admin/updates/update-book";
    }

    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book) {
        bookDAO.update(book);
        return "redirect:/books";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("publishers", publisherDAO.getAll());
        return "admin/adds/add-book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookDAO.insert(book);
        return "redirect:/books";
    }
}