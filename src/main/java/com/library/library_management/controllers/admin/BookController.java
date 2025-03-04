package com.library.library_management.controllers.admin;

import com.library.library_management.entities.Book;
import com.library.library_management.services.BookService;
import com.library.library_management.services.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    private final PublisherService publisherService;

    public BookController(BookService bookService, PublisherService publisherService) {
        this.bookService = bookService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public String showBooks(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "admin/views/view-book";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("publishers", publisherService.getAll());
        return "admin/updates/update-book";
    }

    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book) {
        bookService.update(book);
        return "redirect:/books";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("publishers", publisherService.getAll());
        return "admin/adds/add-book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.insert(book);
        return "redirect:/books";
    }
}