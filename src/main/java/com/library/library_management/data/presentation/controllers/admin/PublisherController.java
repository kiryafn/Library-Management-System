package com.library.library_management.data.presentation.controllers.admin;

import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Publisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing publishers in the library management system.
 *
 * <p>The {@code PublisherController} facilitates the management of CRUD operations
 * (Create, Read, Update, Delete) for publishers. It interacts with the {@link PublisherDAO}
 * to perform database operations and communicates with view templates to display data
 * in the admin interface.</p>
 *
 * <p>Marked with the {@link Controller} annotation, this class is part of the Spring MVC framework
 * and processes HTTP requests under the base path {@code /publishers}.</p>
 */
@Controller
@RequestMapping("/publishers")
public class PublisherController {

    /**
     * Data Access Object for performing operations related to publishers.
     */
    private final PublisherDAO publisherDAO;

    /**
     * Creates an instance of {@code PublisherController} with the specified {@link PublisherDAO}.
     *
     * @param publisherDAO the DAO used for publisher-related operations
     */
    public PublisherController(PublisherDAO publisherDAO) {
        this.publisherDAO = publisherDAO;
    }

    /**
     * Handles HTTP GET requests to display the list of publishers.
     *
     * <p>Fetches all publishers from the database using {@link PublisherDAO#getAll()},
     * and adds the list to the {@link Model}. The returned view template renders the publisher list
     * in the administrative interface.</p>
     *
     * @param model the {@link Model} object used to pass data to the view
     * @return the name of the view template that displays the list of publishers
     */
    @GetMapping
    public String showPublishers(Model model) {
        List<Publisher> publishers = publisherDAO.getAll();
        model.addAttribute("publishers", publishers);
        return "admin/views/view-publisher";
    }

    /**
     * Handles HTTP POST requests to delete a publisher by its ID.
     *
     * <p>Deletes the publisher identified by the given {@code id} using {@link PublisherDAO#delete(int)}.
     * After successful deletion, redirects to the {@code /publishers} endpoint to display the updated list
     * of publishers.</p>
     *
     * @param id the ID of the publisher to be deleted
     * @return a redirect to the publisher list view
     */
    @PostMapping("/delete/{id}")
    public String deletePublisher(@PathVariable("id") int id) {
        publisherDAO.delete(id);
        return "redirect:/publishers";
    }

    /**
     * Handles HTTP GET requests to display the form for updating publisher details.
     *
     * <p>Fetches the publisher identified by the given {@code id} using {@link PublisherDAO#getById(int)},
     * and adds it to the {@link Model}. The returned view template renders a form for updating the
     * publisher's details.</p>
     *
     * @param id the ID of the publisher to update
     * @param model the {@link Model} object used to pass data to the view
     * @return the name of the view template for updating a publisher
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Publisher publisher = publisherDAO.getById(id);
        model.addAttribute("publisher", publisher);
        return "admin/updates/update-publisher";
    }

    /**
     * Handles HTTP POST requests to update a publisher's details.
     *
     * <p>Updates the specified {@link Publisher} entity in the database using
     * {@link PublisherDAO#update(Publisher)}. After the update, redirects to the
     * {@code /publishers} endpoint to display the updated list of publishers.</p>
     *
     * @param publisher the updated {@link Publisher} entity to save
     * @return a redirect to the publisher list view
     */
    @PostMapping("/update")
    public String updatePublisher(@ModelAttribute Publisher publisher) {
        publisherDAO.update(publisher);
        return "redirect:/publishers";
    }

    /**
     * Handles HTTP GET requests to display the form for adding a new publisher.
     *
     * <p>Initializes a new {@link Publisher} object and adds it to the {@link Model},
     * allowing the form to be rendered for creating a new publisher.</p>
     *
     * @param model the {@link Model} object used to pass data to the view
     * @return the name of the view template for adding a new publisher
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "admin/adds/add-publisher";
    }

    /**
     * Handles HTTP POST requests to add a new publisher.
     *
     * <p>Inserts the specified {@link Publisher} entity into the database using
     * {@link PublisherDAO#insert(Publisher)}. After the addition, redirects to the
     * {@code /publishers} endpoint to display the updated list of publishers.</p>
     *
     * @param publisher the {@link Publisher} entity to add
     * @return a redirect to the publisher list view
     */
    @PostMapping("/add")
    public String addPublisher(@ModelAttribute Publisher publisher) {
        publisherDAO.insert(publisher);
        return "redirect:/publishers";
    }
}