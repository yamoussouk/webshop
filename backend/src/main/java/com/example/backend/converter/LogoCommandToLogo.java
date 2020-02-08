package com.example.backend.converter;

import com.example.backend.command.ImageCommand;
import com.example.backend.command.LogoCommand;
import com.example.backend.model.Logo;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;

import javax.validation.constraints.NotNull;

@Component
public class LogoCommandToLogo implements Converter<LogoCommand, Logo> {

    public final ImageCommandToImage imageCommandToImage;

    public LogoCommandToLogo(ImageCommandToImage imageCommandToImage) {
        this.imageCommandToImage = imageCommandToImage;
    }

    @Synchronized
    @NotNull
    @Override
    public Logo convert(LogoCommand logoCommand) {
        if (logoCommand == null) {
            return null;
        }

        Logo logo = new Logo();
        logo.setId(logoCommand.getId());
        logo.setName(logoCommand.getName());
        logo.setPrice(logoCommand.getPrice());
        logo.setShortDescription(logoCommand.getShortDescription());
        logo.setLongDescription(logoCommand.getLongDescription());
        logo.setCategory(logoCommand.getCategory());
        Set<ImageCommand> images = logoCommand.getImage();
        for (ImageCommand i : images) {
            logo.setImage(this.imageCommandToImage.convert(i));
        }
        logo.setQuantity(logoCommand.getQuantity());
        logo.setEnabled(logoCommand.getEnabled());
        logo.setSku(logoCommand.getSku());
        return logo;
    }
}
