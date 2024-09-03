package com.example.crudpractice.repositories;

import com.example.crudpractice.modeles.Ad;
import com.example.crudpractice.modeles.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findByTitle(String title);

//    @Query("SELECT a FROM Ad a LEFT JOIN FETCH a.photos WHERE a.id = :id")
//    Optional<Ad> findByIdWithPhotos(@Param("id") Long id);

}
