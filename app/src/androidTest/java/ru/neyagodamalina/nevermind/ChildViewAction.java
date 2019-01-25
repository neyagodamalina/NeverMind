package ru.neyagodamalina.nevermind;

import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import ru.neyagodamalina.nevermind.util.Constants;

/**
 * Click for child View into row RecycleView
 */

public class ChildViewAction {
    public static ViewAction clickChildViewWithId(
            final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "****************Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController,
                                View view) {
                View v = view.findViewById(id);
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                Log.d(Constants.LOG_TAG, "Before location=" + location[0] + "\t" + location[1]);
                v.performClick();
                v.getLocationOnScreen(location);
                Log.d(Constants.LOG_TAG, "After location=" + location[0] + "\t" + location[1]);

            }
        };
    }
}