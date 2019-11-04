package com.example.backend.dto;

import java.util.List;

public class LogoDto {

    private Long id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private int price;
    private int quantity;
    private String logoText;
    private int enabled;
    private List<String> categories;

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

    public int getEnabled () {
        return this.enabled;
    }

    public String getLogoText () {
        return this.logoText;
    }

    public List<String> getCategories () {
        return this.categories;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}

