package com.example.backend.converter;

import com.example.backend.command.ImageCommand;
import com.example.backend.command.ProductCommand;
import com.example.backend.model.Category;
import com.example.backend.model.Image;
import com.example.backend.model.Product;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

@Component
public class ProductToProductCommand implements Converter<Product, ProductCommand> {

    final ImageToImageCommand imageToImageCommand;

    public ProductToProductCommand() {
        this.imageToImageCommand = new ImageToImageCommand();
    }

    @Synchronized
    @NotNull
    @Override
    public ProductCommand convert(Product product) {
        if (product == null) {
            return null;
        }

        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(product.getId());
        productCommand.setCategory(product.getCategory());
        Set<String> categories = new HashSet<String>();
        for (Category c : product.getCategory()) {
            categories.add(c.getName());
        }
        productCommand.setCategories(categories);
        productCommand.setShortDescription(product.getShortDescription());
        productCommand.setLongDescription(product.getLongDescription());
        productCommand.setName(product.getName());
        productCommand.setPrice(product.getPrice());
        productCommand.setQuantity(product.getQuantity());
        Set<ImageCommand> images = new HashSet<ImageCommand>();
        for (Image i : product.getImages()) {
            images.add(this.imageToImageCommand.convert(i));
        }
        productCommand.setImage(images);
        productCommand.setEnabled(product.getEnabled());
        productCommand.setDownloadLink(product.getDownloadLink());
        return productCommand;
    }
}

