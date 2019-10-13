package com.example.backend.service;

import com.example.backend.command.ProductCommand;
import com.example.backend.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {

    List<Product> getProducts(boolean enabled);
    Product findById(Long id);
    ProductCommand findCommandById(Long id);
    void deleteById(Long idToDelete);
    Product saveProduct(Product product);
}
