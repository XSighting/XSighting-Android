package com.hwang.xsighting.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class Sighting {
    String mAuthorId;
    String mAuthorUsername;
    Timestamp mCreatedTime;
    GeoPoint mLocationData;
    String mLocationName;
    String mImageUrl;
    String mDescription;

    // No argument constructor
    public Sighting() {};

    // Constructor
    public Sighting(String mAuthorId, String mAuthorUsername, Timestamp mCreatedTime, String mLocationName,
                    GeoPoint mLocationData, String image_url, String mDescription) {
        this.mAuthorId = mAuthorId;
        this.mAuthorUsername = mAuthorUsername;
        this.mCreatedTime = mCreatedTime;
        this.mLocationData = mLocationData;
        this.mImageUrl = image_url;
        this.mDescription = mDescription;
        this.mLocationName = mLocationName;
    }

    // Getters
    public String getAuthorId() {
        return this.mAuthorId;
    }

    public String getAuthorUsername() {
        return this.mAuthorUsername;
    }

    public Timestamp getCreatedTime() {
        return this.mCreatedTime;
    }

    public GeoPoint getLocationData() {
        return this.mLocationData;
    }

    public String getImageUrl() {
        return this.mImageUrl;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getLocationName() {
        return mLocationName;
    }

    // Setters
    public void setAuthorId(String mAuthorId) {
        this.mAuthorId = mAuthorId;
    }

    public void setAuthorUsername(String authorUsername) {
        this.mAuthorUsername = authorUsername;
    }

    public void setCreatedTime(Timestamp mCreatedTime) {
        this.mCreatedTime = mCreatedTime;
    }

    public void setLocation(GeoPoint location) {
        this.mLocationData = mLocationData;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setLocationName(String mLocationName) {
        this.mLocationName = mLocationName;
    }
}
