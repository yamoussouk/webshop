package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private double price;
    private int quantity;

    @ElementCollection(targetClass=String.class)
    private Set<String> category = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_images",
            joinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "image_id", referencedColumnName = "id"))
    //@OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.PERSIST)
    //@JsonManagedReference
    private Set<Image> images = new HashSet<>();

    public void setImage(Image image) {
        this.images.add(image);
    }

    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "order_id", referencedColumnName = "id"))
    private Set<Orders> orders = new HashSet<>();

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<String> getCategory() {
        return category;
    }

    public void setCategory(Set<String> category) {
        this.category = category;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public void setOneImage(Image image) {
        this.images.add(image);
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    public void setOneOrder(Orders orders) {
        this.orders.add(orders);
    }
}
