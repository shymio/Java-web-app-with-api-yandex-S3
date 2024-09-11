package com.example.crudpractice.controllers;

import com.example.crudpractice.modeles.Ad;
import com.example.crudpractice.modeles.AdPhoto;
import com.example.crudpractice.services.AdPhotoService;
import com.example.crudpractice.services.AdService;
import com.example.crudpractice.services.YandexS3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {

    private final YandexS3Service yandexS3Service;
    private final AdService adService;
    private final AdPhotoService adPhotoService;

    @GetMapping("/new")
    public String showAdForm(Model model) {
        model.addAttribute("ad", new Ad());
        return "ad-form";
    }

    @PostMapping("/add")
    public String addAd(@ModelAttribute Ad ad, @RequestParam(value = "photoFiles", required = false) List<MultipartFile> photoFiles, Model model) {
        if (photoFiles != null && !photoFiles.isEmpty()) {
            if (photoFiles.size() > 3) {
                model.addAttribute("errorMassage", "Вы не можете загрузить больше 3 фотографий");
                return "error-page";
            }
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
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showAd(@PathVariable Long id, Model model) {
        Optional<Ad> ad = adService.findById(id);
        if(ad.isPresent()) {
            model.addAttribute("ad", ad.get());
            return "ad-details";
        } else return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteAd(@PathVariable Long id) {
        Optional<Ad> adOptional = adService.findById(id);
        if (adOptional.isPresent()){
            adService.delete(adOptional.get());
            List<AdPhoto> adPhotos = adOptional.get().getPhotos();
            for (AdPhoto adPhoto : adPhotos) {
                yandexS3Service.deletePhoto(adPhoto.getUrl());
            }
        } else return "redirect:/error-page";
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editAdForm(@PathVariable Long id, Model model) {
        Optional<Ad> adOptional = adService.findById(id);
        if (adOptional.isPresent()) {
            model.addAttribute("ad", adOptional.get());
            return "edit-ad";
        }
        return "redirect:/error-page";
    }

    @PostMapping("/edit/{id}")
    @Transactional
    public String editAd(@PathVariable Long id, @ModelAttribute Ad ad, @RequestParam(value = "photoFiles", required = false) List<MultipartFile> photoFiles) {
        Optional<Ad> adOptional = adService.findById(id);
        if (adOptional.isPresent()) {
            Ad existingAd = adOptional.get();
            existingAd.setTitle(ad.getTitle());
            existingAd.setPrice(ad.getPrice());
            existingAd.setDescription(ad.getDescription());
            existingAd.setContact(ad.getContact());

            // Добавление новых фотографий
            if (photoFiles != null && !photoFiles.isEmpty()) {
                // Удаление старых фотографий из хранилища
//                for (AdPhoto oldPhoto : existingAd.getPhotos()) {
//                    try {
//                        yandexS3Service.deletePhoto(oldPhoto.getUrl()); // Метод удаления фото из Yandex Object Storage
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                // Очистка старых фотографий из базы данных
                adPhotoService.deleteAdPhotos(existingAd.getId());
//                existingAd.getPhotos().clear();

                for (MultipartFile photo : photoFiles) {
                    if (!photo.isEmpty()) {
                        try {
                            String photoUrl = yandexS3Service.uploadPhoto(photo);
                            AdPhoto newPhoto = new AdPhoto();
                            newPhoto.setUrl(photoUrl);
                            existingAd.addPhoto(newPhoto);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            adService.save(existingAd);
            return "redirect:/";
        }
        return "redirect:/error-page";
    }
}






