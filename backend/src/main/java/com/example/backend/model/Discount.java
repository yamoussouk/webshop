package com.example.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int percent;
    private int enabled;
    // @JsonManagedReference(value="discount")
    // @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    @Column
    @ElementCollection(targetClass=Long.class)
    private List<Long> products;

    public Discount () {}

    public Discount (String name, int percent, int enabled, List<Long> products) {
        this.name = name;
        this.percent = percent;
        this.enabled = enabled;
        this.products = products;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setEnabled (int enabled) {
        this.enabled = enabled;
    }

    public void setProducts(List<Long> products) {
        this.products = products;
    }

    public Long getId() {
        return this.id;
    }

    public String getName () {
        return this.name;
    }

    public int getPercent () {
        return this.percent;
    }

    public int getEnabled () {
        return this.enabled;
    }

    public List<Long> getProducts() {
        return this.products;
    }
}