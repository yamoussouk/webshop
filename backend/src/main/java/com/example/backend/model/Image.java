package com.example.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = "product")
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private Date savedTime;


    @ManyToMany(fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
    //@ManyToOne
    //@JsonBackReference
    //private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setOneProduct(Product product) {
        this.products.add(product);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /*public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }*/

    public Date getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(Date savedTime) {
        this.savedTime = savedTime;
    }
}