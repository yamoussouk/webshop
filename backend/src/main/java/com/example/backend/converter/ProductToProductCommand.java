package com.example.backend.converter;

import com.example.backend.command.ProductCommand;
import com.example.backend.model.Product;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class ProductToProductCommand implements Converter<Product, ProductCommand> {

    public ProductToProductCommand() {
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
        productCommand.setShortDescription(product.getShortDescription());
        productCommand.setLongDescription(product.getLongDescription());
        productCommand.setName(product.getName());
        productCommand.setPrice(product.getPrice());
        productCommand.setQuantity(product.getQuantity());
        productCommand.setImage(product.getImages().iterator().next());
        return productCommand;
    }
}

