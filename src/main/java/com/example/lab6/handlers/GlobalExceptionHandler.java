package com.example.lab6.handlers;

import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleSideExceptions(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "users/error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handlerValidationExceptions(MethodArgumentNotValidException ex, Model model) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        model.addAttribute("errorMessage", errors.toString());
        return "users/error";
    }
}
