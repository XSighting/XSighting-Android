package com.hwang.xsighting.models;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class UserTest {
    User u1 = new User();
    User u2 = new User("usertokenusertoken");

    @Test
    public void testGetCreatedSightings() {
        assertEquals(new HashMap<String, Boolean>(), u1.getCreatedSightings());
        assertEquals(new HashMap<String, Boolean>(), u2.getCreatedSightings());
    }

    @Test
    public void testSetCreatedSightings() {
        HashMap<String, Boolean> hashmap1 = new HashMap<>();
        hashmap1.put("one", true);
        hashmap1.put("two", true);
        hashmap1.put("three", true);
        u1.setCreatedSightings(hashmap1);
        assertEquals(hashmap1, u1.getCreatedSightings());

        HashMap<String, Boolean> hashmap2 = new HashMap<>();
        hashmap1.put("asdf", true);
        hashmap1.put("2345235", true);
        hashmap1.put("get shwifty", true);
        u1.setCreatedSightings(hashmap2);
        assertEquals(hashmap2, u2.getCreatedSightings());
    }

    @Test
    public void testAddCreatedsighting() {
        HashMap<String, Boolean> hashmap1 = new HashMap<>();
        hashmap1.put("four", true);
        hashmap1.put("five", true);
        hashmap1.put("six", true);

        u1.addCreatedsighting("four");
        u1.addCreatedsighting("five");
        u1.addCreatedsighting("six");
        assertEquals(hashmap1, u1.getCreatedSightings());

        HashMap<String, Boolean> hashmap2 = new HashMap<>();
        hashmap2.put("%$#@", true);
        hashmap2.put("1q2w3er4t5", true);
        hashmap2.put("", true);

        u2.addCreatedsighting("%$#@");
        u2.addCreatedsighting("1q2w3er4t5");
        u2.addCreatedsighting("");
        assertEquals(hashmap2, u2.getCreatedSightings());
    }

    @Test
    public void testToken() {
        assertEquals("usertokenusertoken", u2.deviceToken);
    }
}
