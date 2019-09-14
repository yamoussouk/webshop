package com.example.backend.converter;

import com.example.backend.command.ProductCommand;
import com.example.backend.model.Product;
import com.example.backend.model.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class ProductCommandToProduct implements Converter<ProductCommand, Product> {

    public ProductCommandToProduct() {
    }

    @Synchronized
    @NotNull
    @Override
    public Product convert(ProductCommand productCommand) {
        if (productCommand == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productCommand.getId());
        product.setName(productCommand.getName());
        product.setPrice(productCommand.getPrice());
        product.setShortDescription(productCommand.getShortDescription());
        product.setLongDescription(productCommand.getLongDescription());
        product.setCategory(productCommand.getCategory());
        product.setOneImage(productCommand.getImage());
        product.setQuantity(productCommand.getQuantity());
        return product;
    }
}
