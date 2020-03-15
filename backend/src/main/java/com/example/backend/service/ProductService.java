package com.example.backend.service;

import com.example.backend.model.Product;

public interface ProductService {

    Product getProduct(Long id);
    Product getProductByName(String name);
}