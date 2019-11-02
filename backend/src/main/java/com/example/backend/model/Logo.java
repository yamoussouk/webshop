package com.example.backend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
public class Logo extends Product {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_logos",
            joinColumns = @JoinColumn(
                    name = "logo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "order_id", referencedColumnName = "id"))
    private Set<Orders> orders = new HashSet<>();

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