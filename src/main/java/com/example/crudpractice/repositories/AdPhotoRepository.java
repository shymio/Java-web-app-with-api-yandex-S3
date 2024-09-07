package com.example.crudpractice.repositories;

import com.example.crudpractice.modeles.Ad;
import com.example.crudpractice.modeles.AdPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdPhotoRepository extends JpaRepository<AdPhoto, Long> {

}
