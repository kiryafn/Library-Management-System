package com.library.library_management.data.presentation.controllers.admin;

import com.library.library_management.data.entities.Book;
import com.library.library_management.data.repository.BookRepository;
import com.library.library_management.data.repository.PublisherRepository;
import com.library.library_management.domain.services.BookService;
import com.library.library_management.domain.services.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Flow;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    private final PublisherService publisherService;
    private final BookRepository bookRepository;

    public BookController(BookService bookService, PublisherService publisherService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.publisherService = publisherService;
        this.bookRepository = bookRepository;
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

    /**
     * Handles HTTP POST requests to update a book.
     *
     * <p>Updates the specified book in the data layer using {@link BookDAO#update(Book)}.
     * Redirects to the {@code /books} endpoint after the update is complete.</p>
     *
     * @param book the updated {@link Book} object to save
     * @return a redirect to the books list view
     */
    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book) {
        bookDAO.update(book);
        return "redirect:/books";
    }

    /**
     * Handles HTTP GET requests to display the form for adding a new book.
     *
     * <p>Initializes a new {@link Book} object and fetches the list of publishers
     * using {@link PublisherDAO#getAll()}. Populates the model with these details to
     * render the add form.</p>
     *
     * @param model the {@link Model} object used to pass attributes to the view
     * @return the name of the view template for adding a new book
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("publishers", publisherDAO.getAll());
        return "admin/adds/add-book";
    }

    /**
     * Handles HTTP POST requests to add a new book.
     *
     * <p>Inserts the new book into the data layer using {@link BookDAO#insert(Book)}.
     * Redirects to the {@code /books} endpoint after the book has been added.</p>
     *
     * @param book the new {@link Book} object to add
     * @return a redirect to the books list view
     */
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookDAO.insert(book);
        return "redirect:/books";
    }
}