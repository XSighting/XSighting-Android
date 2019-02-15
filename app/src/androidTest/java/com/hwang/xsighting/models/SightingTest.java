package com.hwang.xsighting.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class SightingTest {
    // All this setup code should be in an @Before method, to keep your tests independent.
    // Also, unclear why your model tests are in androidTest instead of test;
    // this should all run fine not on an Android device.
    String authorId1 = "abc123";
    String authorUsername1 = "Test McTesterson";
    Timestamp timestamp1 = new Timestamp(new Date());
    GeoPoint geoPoint1 = new GeoPoint(1, -1);
    String locationName1 = "Here";
    String imageUrl1 = "placeholder.jpg";
    String description1 = "This is a test";
    Sighting s1 = new Sighting(authorId1, authorUsername1, timestamp1,
             geoPoint1, locationName1, imageUrl1, description1);

    String authorId2 = "";
    String authorUsername2 = "";
    Timestamp timestamp2 = null;
    GeoPoint geoPoint2 = null;
    String locationName2 = "";
    String imageUrl2 = "";
    String description2 = "";
    Sighting s2 = new Sighting(authorId2, authorUsername2, timestamp2,
            geoPoint2, locationName2, imageUrl2, description2);

    String authorId3 = "ap4 mw";
    String authorUsername3 = "Dude";
    Timestamp timestamp3 = new Timestamp(new Date());
    GeoPoint geoPoint3 = new GeoPoint(40.24323, 100.3);
    String locationName3 = "The best place ever";
    String imageUrl3 = "thebestimageever.jpeg";
    String description3 = "I've seen things";
    Sighting s3 = new Sighting(authorId3, authorUsername3, timestamp3,
            geoPoint3, locationName3, imageUrl3, description3);

    @Test
    public void testGetAuthorId() {
        assertEquals(authorId1, s1.getAuthorId());
        assertEquals(authorId2, s2.getAuthorId());
        assertEquals(authorId3, s3.getAuthorId());
    }

    @Test
    public void testGetAuthorUsername() {
        assertEquals(authorUsername1, s1.getAuthorUsername());
        assertEquals(authorUsername2, s2.getAuthorUsername());
        assertEquals(authorUsername3, s3.getAuthorUsername());
    }

    @Test
    public void testGetCreatedTime() {
        assertEquals(timestamp1, s1.getCreatedTime());
        assertEquals(timestamp2, s2.getCreatedTime());
        assertEquals(timestamp3, s3.getCreatedTime());
    }

    @Test
    public void testGetLocationData() {
        assertEquals(geoPoint1, s1.getLocationData());
        assertEquals(geoPoint2, s2.getLocationData());
        assertEquals(geoPoint3, s3.getLocationData());
    }

    @Test
    public void testGetImageUrl() {
        assertEquals(imageUrl1, s1.getImageUrl());
        assertEquals(imageUrl2, s2.getImageUrl());
        assertEquals(imageUrl3, s3.getImageUrl());
    }

    @Test
    public void testGetDescription() {
        assertEquals(description1, s1.getDescription());
        assertEquals(description2, s2.getDescription());
        assertEquals(description3, s3.getDescription());
    }

    @Test
    public void testGetLocationName() {
        assertEquals(locationName1, s1.getLocationName());
        assertEquals(locationName2, s2.getLocationName());
        assertEquals(locationName3, s3.getLocationName());
    }

    @Test
    public void TestGetFirebaseId() {
        assertEquals("", s1.getFirebaseId());
        assertEquals("", s2.getFirebaseId());
        assertEquals("", s3.getFirebaseId());
    }

    @Test
    public void testSetAuthorId() {
        authorId1 = "12afp48sjpdfaj4";
        s1.setAuthorId(authorId1);
        assertEquals(authorId1, s1.getAuthorId());

        authorId2 = "6mj52ks9";
        s2.setAuthorId(authorId2);
        assertEquals(authorId2, s2.getAuthorId());

        authorId3 = null;
        s3.setAuthorId(authorId3);
        assertEquals(authorId3, s3.getAuthorId());
    }

    @Test
    public void testSetAuthor() {
        authorUsername1 = "john jacob jingleheimer schmidt";
        s1.setAuthor(authorUsername1);
        assertEquals(authorUsername1, s1.getAuthorUsername());

        authorUsername2 = "Professor Justice";
        s2.setAuthor(authorUsername2);
        assertEquals(authorUsername2, s2.getAuthorUsername());

        authorUsername3 = "";
        s3.setAuthor(authorUsername3);
        assertEquals(authorUsername3, s3.getAuthorUsername());
    }

    @Test
    public void testSetCreatedTime() {
        timestamp1 = new Timestamp(new Date());
        s1.setCreatedTime(timestamp1);
        assertEquals(timestamp1, s1.getCreatedTime());

        timestamp2 = new Timestamp(new Date());
        s2.setCreatedTime(timestamp2);
        assertEquals(timestamp2, s2.getCreatedTime());

        timestamp3 = null;
        s3.setCreatedTime(timestamp3);
        assertEquals(timestamp3, s3.getCreatedTime());
    }

    @Test
    public void testSetLocation() {
        geoPoint1 = new GeoPoint(2, -2);
        s1.setLocation(geoPoint1);
        assertEquals(geoPoint1, s1.getLocationData());

        geoPoint2 = new GeoPoint(-23.4567342634, 1.345634571);
        s2.setLocation(geoPoint2);
        assertEquals(geoPoint2, s2.getLocationData());

        geoPoint3 = null;
        s3.setLocation(geoPoint3);
        assertEquals(geoPoint3, s3.getLocationData());
    }

    @Test
    public void testSetImageUrl() {
        imageUrl1 = "anotherplaceholder.png";
        s1.setImageUrl(imageUrl1);
        assertEquals(imageUrl1, s1.getImageUrl());

        imageUrl2 = "placeholder.png";
        s2.setImageUrl(imageUrl2);
        assertEquals(imageUrl2, s2.getImageUrl());

        imageUrl3 = "";
        s3.setImageUrl(imageUrl3);
        assertEquals(imageUrl3, s3.getImageUrl());
    }

    @Test
    public void testSetDescription() {
        description1 = "This a different description";
        s1.setDescription(description1);
        assertEquals(description1, s1.getDescription());

        description2 = "I saw the thing";
        s2.setDescription(description2);
        assertEquals(description2, s2.getDescription());

        description3 = "";
        s3.setDescription(description3);
        assertEquals(description3, s3.getDescription());
    }

    @Test
    public void testSetLocationName() {
        locationName1 = "There";
        s1.setLocationName(locationName1);
        assertEquals(locationName1, s1.getLocationName());

        locationName2 = "A place";
        s2.setLocationName(locationName2);
        assertEquals(locationName2, s2.getLocationName());

        locationName3 = "";
        s3.setLocationName(locationName3);
        assertEquals(locationName3, s3.getLocationName());
    }

    @Test
    public void TestSetFirebaseId() {
        s1.setFirebaseId("vnoewrpohnvwepinowevpnoi");
        assertEquals("vnoewrpohnvwepinowevpnoi", s1.getFirebaseId());

        s2.setFirebaseId("q34cQ#%Q");
        assertEquals("q34cQ#%Q", s2.getFirebaseId());

        s3.setFirebaseId(null);
        assertEquals(null, s3.getFirebaseId());
    }
}
