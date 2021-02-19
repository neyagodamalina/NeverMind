package ru.neyagodamalina.nevermind.ui;

import android.os.Bundle;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import ru.neyagodamalina.nevermind.util.Constants;

public class CommonActivity extends AppCompatActivity {

    private static Fragment fragment;
    protected Calendar createCalendar = Calendar.getInstance();
    private static DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd  HH:mm:ss");

    public String getName(){
        return this.getClass().getSimpleName() + "\t" +  dateFormat.format(new Date(createCalendar.getTimeInMillis()));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.LOG_TAG, this.getName() + "\tonCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(Constants.LOG_TAG, this.getName() + "\tonSaveInstanceState");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constants.LOG_TAG, this.getName() + "\tonDestroy");
    }







    public void setCurrentFragment(Fragment fragment){
        this.fragment = fragment;
    }

    public Fragment getCurrentFragment(){
        return this.fragment;
    }
}
