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
    public String getmAuthorId() {
        return this.mAuthorId;
    }

    public String getmAuthorUsername() {
        return this.mAuthorUsername;
    }

    public Timestamp getmCreatedTime() {
        return this.mCreatedTime;
    }

    public GeoPoint getmLocationData() {
        return this.mLocationData;
    }

    public String getmImageUrl() {
        return this.mImageUrl;
    }

    public String getmDescription() {
        return this.mDescription;
    }

    public String getmLocationName() {
        return mLocationName;
    }

    // Setters
    public void setmAuthorId(String mAuthorId) {
        this.mAuthorId = mAuthorId;
    }

    public void setAuthor(String authorUsername) {
        this.mAuthorUsername = authorUsername;
    }

    public void setmCreatedTime(Timestamp mCreatedTime) {
        this.mCreatedTime = mCreatedTime;
    }

    public void setLocation(GeoPoint location) {
        this.mLocationData = mLocationData;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmLocationName(String mLocationName) {
        this.mLocationName = mLocationName;
    }
}
