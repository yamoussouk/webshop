package com.example.backend.repository;

import java.util.Optional;

import com.example.backend.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    void deleteByImageUrl(String imageUrl);

    void deleteById(Long id);

    Optional<Image> findById(Long id);
}