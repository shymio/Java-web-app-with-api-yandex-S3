package com.example.crudpractice.controllers;

import com.example.crudpractice.modeles.Ad;
import com.example.crudpractice.services.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TectController {
    private final AdService adService;

    @GetMapping("/test/{id}")
    public Optional<Ad> test(@PathVariable Long id) {
        return adService.findAdWithPhotos(id);
    }
}
