package com.hwang.xsighting.models;

import java.util.Date;

public class Sighting {
    String userId;
    String author;
    Date createdTime;
    String location;
    Double[] latLong;
    String image_url;
    String description;

    // No argument constructor
    public Sighting() {};

    // Constructor
    public Sighting(String userId, String author, Date createdTime,
                    String location, Double[] latLong, String image_url, String description) {
        this.userId = userId;
        this.author = author;
        this.createdTime = createdTime;
        this.location = location;
        this.latLong = latLong;
        this.image_url = image_url;
        this.description = description;
    }

    // Getters
    public String getUserId() {
        return this.userId;
    }

    public String getAuthor() {
        return this.author;
    }

    public Date getCreatedTime() {
        return this.createdTime;
    }

    public String getLocation() {
        return this.location;
    }

    public Double[] getLatLong() {
        return this.latLong;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public String getDescription() {
        return this.description;
    }

    // Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLatLong(Double lat, Double lon) {
        Double[] newLatLong= {lat, lon};
        this.latLong = newLatLong;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
