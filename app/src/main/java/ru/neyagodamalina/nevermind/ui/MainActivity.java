package ru.neyagodamalina.nevermind.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;



public class MainActivity extends AppCompatActivity implements ListTasksFragment.OnListFragmentInteractionListener {

//    private static String CURRENT_FRAGMENT = "NO_FRAGMENT";
//    private static String PREVIOUS_FRAGMENT = "NO_FRAGMENT";
//    private static String START_FRAGMENT = Constants.FRAGMENT_CREATE_TASK;

    private static Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        setupBottomMenu(hostFragment.getNavController());




    }

    private void setupBottomMenu(NavController navController) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
//                case R.id.navigation_projects:
//                    showFragment(Constants.FRAGMENT_LIST_PROJECTS);
//                    return true;
//                case R.id.navigation_tasks:
//                    showFragment(Constants.FRAGMENT_LIST_TASKS);
//                    return true;
//                case R.id.navigation_new_task:
//                    showFragment(Constants.FRAGMENT_CREATE_TASK);
//                    return true;
            }
            return false;
        }

    };


    @Override
    public void onListFragmentInteraction(Task item) {
        Toast.makeText(this, "onListFragmentInteraction id = " + item.getId(), Toast.LENGTH_LONG);
    }



    public void setCurrentFragment(Fragment fragment){
        this.fragment = fragment;
    }

    public Fragment getCurrentFragment(){
        return this.fragment;
    }

}
