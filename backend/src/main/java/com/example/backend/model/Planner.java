package com.example.backend.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Planner extends Product {

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

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private StartingDay startingDay; 

    private String downloadLink;

    public Planner () {
        this.setType("Planner"); 
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

    public void setDownloadLink (String link) {
        this.downloadLink = link;
    }

    public String getDownloadLink () {
        return this.downloadLink;
    }
}