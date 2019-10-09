package com.example.backend.command;

import java.util.Date;

public class ImageCommand {

    private Long id;
    private String imageUrl;
    private Date savedTime;

    public ImageCommand() {
    }

    public Long getId() {
        return this.id;
    }

    public String getImageUrl () {
        return this.imageUrl;
    }

    public Date getSavedTime () {
        return this.savedTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    public void setSavedTime (Date time) {
        this.savedTime = time;
    }
}