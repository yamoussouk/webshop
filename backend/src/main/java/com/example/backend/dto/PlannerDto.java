package com.example.backend.dto;

import java.util.List;
import java.util.Set;

import com.example.backend.model.Meta;

public class PlannerDto {

    private Long id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private double price;
    private int quantity;
    private String downloadLink;
    private int enabled;
    private List<String> categories;
    private String size;
    private String startingDay;
    private String sku;
    private String lastUpdated;
    private Set<Meta> metaTags;

    public String getName() {
        return this.name;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public double getPrice () {
        return this.price;
    }

    public int getQuantity () {
        return this.quantity;
    }

    public int getEnabled () {
        return this.enabled;
    }

    public String getDownloadLink () {
        return this.downloadLink;
    }

    public List<String> getCategories () {
        return this.categories;
    }

    public String getSize() {
        return this.size;
    }

    public String getStartingDay() {
        return this.startingDay;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(String time) {
        this.lastUpdated = time;
    }

    public Set<Meta> getMetaTags() {
        return this.metaTags;
    }

    public void setMetaTags(Set<Meta> meta) {
        this.metaTags = meta;
    }
}
