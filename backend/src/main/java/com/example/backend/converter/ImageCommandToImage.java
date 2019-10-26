package com.example.backend.converter;

import javax.validation.constraints.NotNull;

import com.example.backend.command.ImageCommand;
import com.example.backend.model.Image;
import com.example.backend.repository.ImageRepository;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Synchronized;

@Component
public class ImageCommandToImage implements Converter<ImageCommand, Image> {

    private final ImageRepository imageRepository;

    public ImageCommandToImage(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Synchronized
    @NotNull
    @Override
    public Image convert(ImageCommand imageCommand) {
        if (imageCommand == null) {
            return null;
        }

        Image image = new Image();
        Image temp = this.imageRepository.findById(imageCommand.getId()).get();
        image.setId(imageCommand.getId());
        image.setImageUrl(imageCommand.getImageUrl());
        image.setSavedTime(imageCommand.getSavedTime());
        image.setProducts(temp.getProducts());
        return image;
    }
}