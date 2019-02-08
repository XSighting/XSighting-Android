package com.hwang.xsighting;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateReportTests {

    @Rule
    public ActivityTestRule<CreateSighting> createSightingActivity = new ActivityTestRule<>(CreateSighting.class);
    private String stringToBeTyped;

    @Before
    public void initValidString() {
        // Specify a valid string.
        stringToBeTyped = "Espresso testing 123";
    }


    /*
     * UI elements tested in the order they appear on the CreateSighting activity from top to bottom.
     */

    // Header TextView
    @Test
    public void headerReportSightingWithText() {
        onView(withId(R.id.header_report_sighting)).check(matches(withText(R.string.header_report_sighting)));
    }

    @Test
    public void headerReportSightingIsDisplayed() {
        onView(withId(R.id.header_report_sighting)).check(matches(isDisplayed()));
    }

    @Test
    public void headerReportSightingIsCompletelyDisplayed() {
        onView(withId(R.id.header_report_sighting)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void headerReportSightingIsNotSelectable() {
        onView(withId(R.id.header_report_sighting)).check(matches(not(isSelected())));
    }

    // Description Label TextView
    @Test
    public void reportDescriptionLabelWithText() {
        onView(withId(R.id.report_description_label)).check(matches(withText(R.string.description_tag)));
    }

    @Test
    public void reportDescriptionLabelIsDisplayed() {
        onView(withId(R.id.report_description_label)).check(matches(isDisplayed()));
    }

    @Test
    public void reportDescriptionLabelIsCompletelyDisplayed() {
        onView(withId(R.id.report_description_label)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void reportDescriptionLabelIsNotSelectable() {
        onView(withId(R.id.report_description_label)).check(matches(not(isSelected())));
    }

    // Description EditText Field
    @Test
    public void reportDescriptionWithHint(){
        onView(withId(R.id.report_description)).check(matches(withHint(R.string.include_color_shape_of_object_sounds_etc)));
    }

    @Test
    public void reportDescriptionIsEnabled() {
        onView(withId(R.id.report_description)).check(matches(isEnabled()));
    }

    @Test
    public void reportDescriptionIsDisplayed() {
        onView(withId(R.id.report_description)).check(matches(isDisplayed()));
    }

    @Test
    public void reportDescriptionIsCompletelyDisplayed() {
        onView(withId(R.id.report_description)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void reportDescriptionIsNotSelectable() {
        onView(withId(R.id.report_description)).check(matches(not(isSelected())));
    }

    @Test
    public void reportDescriptionIsClickable() {
        onView(withId(R.id.report_description)).check(matches(isClickable()));
    }

    @Test
    public void reportDescriptionCanTypeText() {
        // Type text
        onView(withId(R.id.report_description))
                .perform(typeText(stringToBeTyped), closeSoftKeyboard());
        // Check that the text was changed
        onView(withId(R.id.report_description))
                .check(matches(withText(stringToBeTyped)));
    }

    // Add Photo Label TextView
    @Test
    public void addPhotoLabelWithText() {
        onView(withId(R.id.addPhoto)).check(matches(withText(R.string.add_photo_tag)));
    }

    @Test
    public void addPhotoLabelIsDisplayed() {
        onView(withId(R.id.addPhoto)).check(matches(isDisplayed()));
    }

    @Test
    public void addPhotoLabelIsCompletelyDisplayed() {
        onView(withId(R.id.addPhoto)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void addPhotoLabelIsNotSelectable() {
        onView(withId(R.id.addPhoto)).check(matches(not(isSelected())));
    }

    // Take Picture Button
    @Test
    public void takePictureButtonIsEnabled() {
        onView(withId(R.id.takePictureButton)).check(matches(isEnabled()));
    }

    @Test
    public void takePictureButtonIsDisplayed() {
        onView(withId(R.id.takePictureButton)).check(matches(isDisplayed()));
    }

    @Test
    public void takePictureButtonIsCompletelyDisplayed() {
        onView(withId(R.id.takePictureButton)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void takePictureButtonIsNotSelectable() {
        onView(withId(R.id.takePictureButton)).check(matches(not(isSelected())));
    }

    @Test
    public void takePictureButtonWithText() {
        onView(withId(R.id.takePictureButton)).check(matches(withText(R.string.report_take_picture)));
    }

    @Test
    public void takePictureButtonIsClickable() {
        onView(withId(R.id.takePictureButton)).check(matches(isClickable()));
    }

    // Choose Picture Button
    @Test
    public void choosePictureButtonIsEnabled() {
        onView(withId(R.id.choosePictureButton)).check(matches(isEnabled()));
    }

    @Test
    public void choosePictureButtonIsDisplayed() {
        onView(withId(R.id.choosePictureButton)).check(matches(isDisplayed()));
    }

    @Test
    public void choosePictureButtonIsCompletelyDisplayed() {
        onView(withId(R.id.choosePictureButton)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void choosePictureButtonIsNotSelectable() {
        onView(withId(R.id.choosePictureButton)).check(matches(not(isSelected())));
    }

    @Test
    public void choosePictureButtonWithText() {
        onView(withId(R.id.choosePictureButton)).check(matches(withText(R.string.report_choose_picture)));
    }

    @Test
    public void choosePictureButtonIsClickable() {
        onView(withId(R.id.choosePictureButton)).check(matches(isClickable()));
    }

    // Report Photo - Before Adding Photo
    @Test
    public void reportPhotoIsNotDisplayed() {
        onView(withId(R.id.sightingImage)).check(matches(not(isDisplayed())));
    }

    @Test
    public void reportPhotoIsCompletelyNotDisplayed() {
        onView(withId(R.id.sightingImage)).check(matches(not(isCompletelyDisplayed())));
    }

    @Test
    public void reportPhotoIsNotSelectable() {
        onView(withId(R.id.sightingImage)).check(matches(not(isSelected())));
    }

    // Submit Button
    @Test
    public void submitReportButtonIsEnabled() {
        onView(withId(R.id.button_submit_report)).check(matches(isEnabled()));
    }

    @Test
    public void submitReportButtonIsDisplayed() {
        onView(withId(R.id.button_submit_report)).check(matches(isDisplayed()));
    }

    @Test
    public void submitReportButtonIsCompletelyDisplayed() {
        onView(withId(R.id.button_submit_report)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void submitReportButtonIsNotSelectable() {
        onView(withId(R.id.button_submit_report)).check(matches(not(isSelected())));
    }

    @Test
    public void submitReportButtonIsClickable() {
        onView(withId(R.id.button_submit_report)).check(matches(isClickable()));
    }

    @Test
    public void submitReportButtonWithText() {
        onView(withId(R.id.button_submit_report)).check(matches(withText(R.string.report_submit)));
    }
}
