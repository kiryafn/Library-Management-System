package com.library.library_management.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class responsible for managing admin-related views in the library management system.
 *
 * <p>The {@code AdminController} handles HTTP requests directed at the administrative section
 * of the application. It provides methods to render specific pages intended for administrative tasks.</p>
 *
 * <p>Marked with the {@link Controller} annotation, this class is a Spring MVC controller for
 * processing web-based requests and returning appropriate views.</p>
 */
@Controller
public class AdminController {

    /**
     * Handles HTTP GET requests to the {@code /tables} endpoint.
     *
     * <p>This method is responsible for displaying the main admin dashboard where tables and
     * administrative features are managed. It returns the view name corresponding to the
     * {@code admin/main.html} template.</p>
     *
     * @return the name of the HTML view to be rendered
     */
    @GetMapping("/tables")
    public String showTables() {
        return "admin/main.html";
    }
}