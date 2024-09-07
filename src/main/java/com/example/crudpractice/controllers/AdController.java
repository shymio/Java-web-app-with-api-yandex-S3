package com.example.crudpractice.controllers;

import com.example.crudpractice.modeles.Ad;
import com.example.crudpractice.modeles.AdPhoto;
import com.example.crudpractice.services.AdPhotoService;
import com.example.crudpractice.services.AdService;
import com.example.crudpractice.services.YandexS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {

    private final YandexS3Service yandexS3Service;
    private final AdService adService;
    private final AdPhotoService adPhotoService;

    @GetMapping("/new") // отображение формы для добавления объявления
    public String showAdForm(Model model) {
        model.addAttribute("ad", new Ad());
        return "ad-form";
    }

    @PostMapping("/add") // отправка и сохранение данных в хранилища
    public String addAd(@ModelAttribute Ad ad, @RequestParam(value = "photoFiles", required = false) List<MultipartFile> photoFiles) {
        if (photoFiles != null && !photoFiles.isEmpty()) {
            for (MultipartFile photo : photoFiles) {
                if (!photo.isEmpty()) {
                    try {
                        String photoUrl = yandexS3Service.uploadPhoto(photo);
                        AdPhoto adPhoto = new AdPhoto();
                        adPhoto.setUrl(photoUrl);
                        ad.addPhoto(adPhoto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        adService.save(ad);

        return "redirect:/ads/new";
    }
}






