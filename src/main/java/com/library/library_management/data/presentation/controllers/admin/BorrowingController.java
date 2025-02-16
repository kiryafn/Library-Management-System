package com.library.library_management.data.presentation.controllers.admin;

import com.library.library_management.data.dao.BorrowingDAO;
import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.dao.CopyDAO;
import com.library.library_management.data.entities.Borrowing;
import com.library.library_management.data.entities.CopyStatus;
import com.library.library_management.data.entities.User;
import com.library.library_management.data.entities.Copy;
import com.library.library_management.domain.services.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing borrowings in the library management system.
 *
 * <p>The {@code BorrowingController} provides functionality for displaying, adding, updating,
 * and deleting borrowing records. Each borrowing links a {@link User} and a {@link Copy}
 * along with the dates for borrowing and returning of the book. This class interacts with
 * the data layer through the {@link BorrowingDAO}, {@link PersonDAO}, and {@link CopyDAO}.
 *
 * <p>Marked with the {@link Controller} annotation, this class is integrated into the Spring MVC framework
 * and processes requests based on the {@code /borrowings} path.
 */
@Controller
@RequestMapping("/borrowings")
public class BorrowingController {

    private final BorrowingDAO borrowingDAO;
    private final BorrowingService borrowingService;
    private final PersonDAO personDAO;
    private final CopyDAO copyDAO;

    @Autowired
    public BorrowingController(BorrowingDAO borrowingDAO, PersonDAO personDAO, CopyDAO copyDAO, BorrowingService borrowingService) {
        this.borrowingDAO = borrowingDAO;
        this.personDAO = personDAO;
        this.copyDAO = copyDAO;
        this.borrowingService = borrowingService;
    }

    /**
     * Handles HTTP GET requests to display the list of all borrowings.
     *
     * @param model the {@link Model} object used to pass attributes to the view
     * @return the name of the view template for displaying borrowings
     */
    @GetMapping
    public String showBorrowings(Model model) {
        List<Borrowing> borrowings = borrowingDAO.getAll();
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
        model.addAttribute("persons", personDAO.getAll());
        model.addAttribute("copies", copyDAO.getAllAvailable());
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
        borrowingService.addBorrowing(borrowing);
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
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Borrowing borrowing = borrowingDAO.getById(id);
        model.addAttribute("borrowing", borrowing);
        model.addAttribute("persons", personDAO.getAll());
        model.addAttribute("copies", copyDAO.getAll());
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
        // Проверка: указана ли дата возврата и статус экземпляра не равен Lost
        if (borrowing.getReturnDate() != null && !CopyStatus.Lost.getName().equals(borrowing.getCopy().getStatus())) {
            // Обновление статуса копии на "Available"
            borrowing.getCopy().setStatus(CopyStatus.Available.getName());
            copyDAO.update(borrowing.getCopy());
        }
        // Обновление Borrowing
        borrowingDAO.update(borrowing);
        return "redirect:/borrowings";
    }

    /**
     * Handles HTTP POST requests to delete a borrowing record by its ID.
     *
     * @param id the ID of the borrowing record to delete
     * @return a redirect to the borrowings list view
     */
    @PostMapping("/delete/{id}")
    public String deleteBorrowing(@PathVariable("id") int id) {
        Borrowing borrowing = borrowingDAO.getById(id);

        if (borrowing.getReturnDate() != null && borrowing.getCopy().getStatus() != CopyStatus.Lost.getName()) {
            borrowingDAO.delete(id);
        }

        return "redirect:/borrowings";
    }
}