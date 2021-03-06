package com.example.backend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Product {

    public enum Size {
        A4,
        A5,
        USLETTER,
        PERSONAL,
        HALFSIZE
    }

    public enum StartingDay {
        SUNDAY,
        MONDAY
    }

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortDescription;
    @Column(length = 3000)
    private String longDescription;
    private double price;
    private Integer quantity;
    private String downloadLink;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private StartingDay startingDay; 

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

    public Set<Category> getCategory() {
        return this.category;
    }

    public void setCategory(Set<Category> category) {
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

    public void setDownloadLink (String link) {
        this.downloadLink = link;
    }

    public String getDownloadLink () {
        return this.downloadLink;
    }

    public boolean getEnabled () {
        return this.enabled;
    }

    public void setEnabled (boolean enabled) {
        this.enabled = enabled;
    }

    public String getSize() {
        return this.size.name();
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getStrartingDay() {
        return this.startingDay.name();
    }

    public void setStartingDay(StartingDay day) {
        this.startingDay = day;
    }
}
