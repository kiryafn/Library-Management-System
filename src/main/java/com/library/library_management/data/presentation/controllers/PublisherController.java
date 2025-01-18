package com.library.library_management.data.presentation.controllers;

import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Publisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherDAO publisherDAO;

    public PublisherController(PublisherDAO publisherDAO) {
        this.publisherDAO = publisherDAO;
    }

    @GetMapping
    public String showPublishers(Model model) {
        List<Publisher> publishers = publisherDAO.getAll();
        model.addAttribute("publishers", publishers);
        return "views/view-publisher";
    }

    @PostMapping("/delete/{id}")
    public String deletePublisher(@PathVariable("id") int id) {
        publisherDAO.delete(id);
        return "redirect:/publishers";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Publisher publisher = publisherDAO.getById(id);
        model.addAttribute("publisher", publisher);
        return "updates/update-publisher";
    }

    @PostMapping("/update")
    public String updatePublisher(@ModelAttribute Publisher publisher) {
        // Вызов сервиса для обновления записи
        publisherDAO.update(publisher);
        // Перенаправляем обратно на страницу со списком издателей
        return "redirect:/publishers";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "adds/add-publisher";
    }

    // Обработка добавления нового издателя
    @PostMapping("/add")
    public String addPublisher(@ModelAttribute Publisher publisher) {
        publisherDAO.insert(publisher);
        return "redirect:/publishers";
    }
}