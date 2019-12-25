package com.example.backend.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.InheritanceType;

@Entity
@Inheritance(
    strategy = InheritanceType.JOINED
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortDescription;
    @Column(length = 5000)
    private String longDescription;
    private double price;
    private Integer quantity;
    private int enabled;
    private String type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id", referencedColumnName = "id"))
    private Set<Category> category = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_images",
            joinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "image_id", referencedColumnName = "id"))
    //@OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.PERSIST)
    //@JsonManagedReference
    @JsonBackReference
    private List<Image> images = new ArrayList<>();

    public void setImage(Image image) {
        this.images.add(image);
    }

    
    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(
    //         name = "order_products",
    //         joinColumns = @JoinColumn(
    //                 name = "product_id", referencedColumnName = "id"),
    //         inverseJoinColumns = @JoinColumn(
    //                 name = "order_id", referencedColumnName = "id"))
    // private Set<Orders> orders = new HashSet<>();

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

    public Set<Category> getCategory() {
        return this.category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setOneImage(Image image) {
        this.images.add(image);
    }

    public int getEnabled () {
        return this.enabled;
    }

    public void setEnabled (int enabled) {
        this.enabled = enabled;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
