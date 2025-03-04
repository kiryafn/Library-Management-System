package com.library.library_management.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/error-page";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(IllegalStateException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/error-page";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An error occurred. Please try later.");
        return "error/error-page";
    }
}