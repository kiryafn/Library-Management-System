package com.library.library_management.data.presentation.controllers.admin;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dao.LibrarianDAO;
import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.entities.Librarian;
import com.library.library_management.data.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/librarians")
public class LibrarianController {

    @Autowired
    private final LibrarianDAO librarianDAO;


    @Autowired
    private final PersonDAO personDAO;


    public LibrarianController(LibrarianDAO librarianDAO, PersonDAO personDAO) {
        this.librarianDAO = librarianDAO;
        this.personDAO = personDAO;
    }
    
    @GetMapping
    public String showLibrarians(Model model) {
        List<Librarian> librarians = librarianDAO.getAll();
        model.addAttribute("librarians", librarians);
        return "admin/views/view-librarian";
    }

    @PostMapping("/delete/{id}")
    public String deleteLibrarian(@PathVariable("id") int id) {
        librarianDAO.delete(id);
        return "redirect:/librarians";
    }
    
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Librarian librarian = librarianDAO.getById(id);
        List<Person> users = personDAO.getAll();
        model.addAttribute("librarian", librarian);
        model.addAttribute("users", users);
        return "admin/updates/update-librarian";
    }
    
    @PostMapping("/update")
    public String updateLibrarian(@ModelAttribute Librarian librarian) {
        librarianDAO.update(librarian);
        return "redirect:/librarians";
    }
    
    @PostMapping("/add")
    public String addLibrarian(@ModelAttribute Librarian librarian) {
        librarianDAO.insert(librarian);
        return "redirect:/librarians";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("librarian", new Librarian());
        List<Person> users = personDAO.getAll();
        model.addAttribute("users", users);
        return "admin/adds/add-librarian";
    }
}
