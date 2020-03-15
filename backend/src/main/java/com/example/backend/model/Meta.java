package com.example.backend.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Product> products = new ArrayList<>();

    public Meta() {
    }

    public Meta(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProduct() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
}
