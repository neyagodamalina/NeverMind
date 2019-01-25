package ru.neyagodamalina.nevermind.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import ru.neyagodamalina.nevermind.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
//        onView(withId(R.id.navigation_list_tasks)).perform(click());
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ViewInteraction appCompatButton = onView(
//                allOf(withId(R.id.btCancelAlarm),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        1),
//                                1),
//                        isDisplayed()));
//        appCompatButton.perform(click());
//
//        ViewInteraction appCompatButton2 = onView(
//                allOf(withId(R.id.btCancelAlarm),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        1),
//                                1),
//                        isDisplayed()));
//        appCompatButton2.perform(click());
//
//        ViewInteraction appCompatButton3 = onView(
//                allOf(withId(R.id.btCancelAlarm),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        1),
//                                1),
//                        isDisplayed()));
//        appCompatButton3.perform(click());
//
//        ViewInteraction appCompatButton4 = onView(
//                allOf(withId(R.id.btCancelAlarm),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        1),
//                                1),
//                        isDisplayed()));
//        appCompatButton4.perform(click());
//
//        ViewInteraction appCompatButton5 = onView(
//                allOf(withId(R.id.btCancelAlarm),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        1),
//                                1),
//                        isDisplayed()));
//        appCompatButton5.perform(click());
//
//        ViewInteraction appCompatButton6 = onView(
//                allOf(withId(R.id.btCancelAlarm),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        1),
//                                1),
//                        isDisplayed()));
//        appCompatButton6.perform(click());
//
//        ViewInteraction appCompatButton7 = onView(
//                allOf(withId(R.id.btCancelAlarm),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        1),
//                                1),
//                        isDisplayed()));
//        appCompatButton7.perform(click());
//
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_list_tasks), withContentDescription("Tasks"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

                try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//
//        ViewInteraction appCompatImageButton = onView(
//                allOf(withId(R.id.btn_play),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        0),
//                                1),
//                        isDisplayed()));
//        appCompatImageButton.perform(click());
//
//        ViewInteraction appCompatImageButton2 = onView(
//                allOf(withId(R.id.btn_play),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        0),
//                                1),
//                        isDisplayed()));
//        appCompatImageButton2.perform(click());
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
