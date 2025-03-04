package com.library.library_management.controllers.admin;

import com.library.library_management.entities.Publisher;
import com.library.library_management.services.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/publishers")
public class PublisherController {
    
    private final PublisherService publisherService;
    
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }
    
    @GetMapping
    public String showPublishers(Model model) {
        model.addAttribute("publishers", publisherService.getAll());
        return "admin/views/view-publisher";
    }
    
    @PostMapping("/delete/{id}")
    public String deletePublisher(@PathVariable("id") Long id) {
        publisherService.delete(id);
        return "redirect:/publishers";
    }
    
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Publisher publisher = publisherService.getById(id);
        model.addAttribute("publisher", publisher);
        return "admin/updates/update-publisher";
    }

    @PostMapping("/update")
    public String updatePublisher(@ModelAttribute Publisher publisher) {
        publisherService.update(publisher);
        return "redirect:/publishers";
    }
   
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "admin/adds/add-publisher";
    }
    
    @PostMapping("/add")
    public String addPublisher(@ModelAttribute Publisher publisher) {
        publisherService.insert(publisher);
        return "redirect:/publishers";
    }
}