package com.hwang.xsighting.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class Sighting {
    String authorId;
    String authorUsername;
    Timestamp createdTime;
    GeoPoint locationData;
    String locationName;
    String imageUrl;
    String description;
    String firebaseId;

    // No argument constructor
    public Sighting() {};

    // Constructor
    public Sighting(String authorId, String authorUsername, Timestamp createdTime, String locationName,
                    GeoPoint locationData, String image_url, String description) {
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.createdTime = createdTime;
        this.locationData = locationData;
        this.imageUrl = image_url;
        this.description = description;
        this.locationName = locationName;
        this.firebaseId = "";
    }

    // Getters
    public String getAuthorId() {
        return this.authorId;
    }

    public String getAuthorUsername() {
        return this.authorUsername;
    }

    public Timestamp getCreatedTime() {
        return this.createdTime;
    }

    public GeoPoint getLocationData() {
        return this.locationData;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getFirebaseId() {return firebaseId;}

    // Setters
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setAuthor(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public void setLocation(GeoPoint location) {
        this.locationData = locationData;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setFirebaseId(String firebaseId) {this.firebaseId = firebaseId;}
}
