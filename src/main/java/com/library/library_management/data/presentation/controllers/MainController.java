package com.library.library_management.data.presentation.controllers;

import com.library.library_management.data.dao.PersonDAO;
import com.library.library_management.data.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    private final PersonDAO personService;

    @Autowired
    public MainController(PersonDAO personService) {
        this.personService = personService;
    }

    @GetMapping
    public String showMainPage() {
        return "index"; // Возвращаем главную страницу
    }

    @PostMapping("/user")
    public String enterAsUser(@RequestParam("email") String email, Model model) {
        Person person = personService.getByEmail(email); // Поиск пользователя по email
        if (person != null) {
            return "redirect:/user/" + person.getId(); // Перенаправление на маршрут пользователя
        } else {
            model.addAttribute("error", "Пользователь с таким email не найден");
            return "index"; // Вернуть на главную страницу с ошибкой
        }
    }

    @GetMapping("/librarian")
    public String enterAsLibrarian() {
        // Здесь логика отображения страницы библиотекаря
        return "admin/main.html"; // Здесь должна быть ваша HTML-страница для библиотекаря
    }
}