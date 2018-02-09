package ru.neyagodamalina.nevermind;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import ru.neyagodamalina.nevermind.business.Duration;
import ru.neyagodamalina.nevermind.business.persistence.Task;
import ru.neyagodamalina.nevermind.business.util.Constants;
import ru.neyagodamalina.nevermind.business.util.FormatDuration;
import ru.neyagodamalina.nevermind.fragment.CreateTaskFragment;
import ru.neyagodamalina.nevermind.fragment.TaskFragment;
import ru.neyagodamalina.nevermind.fragment.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton bt_add;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Constants.LOG_TAG, this.getClass().getSimpleName() + "\tonCreate");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt_add = (FloatingActionButton) findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        TaskFragment taskFragment = TaskFragment.newInstance(1);
        fragmentTransaction.replace(R.id.fragment_container, taskFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        Toast toast = Toast.makeText(getApplicationContext(), "Hiiiiiiiya!", Toast.LENGTH_SHORT);
        toast.show();




        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                CreateTaskFragment createTaskFragment = new CreateTaskFragment();
                fragmentTransaction.replace(R.id.fragment_container, createTaskFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Toast toast = Toast.makeText(getApplicationContext(), "Hiiiiiiiya!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Constants.LOG_TAG, this.getClass().getSimpleName() + "\tonPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constants.LOG_TAG, this.getClass().getSimpleName() + "\tonResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Constants.LOG_TAG, this.getClass().getSimpleName() + "\tonStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Constants.LOG_TAG, this.getClass().getSimpleName() + "\tonRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constants.LOG_TAG, this.getClass().getSimpleName() + "\tonDestroy");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Log.d(Constants.LOG_TAG, this.getClass().getSimpleName() + "\tonRestoreInstanceState");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(Constants.LOG_TAG, this.getClass().getSimpleName() + "\tonSaveInstanceState");
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onListFragmentInteraction(Task item) {
        Log.d(Constants.LOG_TAG, "Interaction with " + item);
    }
}
