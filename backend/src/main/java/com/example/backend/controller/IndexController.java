package com.example.backend.controller;

import com.example.backend.model.Product;
import com.example.backend.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Deprecated
@Controller
public class IndexController {

    private final ProductService productService;

    public IndexController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/products")
    public String getIndexPage(Model model) {

        model.addAttribute("products", productService.getProducts());

        return "products.html";
    }

    @GetMapping("/admin/start")
    public String getStart() {
        return "admin/start.html";
    }

    @GetMapping("/admin/new")
    public String addNewProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product/new.html";
    }
}

