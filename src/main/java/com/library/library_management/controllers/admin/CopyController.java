package com.library.library_management.controllers.admin;

import com.library.library_management.entities.Copy;
import com.library.library_management.entities.CopyStatus;
import com.library.library_management.repository.CopyRepository;
import com.library.library_management.services.BookService;
import com.library.library_management.services.CopyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/copies")
public class CopyController {

    private final CopyService copyService;

    private final BookService bookService;
    private final CopyRepository copyRepository;

    public CopyController(CopyService copyService, BookService bookService, CopyRepository copyRepository) {
        this.copyService = copyService;
        this.bookService = bookService;
        this.copyRepository = copyRepository;
    }

    @GetMapping
    public String showCopies(Model model) {
        List<Copy> copies = copyService.getAll();
        model.addAttribute("copies", copies);
        return "admin/views/view-copy";
    }

    @PostMapping("/delete/{id}")
    public String deleteCopy(@PathVariable("id") Long id) {
        copyService.delete(id);
        return "redirect:/copies";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Copy copy = copyService.getById(id);
        model.addAttribute("copy", copy);
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("statuses", CopyStatus.values());
        return "admin/updates/update-copy";
    }

    @PostMapping("/update")
    public String updateCopy(@ModelAttribute Copy copy) {
        copyService.update(copy);
        return "redirect:/copies";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("copy", new Copy());
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("statuses", CopyStatus.values());
        return "admin/adds/add-copy";
    }

    @PostMapping("/add")
    public String addCopy(@ModelAttribute Copy copy) {
        copyService.insert(copy);
        return "redirect:/copies";
    }
}