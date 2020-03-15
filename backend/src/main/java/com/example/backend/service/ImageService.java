package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    Image saveImageFile(Long recipeId, MultipartFile file);
    void saveOneImage(MultipartFile file);
    void deleteImage(Product product, Image image);
    List<Image> getAllImages();
    Image findById(Long id);
    void saveImage(Image image);
}
