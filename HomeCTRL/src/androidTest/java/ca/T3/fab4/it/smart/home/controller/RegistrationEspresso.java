package ca.T3.fab4.it.smart.home.controller;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegistrationEspresso {

    @Rule
    public ActivityTestRule<RegistrationActivity> mActivityTestRule = new ActivityTestRule<>(RegistrationActivity.class);

    @Test
    public void registration_Espresso() {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("John"), closeSoftKeyboard());


        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.phone),
                       childAtPosition(
                               childAtPosition(
                                        withId(android.R.id.content),
                                       0),
                               5),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("5195782584"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
               allOf(withId(R.id.email),
                       childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                       0),
                               2),
                       isDisplayed()));
       appCompatEditText4.perform(replaceText("john.smith@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.email), withText("john.smith@gmail.com"),
                       childAtPosition(
                               childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText5.perform(pressImeActionButton());

       ViewInteraction appCompatEditText6 = onView(
               allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                       withId(android.R.id.content),
                                        0),
                               3),
                       isDisplayed()));
       appCompatEditText6.perform(replaceText("Abc@1234"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.password), withText("Abc@1234"),
                       childAtPosition(
                               childAtPosition(
                                       withId(android.R.id.content),
                                      0),
                               3),
                        isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());

       ViewInteraction appCompatEditText8 = onView(
               allOf(withId(R.id.password2),
                        childAtPosition(
                               childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("Abc@1234"), closeSoftKeyboard());

       ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.password2), withText("Abc@1234"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                               4),
                        isDisplayed()));
        appCompatEditText9.perform(pressImeActionButton());

        ViewInteraction materialButton2 = onView(
               allOf(withId(R.id.sign), withText("Create New Account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                       0),
                                0),
                       isDisplayed()));
        materialButton2.perform(click());
  }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
