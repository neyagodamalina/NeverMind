package ru.neyagodamalina.nevermind.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.util.Constants;


public class MainActivity extends AppCompatActivity implements ListTasksFragment.OnListFragmentInteractionListener {

    private static String CURRENT_FRAGMENT = "NO_FRAGMENT";
    private static String PREVIOUS_FRAGMENT = "NO_FRAGMENT";
    private static String START_FRAGMENT = Constants.FRAGMENT_CREATE_TASK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button btLogBackStack = findViewById(R.id.logBackStack);
        btLogBackStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logBackStack();
            }
        });

        Button btClearBackStack = findViewById(R.id.clearBackStack);
        btClearBackStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                Fragment fragmentListProjects = createFragmentByTag(Constants.FRAGMENT_LIST_PROJECTS);
                Fragment fragmentListTasks = createFragmentByTag(Constants.FRAGMENT_LIST_TASKS);
                manager.beginTransaction()
                        .replace(R.id.fragment_container, fragmentListProjects, Constants.FRAGMENT_LIST_PROJECTS)
                        .addToBackStack(Constants.FRAGMENT_LIST_PROJECTS)
                        .replace(R.id.fragment_container, fragmentListTasks, Constants.FRAGMENT_LIST_TASKS)
                        .addToBackStack(Constants.FRAGMENT_LIST_TASKS)
                        .commit();

                // manager.popBackStack(Constants.FRAGMENT_LIST_PROJECTS, 0);
                // updateNavigationBarState(Constants.FRAGMENT_LIST_PROJECTS);
                logBackStack();
            }
        });



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



    boolean isTransactionInBackStack(String tagFragment){
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


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        Fragment fragment = manager.findFragmentByTag(tagFragment);
//        if (fragment == null) {
        Fragment  fragment = createFragmentByTag(tagFragment);
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragment_container, fragment, tagFragment)
                    .addToBackStack(tagFragment)
                    .commit();
//        }
//        else{
//            transaction.attach(fragment);
//        }

//        Fragment currentFragment = manager.getPrimaryNavigationFragment();
//        if (currentFragment != null) {
//            transaction.detach(currentFragment);
//        }

//        transaction.setPrimaryNavigationFragment(fragment);
//        transaction.setReorderingAllowed(true);
//        transaction.commit();

        PREVIOUS_FRAGMENT = CURRENT_FRAGMENT;
        CURRENT_FRAGMENT = tagFragment;

        updateNavigationBarState(tagFragment);

        logBackStack();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        FragmentManager manager = getSupportFragmentManager();
        int count = manager.getBackStackEntryCount();
        CURRENT_FRAGMENT = PREVIOUS_FRAGMENT;

        if (count > 0) {
            String currentFragmentTag = manager.getBackStackEntryAt(count - 1).getName();
            PREVIOUS_FRAGMENT = currentFragmentTag;
        }
        else PREVIOUS_FRAGMENT = START_FRAGMENT;

        updateNavigationBarState(CURRENT_FRAGMENT);
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

        Log.i(Constants.LOG_TAG,PREVIOUS_FRAGMENT + " -> " + CURRENT_FRAGMENT);

    }





}
