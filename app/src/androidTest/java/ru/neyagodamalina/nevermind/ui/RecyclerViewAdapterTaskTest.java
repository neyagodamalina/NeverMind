package ru.neyagodamalina.nevermind.ui;

import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
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
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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


        try {
            Thread.sleep(5000);
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


}