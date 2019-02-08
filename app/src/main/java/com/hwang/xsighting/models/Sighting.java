package com.hwang.xsighting.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Sighting {
    String authorId;
    String authorUsername;
    Timestamp createdTime;
    GeoPoint locationData;
    String locationName;
    String imageUrl;
    String description;
    String firebaseId;
    Integer upVote;
    Integer downVote;
    HashMap<String, Boolean> voted;

    // No argument constructor
    public Sighting() {};

    // Constructor
    public Sighting(String authorId, String authorUsername, Timestamp createdTime,
                    GeoPoint locationData, String locationName, String imageUrl, String description) {
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.createdTime = createdTime;
        this.locationData = locationData;
        this.locationName = locationName;
        this.imageUrl = imageUrl;
        this.description = description;
        this.firebaseId = "";
        this.upVote = 0;
        this.downVote = 0;
        this.voted = new HashMap<>();
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

    public int getUpVote() { return upVote; }

    public int getDownVote() { return downVote; }

    public HashMap<String, Boolean> getVoted() { return voted; }


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

    public void setUpVote(Integer upVote) { this.upVote = upVote; }

    public void setDownVote(Integer downVote) { this.downVote = downVote; }

    public void setVoted(String key, boolean value) {
        this.voted.put(key, value);
    }


}
