package com.example.crudpractice.controllers;

import com.example.crudpractice.modeles.Ad;
import com.example.crudpractice.modeles.AdPhoto;
import com.example.crudpractice.services.AdPhotoService;
import com.example.crudpractice.services.AdService;
import com.example.crudpractice.services.YandexS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String addAd(Ad ad, @RequestParam(value = "photos", required = false) List<MultipartFile> photos) {
        List<AdPhoto> photoEntities = new ArrayList<>();

        if (photos != null && !photos.isEmpty()) {
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    try {
                        AdPhoto adPhoto = new AdPhoto();
                        adPhoto.setUrl(yandexS3Service.uploadPhoto(photo));
                        adPhoto.setAd(ad);
                        photoEntities.add(adPhoto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
//        ad.setPhotos(photoEntities);
        adService.save(ad);
        adPhotoService.saveAll(photoEntities);

        return "redirect:/";
    }
}






