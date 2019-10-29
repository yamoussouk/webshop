package com.example.backend.model;

import javax.persistence.Entity;

@Entity
public class Planner extends Product {

    public Planner () {
        this.setType("Planner"); 
    }
}