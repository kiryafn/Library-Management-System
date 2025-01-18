package com.library.library_management.data.presentation.controllers.admin;

import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.entities.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final PersonDAO personDAO;

    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String showPersons(Model model) {
        List<Person> persons = personDAO.getAll();
        model.addAttribute("persons", persons);
        return "admin/views/view-person";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/persons";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Person person = personDAO.getById(id);
        model.addAttribute("person", person);
        return "admin/updates/update-person";
    }

    @PostMapping("/update")
    public String updateBook(@ModelAttribute Person person) {
        personDAO.update(person);
        return "redirect:/persons";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("person", new Person());
        return "admin/adds/add-person";
    }

    @PostMapping("/add")
    public String addPerson(Person person) {
        personDAO.insert(person);
        return "redirect:/persons";
    }
}