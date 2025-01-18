package com.library.library_management.data.presentation.controllers.admin;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dao.CopyDAO;
import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Copy;
import com.library.library_management.data.entities.CopyStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/copies")
public class CopyController {

    private final CopyDAO copyDAO;
    private final BookDAO bookDAO;

    public CopyController(CopyDAO copyDAO, BookDAO bookDAO) {
        this.copyDAO = copyDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String showCopies(Model model) {
        List<Copy> copies = copyDAO.getAll();
        model.addAttribute("copies", copies);
        return "admin/views/view-copy";
    }

    @PostMapping("/delete/{id}")
    public String deleteCopy(@PathVariable("id") int id) {
        copyDAO.delete(id);
        return "redirect:/copies";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Copy copy = copyDAO.getById(id);
        model.addAttribute("copy", copy);
        model.addAttribute("books", bookDAO.getAll());
        model.addAttribute("statuses", CopyStatus.values());
        return "admin/updates/update-copy";
    }

    @PostMapping("/update")
    public String updateCopy(@ModelAttribute Copy copy) {
        copyDAO.update(copy);
        return "redirect:/copies";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("copy", new Copy());
        model.addAttribute("books", bookDAO.getAll());
        model.addAttribute("statuses", CopyStatus.values());
        return "admin/adds/add-copy";
    }

    @PostMapping("/add")
    public String addCopy(@ModelAttribute Copy copy) {
        copyDAO.insert(copy);
        return "redirect:/copies";
    }
}