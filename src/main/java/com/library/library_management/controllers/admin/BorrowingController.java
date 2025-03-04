package com.library.library_management.controllers.admin;

import com.library.library_management.entities.Borrowing;
import com.library.library_management.entities.CopyStatus;
import com.library.library_management.services.BorrowingService;
import com.library.library_management.services.CopyService;
import com.library.library_management.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {

    private final BorrowingService borrowingService;
    private final UserService userService;
    private final CopyService copyService;

    public BorrowingController(BorrowingService borrowingService, UserService userService, CopyService copyService) {
        this.borrowingService = borrowingService;
        this.userService = userService;
        this.copyService = copyService;
    }

    @GetMapping
    public String showBorrowings(Model model) {
        List<Borrowing> borrowings = borrowingService.getAll();
        model.addAttribute("borrowings", borrowings);
        return "admin/views/view-borrowing";
    }

    /**
     * Handles HTTP GET requests to display the form for adding a new borrowing record.
     *
     * @param model the {@link Model} object used to pass attributes to the view
     * @return the name of the view template for adding a new borrowing record
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("borrowing", new Borrowing());
        model.addAttribute("persons", userService.getAll());
        model.addAttribute("copies", copyService.getByStatus(CopyStatus.Available.getName()));
        return "admin/adds/add-borrowing";
    }

    /**
     * Handles HTTP POST requests to add a new borrowing record.
     *
     * @param borrowing the {@link Borrowing} object to save
     * @return a redirect to the borrowings list view
     */
    @PostMapping("/add")
    public String addBorrowing(@ModelAttribute Borrowing borrowing) {
        borrowingService.insert(borrowing);
        return "redirect:/borrowings";
    }

    /**
     * Handles HTTP GET requests to display the form for updating an existing borrowing record.
     *
     * @param id the ID of the borrowing record to update
     * @param model the {@link Model} object used to pass attributes to the view
     * @return the name of the view template for updating a borrowing record
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("borrowing", borrowingService.getById(id));
        model.addAttribute("persons", userService.getAll());
        model.addAttribute("copies", copyService.getAll());
        model.addAttribute("statuses", CopyStatus.values());
        return "admin/updates/update-borrowing";
    }

    /**
     * Handles HTTP POST requests to update an existing borrowing record.
     *
     * @param borrowing the updated {@link Borrowing} object to save
     * @return a redirect to the borrowings list view
     */
    @PostMapping("/update")
    public String updateBorrowing(@ModelAttribute Borrowing borrowing) {
        borrowingService.update(borrowing);
        return "redirect:/borrowings";
    }

    /**
     * Handles HTTP POST requests to delete a borrowing record by its ID.
     *
     * @param id the ID of the borrowing record to delete
     * @return a redirect to the borrowings list view
     */
    @PostMapping("/delete/{id}")
    public String deleteBorrowing(@PathVariable("id") Long id) {
        borrowingService.delete(id);
        return "redirect:/borrowings";
    }
}