package com.example.lab6.controllers;

import com.example.lab6.models.User;
import com.example.lab6.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/menu")
    public String showMainMenu() {
        return "users/menu";
    }

    @GetMapping
    public String listUsers(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<User> userPage = userService.getAllUsers(PageRequest.of(page, size));
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        return "users/list";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/add_user";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("user") User user, Model model) {
        try {
            userService.create(user);
            return "redirect:/api/v1/users";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "users/add_user";
        }
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/details";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @Valid @ModelAttribute("user") User updatedUser, Model model) {
        try {
            updatedUser.setId(id);
            userService.update(updatedUser);
            return "redirect:/api/v1/users";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "users/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        try {
            userService.delete(id);
            return "redirect:/api/v1/users";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "users/list";
        }
    }
}
