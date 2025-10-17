// src/main/java/com/example/inventory/web/GlobalExceptionHandler.java
package com.example.inventory.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleFK(DataIntegrityViolationException ex, Model model) {
        log.error("Constraint violation: {}", ex.getMostSpecificCause().getMessage());
        model.addAttribute("errorMessage", "Data constraint error: " + ex.getMostSpecificCause().getMessage());
        return "error"; // create a simple templates/error.html if you want
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        log.error("Unexpected error", ex);
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
