package com.library.library_management.controllers.admin;

import com.library.library_management.entities.Librarian;
import com.library.library_management.entities.User;
import com.library.library_management.services.LibrarianService;
import com.library.library_management.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/librarians")
public class LibrarianController {

    private final LibrarianService librarianService;

    private final UserService userService;

    public LibrarianController(LibrarianService librarianService, UserService userService) {
        this.librarianService = librarianService;
        this.userService = userService;
    }
    
    @GetMapping
    public String showLibrarians(Model model) {
        List<Librarian> librarians = librarianService.getAll();
        model.addAttribute("librarians", librarians);
        return "admin/views/view-librarian";
    }

    @PostMapping("/delete/{id}")
    public String deleteLibrarian(@PathVariable("id") Long id) {
        librarianService.delete(id);
        return "redirect:/librarians";
    }
    
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Librarian librarian = librarianService.getById(id);
        List<User> users = userService.getAll();
        model.addAttribute("librarian", librarian);
        model.addAttribute("users", users);
        return "admin/updates/update-librarian";
    }
    
    @PostMapping("/update")
    public String updateLibrarian(@ModelAttribute Librarian librarian) {
        librarianService.update(librarian);
        return "redirect:/librarians";
    }
    
    @PostMapping("/add")
    public String addLibrarian(@ModelAttribute Librarian librarian) {
        librarianService.insert(librarian);
        return "redirect:/librarians";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("librarian", new Librarian());
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "admin/adds/add-librarian";
    }
}
