package com.library.library_management.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/tables")
    public String showTables() {
        return "admin/main.html";
    }
}