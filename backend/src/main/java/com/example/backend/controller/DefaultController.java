package com.example.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.backend.command.ProductCommand;
import com.example.backend.model.Image;
import com.example.backend.model.Product;
import com.example.backend.service.ProductService;
import com.example.backend.converter.ProductToProductCommand;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    private final ProductService productService;
    private final ProductToProductCommand productToProductCommand = new ProductToProductCommand();

    public DefaultController (ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/default/products/all")
    public List<ProductCommand> getAllProducts() {
        List<Product> products =  this.productService.getProducts();
        List<ProductCommand> returnedValue = new ArrayList<ProductCommand>();
        for (Product p : products) {
            returnedValue.add(productToProductCommand.convert(p));
        }
        return returnedValue;
    }

    @GetMapping("/default/product/{id}")
    public Product getProductById(@PathVariable(name = "id") String id) {
        return productService.findById(new Long(id));
    }

    @GetMapping("/default/product/{id}/images")
    public Set<Image> getProductImages(@PathVariable(name = "id") String id) {
        return productService.findById(new Long(id)).getImages();
    }
}