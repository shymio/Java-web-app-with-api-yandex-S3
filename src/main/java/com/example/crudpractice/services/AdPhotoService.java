package com.example.crudpractice.services;

import com.example.crudpractice.modeles.AdPhoto;
import com.example.crudpractice.repositories.AdPhotoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdPhotoService {
    private final AdPhotoRepository adPhotoRepository;
    private final YandexS3Service yandexS3Service;

    public void saveAll(List<AdPhoto> adPhotos) {
        adPhotoRepository.saveAll(adPhotos);
    }

    @Transactional
    public void deleteAdPhotos(Long adId) {
        List<AdPhoto> photos = adPhotoRepository.findAllByAdId(adId);
        for (AdPhoto photo : photos) {
            try {
                yandexS3Service.deletePhoto(photo.getUrl());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        adPhotoRepository.deleteAll(photos);
    }
}
