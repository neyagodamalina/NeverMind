package ru.neyagodamalina.nevermind.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import ru.neyagodamalina.nevermind.util.Constants;

public class Behavior extends HideBottomViewOnScrollBehavior {


    public Behavior() {
    }

    public Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void slideUp(View child) {
        super.slideUp(child);
        Log.d(Constants.LOG_TAG, "slideUp");
    }

    @Override
    public void slideDown(View child) {
        super.slideDown(child);
        Log.d(Constants.LOG_TAG, "slideDown");
    }
}
