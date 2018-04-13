package ru.neyagodamalina.nevermind.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.util.Constants;


public class MainActivity extends AppCompatActivity implements ListTasksFragment.OnListFragmentInteractionListener {

    private static String CURRENT_FRAGMENT = "NO_FRAGMENT";
    private static String PREVIOUS_FRAGMENT = "NO_FRAGMENT";
    private static String START_FRAGMENT = Constants.FRAGMENT_CREATE_TASK;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        FragmentManager manager = getSupportFragmentManager();
//        int count = manager.getBackStackEntryCount();
//        if (count > 0) {
//            String currentFragmentTag = manager.getBackStackEntryAt(count - 1).getName();
//            CURRENT_FRAGMENT = currentFragmentTag;
//        }
//        else CURRENT_FRAGMENT = START_FRAGMENT;
        CURRENT_FRAGMENT = PREVIOUS_FRAGMENT;
        updateNavigationBarState(CURRENT_FRAGMENT);
        logBackStack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


//        Fragment privacyFragment = createFragmentByTag(Constants.FRAGMENT_LIST_TASKS);
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragment_container, privacyFragment, Constants.FRAGMENT_LIST_TASKS)
//                .commit();
//        CURRENT_FRAGMENT = Constants.FRAGMENT_LIST_TASKS;



        showFragment(START_FRAGMENT);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_projects:
                    showFragment(Constants.FRAGMENT_LIST_PROJECTS);
                    return true;
                case R.id.navigation_tasks:
                    showFragment(Constants.FRAGMENT_LIST_TASKS);
                    return true;
                case R.id.navigation_new_task:
                    showFragment(Constants.FRAGMENT_CREATE_TASK);
                    return true;
            }
            return false;
        }

    };


    @Override
    public void onListFragmentInteraction(Task item) {
        Toast.makeText(this, "onListFragmentInteraction id = " + item.getId(), Toast.LENGTH_LONG);
    }



    boolean isFragmentInBackStack(String tagFragment){
        FragmentManager manager = getSupportFragmentManager();
        int count = manager.getBackStackEntryCount();
        int n = 0;
        for (int i = 0; i < count; i++){
            if (tagFragment.equals(manager.getBackStackEntryAt(i).getName()))
                return true;
        }
        return false;
    }

    private void showFragment(String tagFragment) {

        if (CURRENT_FRAGMENT.equals(tagFragment)) return;

        Fragment fragment = createFragmentByTag(tagFragment);

        //Log.i(Constants.LOG_TAG, getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_LIST_TASKS).toString());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment, tagFragment);


        //String transactionName = CURRENT_FRAGMENT + "->" + tagFragment;
        if (!isFragmentInBackStack(tagFragment)  && !tagFragment.equals(START_FRAGMENT))
            fragmentTransaction.addToBackStack(tagFragment);

        fragmentTransaction.commit();

        PREVIOUS_FRAGMENT = CURRENT_FRAGMENT;
        CURRENT_FRAGMENT = tagFragment;

        updateNavigationBarState(tagFragment);

        logBackStack();

    }

    private void updateNavigationBarState(String tagFragment){

        int actionId = -1;
        switch (tagFragment) {
            case Constants.FRAGMENT_LIST_PROJECTS:
                actionId = R.id.navigation_projects;
                break;
            case Constants.FRAGMENT_LIST_TASKS:
                actionId = R.id.navigation_tasks;
                break;
            case Constants.FRAGMENT_CREATE_TASK:
                actionId = R.id.navigation_new_task;
                break;
        }

        if (actionId != -1) {
            BottomNavigationView navigation =  ((BottomNavigationView) (findViewById(R.id.navigation)));
            navigation.setSelectedItemId(actionId);
        }

    }

    private Fragment createFragmentByTag(String tag){

        Fragment fragment;

        switch (tag) {
            case Constants.FRAGMENT_LIST_PROJECTS:
                fragment = new ListProjectsFragment();
                break;
            case Constants.FRAGMENT_LIST_TASKS:
                fragment = new ListTasksFragment();
                break;
            case Constants.FRAGMENT_CREATE_TASK:
                fragment = new CreateTaskFragment();
                break;
            default:
                fragment = new ListTasksFragment();
        }

        return fragment;

    }

    public void logBackStack(){
        FragmentManager manager = getSupportFragmentManager();
        int count = manager.getBackStackEntryCount();
        if (count == 0) Log.i(Constants.LOG_TAG, "Count entry in back stack = " + count);
        for (int i = 0; i < count; i++){
            Log.i(Constants.LOG_TAG, "Back stack\t" + manager.getBackStackEntryAt(i).getId() + "\t" + manager.getBackStackEntryAt(i).getName());
        }

        Log.i(Constants.LOG_TAG,"--------------------" + CURRENT_FRAGMENT);

    }





}
