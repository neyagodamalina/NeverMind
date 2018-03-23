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
import android.widget.Toast;

import java.util.List;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.util.Constants;


public class MainActivity extends AppCompatActivity implements ListTasksFragment.OnListFragmentInteractionListener {

    private static String CURRENT_FRAGMENT = "NO_FRAGMENT";

    @Override
    public void onBackPressed() {
        super.onBackPressed(); logBackStack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Fragment privaryFragment = createFragmentByTag(Constants.FRAGMENT_LIST_TASKS);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, privaryFragment, Constants.FRAGMENT_LIST_TASKS)
                .commit();
        CURRENT_FRAGMENT = Constants.FRAGMENT_LIST_TASKS;



        //showFragment(Constants.FRAGMENT_LIST_TASKS);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_projects:
                    //makeShowFragment(Constants.FRAGMENT_LIST_PROJECTS, true);
                    logBackStack();
                    return true;
                case R.id.navigation_tasks:
                    makeShowFragment(Constants.FRAGMENT_LIST_TASKS, true);
                    return true;
                case R.id.navigation_new_task:
                    makeShowFragment(Constants.FRAGMENT_CREATE_TASK, true);
                    return true;
            }
            return false;
        }

    };


    @Override
    public void onListFragmentInteraction(Task item) {
        Toast.makeText(this, "onListFragmentInteraction id = " + item.getId(), Toast.LENGTH_LONG);
    }



    private void showFragment(String tagFragment){

        BottomNavigationView navigation =  findViewById(R.id.navigation);

        switch (tagFragment) {
            case Constants.FRAGMENT_LIST_TASKS:
                navigation.setSelectedItemId(R.id.navigation_tasks);
                break;
            case Constants.FRAGMENT_CREATE_TASK:
                navigation.setSelectedItemId(R.id.navigation_new_task);
                break;
            default:
                navigation.setSelectedItemId(R.id.navigation_tasks);
        }
    }

    private void makeShowFragment(String tagFragment, boolean isAddToBackStack){

        if (CURRENT_FRAGMENT.equals(tagFragment)) return;

        Fragment fragment = createFragmentByTag(tagFragment);

        //Log.i(Constants.LOG_TAG, getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_LIST_TASKS).toString());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment, tagFragment);

        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(tagFragment);

        fragmentTransaction.commit();

        CURRENT_FRAGMENT = tagFragment;
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

        List<Fragment> fragmentList = manager.getFragments();
        for (Fragment fragment : fragmentList){
            Log.i(Constants.LOG_TAG, "Manager\t" + fragment.getTag());
        }

        Log.i(Constants.LOG_TAG,"--------------------");

    }

    public void clearBackStack(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(Constants.FRAGMENT_CREATE_TASK);
        //manager.popBackStackImmediate(Constants.FRAGMENT_LIST_TASKS, 0);
        //int count = manager.getBackStackEntryCount();
        //Log.i(Constants.LOG_TAG, "Count entry in back stack = " + count);
    }

}
