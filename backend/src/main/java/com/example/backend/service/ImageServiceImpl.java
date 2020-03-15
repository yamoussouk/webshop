package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.model.Product;
import com.example.backend.repository.ImageRepository;
import com.example.backend.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageRepository imageRepository;

    private final String WDIR = System.getProperty("user.dir");
    private final String UPLOADED = WDIR + "/frontend/static/uploaded/";

    public ImageServiceImpl() {}

    @Override
    @Transactional
    public Image saveImageFile(Long productId, MultipartFile file) {
        if (file.isEmpty()) {
            return null;
            //log.error("File is empty");
        }
        try {
            Product product = productRepository.findById(productId).get();
            byte[] bytes = file.getBytes();
            if (!new File(UPLOADED + productId).exists()) {
                new File(UPLOADED + productId).mkdirs();
            }
            int r = new Random().nextInt(10000);
            System.out.println(file.getOriginalFilename());
            String[] temp = file.getOriginalFilename().split("\\.");
            long time = new Date().getTime();
            System.out.println(new Date(time));
            String name = time + "_" + r + "." + temp[temp.length - 1];
            System.out.println(name);
            if (file.getOriginalFilename().toLowerCase().contains("cover")) {
                String[] namePart = name.split("\\.");
                System.out.println(namePart);
                name = namePart[0] + "_cover." + namePart[1];   
            }
            Path path = Paths.get(UPLOADED + productId + "//" + name);
            Image uploadedImage = new Image();
            uploadedImage.setImageUrl(name);
            uploadedImage.setOneProduct(product);
            uploadedImage.setSavedTime(new Date(time));
            Files.write(path, bytes);
            imageRepository.save(uploadedImage);
            return uploadedImage;
        } catch (IOException e) {
            //log.error("Error occurred", e);
            e.printStackTrace();
            return null;
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

    @Transactional
    @Override
    public void deleteImage(Product product, Image image) {
        imageRepository.deleteById(image.getId());
        File file = new File(System.getProperty("user.dir") + "/frontend/static/uploaded/" + Long.toString(product.getId()) + "/" + image.getImageUrl());
        file.delete();
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

