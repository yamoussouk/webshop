package com.example.backend.converter;

import com.example.backend.command.ImageCommand;
import com.example.backend.command.PlannerCommand;
import com.example.backend.model.Image;
import com.example.backend.model.Product;
import com.example.backend.model.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;

import javax.validation.constraints.NotNull;

@Component
public class PlannerCommandToPlanner implements Converter<PlannerCommand, Product> {

    public final ImageCommandToImage imageCommandToImage;

    public PlannerCommandToPlanner(ImageCommandToImage imageCommandToImage) {
        this.imageCommandToImage = imageCommandToImage;
    }

    @Synchronized
    @NotNull
    @Override
    public Product convert(PlannerCommand PlannerCommand) {
        if (PlannerCommand == null) {
            return null;
        }

        Product product = new Product();
        product.setId(PlannerCommand.getId());
        product.setName(PlannerCommand.getName());
        product.setPrice(PlannerCommand.getPrice());
        product.setShortDescription(PlannerCommand.getShortDescription());
        product.setLongDescription(PlannerCommand.getLongDescription());
        product.setCategory(PlannerCommand.getCategory());
        Set<ImageCommand> images = PlannerCommand.getImage();
        for (ImageCommand i : images) {
            product.setImage(this.imageCommandToImage.convert(i));
        }
        product.setQuantity(PlannerCommand.getQuantity());
        return product;
    }
}