package com.library.library_management.data.presentation.controllers.admin;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class responsible for managing books within the library management system.
 *
 * <p>This class handles HTTP requests related to book management, including listing,
 * adding, updating, and deleting books. It uses the {@link BookDAO} and {@link PublisherDAO}
 * to interact with the data layer and facilitates communication between the system and
 * the user interface through view templates.</p>
 *
 * <p>Marked with the {@link Controller} annotation, this class is part of the Spring MVC
 * framework and processes requests under the {@code /books} path.</p>
 */
@Controller
@RequestMapping("/books")
public class BookController {

    /**
     * Data Access Object (DAO) for interacting with book-related data.
     */
    private final BookDAO bookDAO;

    /**
     * Data Access Object (DAO) for interacting with publisher-related data.
     */
    private final PublisherDAO publisherDAO;

    /**
     * Constructs a new {@code BookController} with the specified DAOs for books and publishers.
     *
     * @param bookDAO the DAO used for book-related operations
     * @param publisherDAO the DAO used for publisher-related operations
     */
    public BookController(BookDAO bookDAO, PublisherDAO publisherDAO) {
        this.bookDAO = bookDAO;
        this.publisherDAO = publisherDAO;
    }

    /**
     * Handles HTTP GET requests to display the list of all books.
     *
     * <p>Fetches a list of books from the data layer using the {@link BookDAO#getAll()} method
     * and populates the model with it. The returned view template renders these books for the
     * admin interface.</p>
     *
     * @param model the {@link Model} object used to pass attributes to the view
     * @return the name of the view template for displaying books
     */
    @GetMapping
    public String showBooks(Model model) {
        List<Book> books = bookDAO.getAll();
        model.addAttribute("books", books);
        return "admin/views/view-book";
    }

    /**
     * Handles HTTP POST requests to delete a book by its ID.
     *
     * <p>Uses the {@link BookDAO#delete(int)} method to delete the specified book
     * from the data layer. Redirects to the {@code /books} endpoint after successful deletion.</p>
     *
     * @param id the ID of the book to be deleted
     * @return a redirect to the books list view
     */
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    /**
     * Handles HTTP GET requests to display the form for updating a specific book.
     *
     * <p>Fetches the book by its ID using {@link BookDAO#getById(int)} and the list of
     * publishers using {@link PublisherDAO#getAll()}. Populates the model with these details
     * to render the update form.</p>
     *
     * @param id the ID of the book to update
     * @param model the {@link Model} object used to pass attributes to the view
     * @return the name of the view template for updating the book
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("publishers", publisherDAO.getAll());
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