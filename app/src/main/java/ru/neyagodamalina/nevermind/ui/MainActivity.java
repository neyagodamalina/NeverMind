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
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.ui.CreateTaskFragment;
import ru.neyagodamalina.nevermind.ui.ListTasksFragment;
import ru.neyagodamalina.nevermind.util.Constants;


public class MainActivity extends AppCompatActivity implements ListTasksFragment.OnListFragmentInteractionListener {

    private static String CURRENT_FRAGMENT = "NO_FRAGMENT";

    @Override
    public void onBackPressed() {
        //getFragmentManager().get
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        showFragment(Constants.LIST_TASKS_FRAGMENT);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_projects:
                    return true;
                case R.id.navigation_tasks:
                    makeShowFragment(Constants.LIST_TASKS_FRAGMENT, true);
                    return true;
                case R.id.navigation_new_task:
                    makeShowFragment(Constants.CREATE_TASK_FRAGMENT, true);
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
            case Constants.LIST_TASKS_FRAGMENT:
                navigation.setSelectedItemId(R.id.navigation_tasks);
                break;
            case Constants.CREATE_TASK_FRAGMENT:
                navigation.setSelectedItemId(R.id.navigation_new_task);
                break;
            default:
                navigation.setSelectedItemId(R.id.navigation_tasks);
        }
    }

    private void makeShowFragment(String tagFragment, boolean isAddToBackStack){

        if (CURRENT_FRAGMENT.equals(tagFragment)) return;

        Fragment fragment = getFragmentByTag(tagFragment);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment, tagFragment);

        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        CURRENT_FRAGMENT = tagFragment;
    }



    private Fragment getFragmentByTag(String tag){

        Fragment fragment;

        switch (tag) {
            case Constants.LIST_TASKS_FRAGMENT:
                fragment = new ListTasksFragment();
                break;
            case Constants.CREATE_TASK_FRAGMENT:
                fragment = new CreateTaskFragment();
                break;
            default:
                fragment = new ListTasksFragment();
        }

        return fragment;

    }


}
