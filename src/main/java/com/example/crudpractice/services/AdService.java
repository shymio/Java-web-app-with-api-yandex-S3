package com.example.crudpractice.services;

import com.example.crudpractice.modeles.Ad;
import com.example.crudpractice.repositories.AdRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepository;

    public Optional<Ad> findById(Long id) {
        return adRepository.findById(id);
    }

    public List<Ad> findByTitle(String title) {
        return adRepository.findByTitle(title);
    }

    public Optional<Ad> findAdWithPhotos(Long id) {
        return adRepository.findAdWithPhotos(id);
    }

    public List<Ad> findAll() {
        return adRepository.findAll();
    }

    public void save(Ad ad) {
        adRepository.save(ad);
    }

    public void delete(Ad ad) {
        adRepository.delete(ad);
    }
}
