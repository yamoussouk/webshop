package com.example.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.backend.command.ProductCommand;
import com.example.backend.model.Image;
import com.example.backend.model.Mail;
import com.example.backend.model.Product;
import com.example.backend.service.EmailService;
import com.example.backend.service.ProductService;
import com.example.backend.converter.ProductToProductCommand;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class DefaultController {

    private final ProductService productService;
    private final ProductToProductCommand productToProductCommand = new ProductToProductCommand();
    private final EmailService emailService = new EmailService();

    @Value("${owner.email}")
    private String ownerEmail;

    public DefaultController (ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/default/products/all")
    public List<ProductCommand> getAllProducts() {
        List<Product> products =  this.productService.getProducts(true);
        List<ProductCommand> returnedValue = new ArrayList<ProductCommand>();
        for (Product p : products) {
            returnedValue.add(productToProductCommand.convert(p));
        }
        return returnedValue;
    }

    @GetMapping("/default/product/{id}")
    public ProductCommand getProductById(@PathVariable(name = "id") String id) {
        Product p = productService.findById(new Long(id));
        return productToProductCommand.convert(p);
    }

    @GetMapping("/default/product/{id}/images")
    public Set<Image> getProductImages(@PathVariable(name = "id") String id) {
        return productService.findById(new Long(id)).getImages();
    }

    @PostMapping("/default/contact")
    public ResponseEntity<?> contact(@RequestParam("from") String from, @RequestParam("text") String email) {
        this.emailService.sendEmail(mailAssembler(from, email), "contact");
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    private Mail mailAssembler(String from, String email) {
        Mail contactMessage = new Mail();
        contactMessage.setFrom("valaki@gmail.com");
        contactMessage.setTo(ownerEmail);
        contactMessage.setSubject("Contact message from the webshop");
        Map<String, Object> model = new HashMap<>();
        model.put("message", email);
        model.put("from", from);
        contactMessage.setModel(model);
        return contactMessage;
    }
}