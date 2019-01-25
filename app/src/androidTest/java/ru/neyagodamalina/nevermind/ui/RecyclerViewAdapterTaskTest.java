package ru.neyagodamalina.nevermind.ui;

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
public class RecyclerViewAdapterTaskTest extends InitDatabaseForTest{

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void frozenScrollListTasksWhenPressPlayStop() {
        //Context appContext = ApplicationProvider.getApplicationContext();
        for (int i = 0; i < 10; i++) {
            Task task = new Task("Task" + i);
            mTaskDao.insert(task);
        }

        Calendar now = Calendar.getInstance();
        String taskName = "Task" + now.getTime().toString();
        Task task = new Task(taskName);
        mTaskDao.insert(task);

        for (int i = 10; i < 20; i++) {
            task = new Task("Task" + i);
            mTaskDao.insert(task);
        }

//        taskName = "Task2";
        onView(withId(R.id.navigation_list_tasks)).perform(click());
        onView(withText(taskName)).check(doesNotExist());
        onView((withId(R.id.rv_navigation_list_tasks))).perform(scrollTo(hasDescendant(withText(taskName))));
        onView(withText(taskName)).check(matches(isDisplayed()));

        onView(allOf(withParent(withParent(withChild(withText(taskName)))), myMatcher())).perform(click());


//        onView(withId(R.id.btn_play)).perform(click());


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        onView(withId(R.id.navigation_list_tasks)).perform(click());
//        onView(withId(R.id.rv_navigation_list_tasks)).perform(RecyclerViewActions.scrollToHolder(withText(taskName))).perform(ChildViewAction.clickChildViewWithId(R.id.btn_play));

        //perform(RecyclerViewActions.actionOnItemAtPosition(8, ChildViewAction.clickChildViewWithId(R.id.btn_play)));



        //check(matches(withClassName(Matchers.<String>hasToString("ru.neyagodamalina.nevermind.ui.ListTasksFragment"))));


        //onView(withId(R.id.task_date_group)).check(matches(isDisplayed()));

//        onView(withId(R.id.tasks)).perform(click());

//        int[] location = new int[2];
//        v.getLocationOnScreen(location);
//        Log.d(Constants.LOG_TAG, "location=" + location[0] + "\t" + location[1]);

    }
    private static Matcher<View> myMatcher(
            ) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                Log.d(Constants.LOG_TAG, "describeTo" + description);
                description.appendText("myMatcher 1111");
            }

            @Override
            public boolean matchesSafely(View view) {
                    Log.d(Constants.LOG_TAG, "myMatcher>>>>>>>>>\t" + view.toString() + ((view instanceof TextView)? ((TextView) view).getText():""));
                return true;
            }
        };
    }

    private static ViewAssertion myAssertion(){
        return new ViewAssertion(){

            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                Log.d(Constants.LOG_TAG, (view == null)? "null" : "myAssertion>>>>>>>>>\t" + view.toString() + ((view instanceof TextView)? ((TextView) view).getText():""));
            }
        };
    }

}