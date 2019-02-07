package com.hwang.xsighting;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewSightingTest {

  @Rule
  public ActivityTestRule<ViewSighting> viewSightingActivityTestRule = new ActivityTestRule<>(ViewSighting.class);
  // UI Tests
  @Test
  public void viewSightingTextViewMatchTest() {
//    onView(withId(R.id.postTitle)).check(matches(withText(R.string.detail_title)));
    onView(withId(R.id.userTag)).check(matches(withText(R.string.detail_user_tag)));
//    onView(withId(R.id.postUser)).check(matches(withText(R.string.detail_username)));
    onView(withId(R.id.textView_detail_time)).check(matches(withText(R.string.detail_time)));
//    onView(withId(R.id.postDate)).check(matches(withText(R.string.detail_default)));
    onView(withId(R.id.descriptionTag)).check(matches(withText(R.string.detail_user_tag)));
//    onView(withId(R.id.postDescription)).check(matches(withText(R.string.detail_default)));
  }


}
