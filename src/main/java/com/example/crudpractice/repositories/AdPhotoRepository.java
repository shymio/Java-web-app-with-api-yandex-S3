package com.example.crudpractice.repositories;


import com.example.crudpractice.modeles.AdPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdPhotoRepository extends JpaRepository<AdPhoto, Long> {

}
