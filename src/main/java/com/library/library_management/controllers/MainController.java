package com.library.library_management.controllers;

import com.library.library_management.entities.User;
import com.library.library_management.services.UserService;
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
    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
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
        return "index";
    }


    @PostMapping("/user")
    public String enterAsUser(@RequestParam("email") String email, Model model) {
        User user = userService.getByEmail(email); // Search the user by email
        if (user != null) {
            return "redirect:/user/" + user.getId(); // Redirect to the user-specific route
        } else {
            model.addAttribute("error", "Пользователь с таким email не найден"); // Add error to the model
            return "error/error-page";
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