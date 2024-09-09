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

    // Контроллер для удаления объявления
    @PostMapping("/delete/{id}")
    public String deleteAd(@PathVariable Long id) {
        Optional<Ad> adOptional = adService.findById(id);
        if (adOptional.isPresent()){
            adService.delete(adOptional.get());
        } else return "redirect:/error-page";
        return "redirect:/";
    }


}






