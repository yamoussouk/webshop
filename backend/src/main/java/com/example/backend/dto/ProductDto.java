package com.example.backend.dto;

import java.util.List;

public class ProductDto {

    private String name;
    private String shortDescription;
    private String longDescription;
    private int price;
    private int quantity;
    private String downloadLink;
    private boolean enabled;
    private List<String> categories;
    private String sku;

    public String getName() {
        return this.name;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public int getPrice () {
        return this.price;
    }

    public int getQuantity () {
        return this.quantity;
    }

    public boolean getEnabled () {
        return this.enabled;
    }

    public String getDownloadLink () {
        return this.downloadLink;
    }

    public List<String> getCategories () {
        return this.categories;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}

