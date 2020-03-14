package com.example.backend.service;

import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl {
    private final PlannerService plannerService;
    private final LogoService logoService;

    public ProductServiceImpl(PlannerService plannerService, LogoService logoService) {
        this.plannerService = plannerService;
        this.logoService = logoService;
    }

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