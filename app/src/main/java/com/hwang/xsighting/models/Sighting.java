package com.hwang.xsighting.models;

import java.util.Date;

public class Sighting {
    String authorUserId;
    String authorUsername;
    Date timestamp;
    String location;
    double[] latLong;
    String imgUrl;
    String description;

    // No argument constructor
    public Sighting() {};

    // Constructor
    public Sighting(String authorUserId, String authorUsername, Date timestamp,
                    String location, double[] latLong, String imgUrl, String description) {
        this.authorUserId = authorUserId;
        this.authorUsername = authorUsername;
        this.timestamp = timestamp;
        this.location = location;
        this.latLong = latLong;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    // Getters
    public String getAuthorUserId() {
        return this.authorUserId;
    }

    public String getAuthorUsername() {
        return this.authorUsername;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public String getLocation() {
        return this.location;
    }

    public double[] getLatLong() {
        return this.latLong;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public String getDescription() {
        return this.description;
    }

    // Setters
    public void setAuthorUserId(String authorUserId) {
        this.authorUserId = authorUserId;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLatLong(double lat, double lon) {
        double[] newLatLong= {lat, lon};
        this.latLong = newLatLong;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
