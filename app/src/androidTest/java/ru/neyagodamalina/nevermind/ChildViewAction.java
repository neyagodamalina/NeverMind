package ru.neyagodamalina.nevermind;

import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Matcher;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import ru.neyagodamalina.nevermind.ui.RecyclerViewAdapterTaskTest;
import ru.neyagodamalina.nevermind.util.Constants;

/**
 * Click for child View into row RecycleView
 */

public class ChildViewAction {
    public static ViewAction clickButtonPlayNearTitleView() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isDisplayed();
            }

            @Override
            public String getDescription() {
                return "****************Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController,
                                View view) {
                ViewParent viewParent = view.getParent();
                if (viewParent instanceof View){
                    View v = ((View) viewParent).findViewById(R.id.btn_play);
                    //Log.i(Constants.LOG_TAG, "myAction>>>>>>>>>\t" + v.toString() + ((v instanceof TextView) ? ((TextView) v).getText() : ""));
                    v.performClick();
                }
                Log.i(Constants.LOG_TAG, "myAction>>>>>>>>>\t");

//                Log.i(Constants.LOG_TAG, view.toString());
//                Log.i(Constants.LOG_TAG, v.toString());
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//                Log.i(Constants.LOG_TAG, "Before location=" + location[0] + "\t" + location[1]);
//                v.performClick();
//                v.getLocationOnScreen(location);
//                Log.i(Constants.LOG_TAG, "After location=" + location[0] + "\t" + location[1]);

            }
        };
    }
}
