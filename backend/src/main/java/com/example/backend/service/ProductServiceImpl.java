package com.example.backend.service;

import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Product;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private PlannerService plannerService;
    @Autowired
    private LogoService logoService;

    public ProductServiceImpl() {}

    public Product getProduct(Long id) {
        try {
            return plannerService.findById(id);
        } catch (NotFoundException e) {
            return logoService.findById(id);
        }
    }

    public Product getProductByName(String name) {
        return plannerService.findByName(name);
    }
}