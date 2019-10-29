package com.example.backend.model;

import javax.persistence.Entity;

@Entity
public class Logo extends Product {

    private String logoText;

    public Logo () {
        this.setType("Logo");
    }

    public String getLogoText () {
        return this.logoText;
    }

    public void setLogoText (String logo) {
        this.logoText = logo;
    }
}