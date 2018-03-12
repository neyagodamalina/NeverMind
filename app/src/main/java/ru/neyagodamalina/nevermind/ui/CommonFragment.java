package ru.neyagodamalina.nevermind.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.neyagodamalina.nevermind.util.Constants;

/**
 * Created by developer on 26.01.2018.
 */

public class CommonFragment extends Fragment {
    protected Calendar createCalendar = Calendar.getInstance();
    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public String getName(){
        return this.getClass().getSimpleName() + dateFormat.format(new Date(createCalendar.getTimeInMillis()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(Constants.LOG_TAG, this.getName() + "\tonAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.LOG_TAG, this.getName() + "\tonCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, this.getName() + "\tonCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(Constants.LOG_TAG, this.getName() + "\tonActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonDetach");
    }
}
