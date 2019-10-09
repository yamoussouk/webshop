package com.example.backend.converter;

import com.example.backend.command.ImageCommand;
import com.example.backend.model.Image;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class ImageToImageCommand implements Converter<Image, ImageCommand> {

    public ImageToImageCommand() {
    }

    @Synchronized
    @NotNull
    @Override
    public ImageCommand convert(Image image) {
        if (image == null) {
            return null;
        }

        ImageCommand imageCommand = new ImageCommand();
        imageCommand.setId(image.getId());
        imageCommand.setImageUrl(image.getImageUrl());
        imageCommand.setSavedTime(image.getSavedTime());
        return imageCommand;
    }
}