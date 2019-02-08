package com.hwang.xsighting.models;

import java.util.HashMap;
import java.util.Map;

public class User {
    protected Map<String, Boolean> createdSightings;
    protected String deviceToken;

    // No argument constructor
    public User() {
        this.createdSightings = new HashMap<>();
    }

    // Constructor to save token Firebase Cloud Messaging
    public User(String deviceToken) {
        this.createdSightings = new HashMap<>();
        this.deviceToken = deviceToken;
    }

    // Getters
    public Map<String, Boolean> getCreatedSightings() {
        return this.createdSightings;
    }

    // Setters
    public void setCreatedSightings(Map<String, Boolean> createdSightings) {
        this.createdSightings = createdSightings;
    }

    // Adds a sighting to createdSightings
    public void addCreatedsighting(String sightingId) {
        this.createdSightings.put(sightingId, true);
    }
}
