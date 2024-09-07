package com.example.crudpractice.modeles;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    private int price;
    private String description;
    @NonNull
    private String contact;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<AdPhoto> photos = new ArrayList<>();

    public void addPhoto(AdPhoto adPhoto) {
        photos.add(adPhoto);
        adPhoto.setAd(this);
    }

    public void removePhoto(AdPhoto adPhoto) {
        photos.remove(adPhoto);
        adPhoto.setAd(null);
    }
}
