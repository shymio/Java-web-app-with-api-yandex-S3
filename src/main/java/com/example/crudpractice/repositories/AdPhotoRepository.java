package com.example.crudpractice.repositories;


import com.example.crudpractice.modeles.AdPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdPhotoRepository extends JpaRepository<AdPhoto, Long> {

    List<AdPhoto> findAllByAdId(Long adId);
//    void deleteAll(List<AdPhoto> photos);
}
