package com.example.crudpractice.modeles;

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

//    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<AdPhoto> photos = new ArrayList<>();
}
