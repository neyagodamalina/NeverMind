package ru.neyagodamalina.nevermind.ui;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    /**
     * Create main toolbar menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    /**
     * Press menu item on main toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this,"Press settings", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item); // important line
    }

    public void setCurrentFragment(Fragment fragment){
        this.fragment = fragment;
    }

    public Fragment getCurrentFragment(){
        return this.fragment;
    }

}
