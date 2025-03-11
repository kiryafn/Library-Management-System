package com.library.library_management.controllers.admin;

import com.library.library_management.entities.User;
import com.library.library_management.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/persons")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showPersons(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("persons", users);
        return "admin/views/view-person";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/persons";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("person", user);
        return "admin/updates/update-person";
    }

    @PostMapping("/update")
    public String updateBook(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/persons";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("person", new User());
        return "admin/adds/add-person";
    }

    @PostMapping("/add")
    public String addPerson(User user) {
        userService.insert(user);
        return "redirect:/persons";
    }
}