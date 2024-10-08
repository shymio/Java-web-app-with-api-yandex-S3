package com.example.crudpractice.repositories;

import com.example.crudpractice.modeles.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findByTitle(String title);

    @Query("SELECT ad FROM Ad ad LEFT JOIN FETCH ad.photos WHERE ad.id = :id")
    Optional<Ad> findAdWithPhotos(@Param("id") Long id);


}
