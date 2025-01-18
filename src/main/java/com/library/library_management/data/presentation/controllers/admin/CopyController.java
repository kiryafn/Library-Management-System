package com.library.library_management.data.presentation.controllers.admin;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dao.CopyDAO;
import com.library.library_management.data.entities.Copy;
import com.library.library_management.data.entities.CopyStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing copies of books in the library management system.
 *
 * <p>The {@code CopyController} provides functionality for listing, adding, updating,
 * and deleting copies of books. Each copy represents a physical or digital instance of a book
 * along with its status (e.g., available, checked out, or reserved). This class interacts with the
 * data layer through the {@link CopyDAO} and {@link BookDAO} to manage copy-related operations
 * and to retrieve related book data.</p>
 *
 * <p>Marked with the {@link Controller} annotation, this class is integrated into the Spring MVC framework
 * and processes requests based on the {@code /copies} path.</p>
 */
@Controller
@RequestMapping("/copies")
public class CopyController {

    /**
     * Data Access Object (DAO) for managing copy-related operations in the database.
     */
    private final CopyDAO copyDAO;

    /**
     * Data Access Object (DAO) for accessing book-related data.
     */
    private final BookDAO bookDAO;

    /**
     * Constructs a new {@code CopyController} with the specified DAOs for copies and books.
     *
     * @param copyDAO the DAO used for copy-related operations
     * @param bookDAO the DAO used for book-related operations
     */
    public CopyController(CopyDAO copyDAO, BookDAO bookDAO) {
        this.copyDAO = copyDAO;
        this.bookDAO = bookDAO;
    }

    /**
     * Handles HTTP GET requests to display the list of all copies.
     *
     * <p>Fetches a list of copies from the data layer using {@link CopyDAO#getAll()} and
     * populates the model with the retrieved {@link Copy} objects. The returned view renders
     * the list of copies in the admin interface.</p>
     *
     * @param model the {@link Model} object used to pass attributes to the view
     * @return the name of the view template for displaying copies
     */
    @GetMapping
    public String showCopies(Model model) {
        List<Copy> copies = copyDAO.getAll();
        model.addAttribute("copies", copies);
        return "admin/views/view-copy";
    }

    /**
     * Handles HTTP POST requests to delete a copy by its ID.
     *
     * <p>Deletes the specified copy from the data layer using {@link CopyDAO#delete(int)}.
     * Redirects to the {@code /copies} endpoint after successful deletion.</p>
     *
     * @param id the ID of the copy to be deleted
     * @return a redirect to the copies list view
     */
    @PostMapping("/delete/{id}")
    public String deleteCopy(@PathVariable("id") int id) {
        copyDAO.delete(id);
        return "redirect:/copies";
    }

    /**
     * Handles HTTP GET requests to display the form for updating details of a copy.
     *
     * <p>Fetches the copy by ID using {@link CopyDAO#getById(int)} and retrieves
     * all available books and copy statuses. Populates the model with the data needed
     * to render the update form.</p>
     *
     * @param id the ID of the copy to update
     * @param model the {@link Model} object used to pass attributes to the view
     * @return the name of the view template for updating a copy
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Copy copy = copyDAO.getById(id);
        model.addAttribute("copy", copy);
        model.addAttribute("books", bookDAO.getAll());
        model.addAttribute("statuses", CopyStatus.values());
        return "admin/updates/update-copy";
    }

    /**
     * Handles HTTP POST requests to update a copy.
     *
     * <p>Updates the specified copy in the data layer using {@link CopyDAO#update(Copy)}.
     * Redirects to the {@code /copies} endpoint after the update is complete.</p>
     *
     * @param copy the updated {@link Copy} object to save
     * @return a redirect to the copies list view
     */
    @PostMapping("/update")
    public String updateCopy(@ModelAttribute Copy copy) {
        copyDAO.update(copy);
        return "redirect:/copies";
    }

    /**
     * Handles HTTP GET requests to display the form for adding a new copy.
     *
     * <p>Initializes a new {@link Copy} object and fetches the list of books and
     * available copy statuses using {@link BookDAO#getAll()} and {@link CopyStatus#values()}.
     * Populates the model with the data needed to render the add form.</p>
     *
     * @param model the {@link Model} object used to pass attributes to the view
     * @return the name of the view template for adding a new copy
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("copy", new Copy());
        model.addAttribute("books", bookDAO.getAll());
        model.addAttribute("statuses", CopyStatus.values());
        return "admin/adds/add-copy";
    }

    /**
     * Handles HTTP POST requests to add a new copy.
     *
     * <p>Inserts the new copy into the data layer using {@link CopyDAO#insert(Copy)}.
     * Redirects to the {@code /copies} endpoint after the copy has been added.</p>
     *
     * @param copy the new {@link Copy} object to add
     * @return a redirect to the copies list view
     */
    @PostMapping("/add")
    public String addCopy(@ModelAttribute Copy copy) {
        copyDAO.insert(copy);
        return "redirect:/copies";
    }
}