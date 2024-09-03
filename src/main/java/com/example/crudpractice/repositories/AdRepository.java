package com.example.crudpractice.repositories;

import com.example.crudpractice.modeles.Ad;
import com.example.crudpractice.modeles.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findByTitle(String title);
}
