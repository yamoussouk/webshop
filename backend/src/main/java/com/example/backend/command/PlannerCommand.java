package com.example.backend.command;

import com.example.backend.model.Category;
import com.example.backend.command.ImageCommand;

import java.util.Set;
import java.util.List;

public class PlannerCommand {

    private Long id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private double price;
    private Set<Category> category;
    private Set<String> categories;
    private List<ImageCommand> image;
    private int quantity;
    private int enabled;
    private String downloadLink;
    private String size;
    private String startingDay;
    private double vat;
    private double discount;
    private String sku;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PlannerCommand() {
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

    public Set<Category> getCategory() {
        return this.category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }

    public void setCategories(Set<String> category) {
        this.categories = category;
    }

    public Set<String> getCategories() {
        return this.categories;
    }

    public List<ImageCommand> getImage() {
        return image;
    }

    public void setImage(List<ImageCommand> image) {
        this.image = image;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getEnabled() {
        return this.enabled;
    }

    public String getDownloadLink() {
        return this.downloadLink;
    }

    public void setDownloadLink(String link) {
        this.downloadLink = link;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return this.size;
    }

    public void setStartingDay(String day) {
        this.startingDay = day;
    }

    public String getStartingDay() {
        return this.startingDay;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getVat() {
        return this.vat;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return this.discount;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}

