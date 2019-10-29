package com.example.backend.converter;

import com.example.backend.command.ImageCommand;
import com.example.backend.command.LogoCommand;
import com.example.backend.model.Category;
import com.example.backend.model.Image;
import com.example.backend.model.Logo;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

@Component
public class LogoToLogoCommand implements Converter<Logo, LogoCommand> {

    final ImageToImageCommand imageToImageCommand;

    public LogoToLogoCommand() {
        this.imageToImageCommand = new ImageToImageCommand();
    }

    @Synchronized
    @NotNull
    @Override
    public LogoCommand convert(Logo Logo) {
        if (Logo == null) {
            return null;
        }

        LogoCommand LogoCommand = new LogoCommand();
        LogoCommand.setId(Logo.getId());
        LogoCommand.setCategory(Logo.getCategory());
        Set<String> categories = new HashSet<String>();
        for (Category c : Logo.getCategory()) {
            categories.add(c.getName());
        }
        LogoCommand.setCategories(categories);
        LogoCommand.setShortDescription(Logo.getShortDescription());
        LogoCommand.setLongDescription(Logo.getLongDescription());
        LogoCommand.setName(Logo.getName());
        LogoCommand.setPrice(Logo.getPrice());
        LogoCommand.setQuantity(Logo.getQuantity());
        Set<ImageCommand> images = new HashSet<ImageCommand>();
        for (Image i : Logo.getImages()) {
            images.add(this.imageToImageCommand.convert(i));
        }
        LogoCommand.setImage(images);
        LogoCommand.setEnabled(Logo.getEnabled());
        return LogoCommand;
    }
}

