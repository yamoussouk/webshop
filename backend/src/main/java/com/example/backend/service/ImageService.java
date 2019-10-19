package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.model.Product;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ImageService {

    Image saveImageFile(Long recipeId, MultipartFile file);
    void saveOneImage(MultipartFile file);
    void renderImage(Long id, String imageUrl, HttpServletResponse response) throws IOException;
    void deleteImage(Product product, Image image);
    List<Image> getAllImages();
    Image findById(Long id);
    void saveImage(Image image);
}
