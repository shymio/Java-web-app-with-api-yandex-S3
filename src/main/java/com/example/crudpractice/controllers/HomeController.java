package com.example.crudpractice.controllers;

import com.example.crudpractice.modeles.Ad;
import com.example.crudpractice.services.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final AdService adService;

    @GetMapping
    public String showHomePage(Model model) {
        // Отображение главной страницы с помощью шаблонизатора
        return "home-page";
    }

    @GetMapping("/ads")
    @ResponseBody
    public List<Ad> getAllAds() {
        // Возвращаем все объявления в формате JSON
        return adService.findAll();
    }
}
