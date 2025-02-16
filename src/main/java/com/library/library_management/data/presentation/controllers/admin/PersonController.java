package com.library.library_management.data.presentation.controllers.admin;

import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing persons (e.g., library members or staff) in the library management system.
 *
 * <p>The {@code PersonController} provides endpoints to handle CRUD (Create, Read, Update, Delete)
 * operations associated with persons. It interacts with the {@link PersonDAO} to perform database
 * operations and communicates with view templates to display data for the administrative interface.</p>
 *
 * <p>Marked with the {@link Controller} annotation, this class functions as part of the Spring MVC framework,
 * processing requests with the base path {@code /persons}.</p>
 */
@Controller
@RequestMapping("/persons")
public class PersonController {

    /**
     * Data Access Object (DAO) for managing operations related to persons in the database.
     */
    private final PersonDAO personDAO;

    /**
     * Constructs a new {@code PersonController} with the specified {@link PersonDAO}.
     *
     * @param personDAO the DAO used for person-related operations
     */
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    /**
     * Handles HTTP GET requests to display the list of persons.
     *
     * <p>Fetches a list of all registered persons from the data layer using {@link PersonDAO#getAll()},
     * and populates the provided {@link Model} with the retrieved {@link User} objects.
     * The returned view template renders the person list in the administrative interface.</p>
     *
     * @param model the {@link Model} object used to pass data to the view template
     * @return the name of the view template for displaying the persons list
     */
    @GetMapping
    public String showPersons(Model model) {
        List<User> users = personDAO.getAll();
        model.addAttribute("persons", users);
        return "admin/views/view-person";
    }

    /**
     * Handles HTTP POST requests to delete a person by their ID.
     *
     * <p>Removes the person identified by the given {@code id} from the database
     * using {@link PersonDAO#delete(int)}, and redirects to the {@code /persons} endpoint
     * after deletion.</p>
     *
     * @param id the ID of the person to delete
     * @return a redirect to the persons list view
     */
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/persons";
    }

    /**
     * Handles HTTP GET requests to display the form for updating a person's details.
     *
     * <p>Fetches the person identified by the given {@code id} using {@link PersonDAO#getById(int)}
     * and populates the model with the retrieved {@link User} object.
     * The returned view template renders the form for updating the person's information.</p>
     *
     * @param id the ID of the person to update
     * @param model the {@link Model} object used to pass data to the view template
     * @return the name of the view template for updating a person
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        User user = personDAO.getById(id);
        model.addAttribute("person", user);
        return "admin/updates/update-person";
    }

    /**
     * Handles HTTP POST requests to update a user's details.
     *
     * <p>Updates the specified {@link User} object in the database using {@link PersonDAO#update(User)}.
     * After updating the user, redirects to the {@code /persons} endpoint to display the updated list of persons.</p>
     *
     * @param user the updated {@link User} object to save
     * @return a redirect to the persons list view
     */
    @PostMapping("/update")
    public String updateBook(@ModelAttribute User user) {
        personDAO.update(user);
        return "redirect:/persons";
    }

    /**
     * Handles HTTP GET requests to display the form for adding a new person.
     *
     * <p>Initializes a new {@link User} object and populates the model with it to render the form
     * for adding a person in the admin interface.</p>
     *
     * @param model the {@link Model} object used to pass data to the view template
     * @return the name of the view template for creating a new person
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("person", new User());
        return "admin/adds/add-person";
    }

    /**
     * Handles HTTP POST requests to add a new user.
     *
     * <p>Adds the specified {@link User} object to the database using {@link PersonDAO#insert(User)},
     * and redirects to the {@code /persons} endpoint to show the updated list of persons.</p>
     *
     * @param user the new {@link User} object to add
     * @return a redirect to the persons list view
     */
    @PostMapping("/add")
    public String addPerson(User user) {
        personDAO.insert(user);
        return "redirect:/persons";
    }
}