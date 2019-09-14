package com.example.backend.repository;

import com.example.backend.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    void deleteByImageUrl(String imageUrl);
}