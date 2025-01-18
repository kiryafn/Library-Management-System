package com.library.library_management.data.presentation.controllers;

import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing the main entry points of the library management system.
 *
 * <p>This class provides endpoints for accessing the system's main page,
 * logging in as a user or librarian, and redirecting to the appropriate interface based
 * on the provided credentials.</p>
 *
 * <p>Annotated with {@link Controller}, this class is part of the Spring MVC framework
 * and serves as an entry point for different roles in the library system.</p>
 */
@Controller
public class MainController {

    /**
     * Data Access Object for managing user-related operations, especially for user authentication purposes.
     */
    private final PersonDAO personService;

    /**
     * Constructs an instance of {@code MainController} and injects the required {@link PersonDAO}.
     *
     * @param personService the DAO object used for performing operations related to {@link Person} entities
     */
    @Autowired
    public MainController(PersonDAO personService) {
        this.personService = personService;
    }

    /**
     * Displays the main page of the library management system.
     *
     * <p>This endpoint serves the main entry point of the system, allowing users to log in
     * or navigate to their respective roles (e.g., user or librarian).</p>
     *
     * @return the name of the main HTML page
     */
    @GetMapping
    public String showMainPage() {
        return "index"; // Return the main entry page
    }

    /**
     * Handles user login requests and redirects to the user-specific interface.
     *
     * <p>This endpoint authenticates the user by searching for their email in the database using
     * {@link PersonDAO#getByEmail(String)}. If the email is found, redirects to the user's home page;
     * otherwise, returns to the main page with an error message.</p>
     *
     * @param email the email entered by the user
     * @param model the {@link Model} object used to store attributes for rendering the view
     * @return the redirect URL for the user interface if authentication is successful; otherwise,
     * returns the main page with an error message
     */
    @PostMapping("/user")
    public String enterAsUser(@RequestParam("email") String email, Model model) {
        Person person = personService.getByEmail(email); // Search the user by email
        if (person != null) {
            return "redirect:/user/" + person.getId(); // Redirect to the user-specific route
        } else {
            model.addAttribute("error", "Пользователь с таким email не найден"); // Add error to the model
            return "index"; // Return to the main page with an error
        }
    }

    /**
     * Displays the main interface for librarians.
     *
     * <p>This endpoint allows librarians to access their dashboard or main operational page.</p>
     *
     * @return the name of the librarian-specific HTML page
     */
    @GetMapping("/librarian")
    public String enterAsLibrarian() {
        return "admin/main.html"; // Return the librarian interface page
    }
}