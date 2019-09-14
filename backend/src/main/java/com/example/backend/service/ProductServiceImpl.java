package com.example.backend.service;

import com.example.backend.command.ProductCommand;
import com.example.backend.converter.ProductToProductCommand;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Product;
import com.example.backend.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductToProductCommand converter;

    public ProductServiceImpl(ProductRepository productRepository, ProductToProductCommand converter) {
        this.productRepository = productRepository;
        this.converter = converter;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> allProducts = new ArrayList<>();
        this.productRepository.findAll().iterator().forEachRemaining(allProducts::add);
        return allProducts;
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new NotFoundException("User with given id has not found!");
        }
    }

    @Override
    @Transactional
    public ProductCommand findCommandById(Long id) {
        return converter.convert(findById(id));
    }

    @Override
    @Transactional
    public void deleteById(Long idToDelete) {
        productRepository.deleteById(idToDelete);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}

