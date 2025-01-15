package com.library.library_management.data.presentation.controllers;

import com.library.library_management.data.entities.Publisher;
import com.library.library_management.domain.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/api")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/publishers")
    public String showUsers(Model model) {
        Iterable<Publisher> publishers = publisherService.getAllPublishers();
        model.addAttribute("publishers", publishers);
        return "publisherView";
    }
}