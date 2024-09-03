package com.example.crudpractice.controllers;

import com.example.crudpractice.modeles.User;
import com.example.crudpractice.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user, Model model) {
        model.addAttribute("message", "Пользователь " + user.getName() + " успешно создан!");
        userService.save(user);
        return "redirect:/users/" + userService.save(user).getId();
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        if (userService.findById(id).isPresent()) {
            model.addAttribute("user", userService.findById(id).get());
            return "user-result";
        } else {
            return "redirect:/users/not-found";
        }
    }

    @GetMapping("/not-found")
    public String notFoundUser() {
        return "not-found-user";
    }
}
