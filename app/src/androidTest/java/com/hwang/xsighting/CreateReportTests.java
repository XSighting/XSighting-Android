package com.hwang.xsighting;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.OutputStream;

import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
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
    public void reportDescriptionWithText() {
        onView(withId(R.id.report_description_label)).check(matches(withText(R.string.description_tag)));
    }

    @Test
    public void reportDescriptionLabelWithHint(){
        onView(withId(R.id.report_description)).check(matches(withHint(R.string.include_color_shape_of_object_sounds_etc)));
    }




}
