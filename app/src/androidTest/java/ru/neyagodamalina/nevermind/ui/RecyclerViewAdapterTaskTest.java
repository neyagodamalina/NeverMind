package ru.neyagodamalina.nevermind.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import ru.neyagodamalina.nevermind.ChildViewAction;
import ru.neyagodamalina.nevermind.InitDatabaseForTest;
import ru.neyagodamalina.nevermind.LiveDataTestUtil;
import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.util.Constants;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import static androidx.test.espresso.Espresso.onView;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecyclerViewAdapterTaskTest extends InitDatabaseForTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

//    @Rule
//    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Test
    public void frozenScrollListTasksWhenPressPlayStop() {
        Log.d(Constants.LOG_TAG, "Try insert in " + database);
        for (int i = 0; i < 10; i++) {
            Task task = new Task("Task" + i);
            mTaskDao.insert(task);
        }

        Calendar now = Calendar.getInstance();
        String taskName = "Task" + now.getTime().toString();
        Task task = new Task(taskName);
        mTaskDao.insert(task);

        for (int i = 10; i < 10; i++) {
            task = new Task("Task" + i);
            mTaskDao.insert(task);
        }

        onView(withId(R.id.navigation_list_tasks)).perform(click());
        Log.i(Constants.LOG_TAG, "Change fragment done!");
        onView(withText(taskName)).check(doesNotExist());
        onView((withId(R.id.rv_navigation_list_tasks))).perform(scrollTo(hasDescendant(withText(taskName))));
        Log.i(Constants.LOG_TAG, "Scroll done!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText(taskName)).check(matches(isDisplayed()));

        MyY myY = new MyY();
        onView(withText(taskName)).check(myY);
        Log.i(Constants.LOG_TAG, "1>>>>>" + myY.y);

        // it works! onView(allOf(withChild(withText(taskName)), myMatcher())).perform(ChildViewAction.clickChildViewWithId(R.id.btn_play));
        //onView(withText(taskName)).perform(ChildViewAction.clickButtonPlayNearTitleView());

        Log.i(Constants.LOG_TAG, "Click done!");
//        onView(withId(R.id.btn_play)).perform(click());


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //onView(withText(taskName)).check(matches(isDisplayed()));
        onView(withText(taskName)).check(matches(isDisplayed()));
        onView(withText(taskName)).check(myY);
        Log.i(Constants.LOG_TAG, "2>>>>>" + myY.y);

//        onView(withId(R.id.navigation_list_tasks)).perform(click());
//        onView(withId(R.id.rv_navigation_list_tasks)).perform(RecyclerViewActions.scrollToHolder(withText(taskName))).perform(ChildViewAction.clickChildViewWithId(R.id.btn_play));

        //perform(RecyclerViewActions.actionOnItemAtPosition(8, ChildViewAction.clickChildViewWithId(R.id.btn_play)));


        //check(matches(withClassName(Matchers.<String>hasToString("ru.neyagodamalina.nevermind.ui.ListTasksFragment"))));


        //onView(withId(R.id.task_date_group)).check(matches(isDisplayed()));

//        onView(withId(R.id.tasks)).perform(click());

//        int[] location = new int[2];
//        v.getLocationOnScreen(location);
//        Log.i(Constants.LOG_TAG, "location=" + location[0] + "\t" + location[1]);

    }

    public static Matcher<View> btPlayOf(final MyY myY)
        {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("button Play near title ");
            }

            @Override
            public boolean matchesSafely(View view) {

                ViewParent viewParent = view.getParent();
                if (viewParent instanceof View){
                    View v = ((View) viewParent).findViewById(R.id.btn_play);
                    Log.i(Constants.LOG_TAG, "myMatcher>>>>>>>>>\t" + v.toString() + ((v instanceof TextView) ? ((TextView) v).getText() : ""));
                }

                return true;
            }
        };
    }

    private static ViewAssertion myAssertion() {
        return new ViewAssertion() {

            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                Log.i(Constants.LOG_TAG, (view == null) ? "null" : "myAssertion>>>>>>>>>\t" + view.toString() + ((view instanceof TextView) ? ((TextView) view).getText() : ""));
            }
        };
    }

    class MyY implements ViewAssertion{

        public int y;

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            y = location[1];

        }
    }
}