package com.example.backend.service;

import com.example.backend.command.ProductCommand;
import com.example.backend.model.Image;
import com.example.backend.model.Product;
import com.example.backend.repository.ImageRepository;
import com.example.backend.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ImageRepository imageRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    private final String UPLOADED_FOLDER = "src/main/resources/static/images/";
    private final String WDIR = System.getProperty("user.dir");
    private final String UPLOADED = WDIR + "\\frontend\\static\\uploaded\\";

    public ImageServiceImpl( ProductRepository productRepository, ProductService productService, ImageRepository imageRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long productId, MultipartFile file) {
        if (file.isEmpty()) {
            //log.error("File is empty");
        }
        try {
            Product product = productRepository.findById(productId).get();
            byte[] bytes = file.getBytes();
            if (!new File(UPLOADED_FOLDER + productId).exists()) {
                new File(UPLOADED_FOLDER + productId).mkdirs();
            }
            Path path = Paths.get(UPLOADED_FOLDER + productId + "//" + file.getOriginalFilename());
            Image uploadedImage = new Image();
            uploadedImage.setImageUrl(file.getOriginalFilename());
            uploadedImage.setOneProduct(product);
            Files.write(path, bytes);
            imageRepository.save(uploadedImage);
        } catch (IOException e) {
            //log.error("Error occurred", e);
            e.printStackTrace();
        }
    }

    //TODO: Make it safe
    @Override
    public void saveOneImage(MultipartFile file) {
        if (file.isEmpty()) {
            //log.error("File is empty");
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED + file.getOriginalFilename());
            Image uploadedImage = new Image();
            uploadedImage.setImageUrl(file.getOriginalFilename());
            Files.write(path, bytes);
            imageRepository.save(uploadedImage);
        } catch (IOException e) {
            //log.error("Error occurred", e);
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void renderImage(Long id, String imageUrl, HttpServletResponse response) throws IOException {
        ProductCommand productCommand = productService.findCommandById(id);
        ClassPathResource imgFile = new ClassPathResource("static/images/" + productCommand.getId() + "/" + imageUrl);
        System.out.println("TODO: DELETE IT! " + imgFile);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try {
            StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        } catch (IOException e) {
            StreamUtils.copy(new ClassPathResource("static/images/noimage.jpg").getInputStream(), response.getOutputStream());
        }
    }

    @Transactional
    @Override
    public void deleteImage(Product product, String imageUrl) {
        imageRepository.deleteByImageUrl(imageUrl);
        imageRepository.findAll().iterator().forEachRemaining(System.out::println);
    }

    @Override
    public List<Image> getAllImages() {
        List<Image> images = new ArrayList<>();
        imageRepository.findAll().iterator().forEachRemaining(images::add);
        return images;
    }

    @Override
    public Image findById(Long id) {
        Optional<Image> i = imageRepository.findById(id);
        //TODO: check error
        return i.orElse(null);
    }

    @Override
    public void saveImage(Image image) {
        imageRepository.save(image);
    }
}

