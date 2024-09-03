package com.example.crudpractice.services;

import com.example.crudpractice.modeles.AdPhoto;
import com.example.crudpractice.repositories.AdPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdPhotoService {
    private final AdPhotoRepository adPhotoRepository;

    public void saveAll(List<AdPhoto> adPhotos) {
        adPhotoRepository.saveAll(adPhotos);
    }
}
