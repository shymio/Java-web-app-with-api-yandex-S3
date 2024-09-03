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

    @ElementCollection
    private List<String> photos = new ArrayList<>();
}
