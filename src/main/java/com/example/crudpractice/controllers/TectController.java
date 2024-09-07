package com.example.crudpractice.controllers;

import com.example.crudpractice.modeles.Ad;
import com.example.crudpractice.services.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TectController {
    private final AdService adService;

    @GetMapping("/error-test")
    public String test(Model model) {
        model.addAttribute("errorMassage", "Вы не можете загрузить больше 3 фотографий");
        return "error-page";
    }
}
