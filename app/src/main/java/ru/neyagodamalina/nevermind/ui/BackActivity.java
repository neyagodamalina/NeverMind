package ru.neyagodamalina.nevermind.ui;

import androidx.appcompat.widget.Toolbar;
import ru.neyagodamalina.nevermind.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class BackActivity extends CommonActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);

        Toolbar toolbar = findViewById(R.id.back_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // What kind of fragment to be placed in the activity layout
            CommonFragment commonFragment = null;
            int fragmentId = getIntent().getIntExtra("fragmentId", 0);
            switch (fragmentId) {
                case R.layout.fragment_create_task:
                    commonFragment = CreateEditTaskFragment.newInstance(getIntent().getLongExtra("taskId", 0));
                    break;
            }


            // Add the fragment to the 'fragment_container' FrameLayout
            if (commonFragment != null)
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, commonFragment).commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate toolbar menu depend on fragment
        int fragmentId = getIntent().getIntExtra("fragmentId", 0);
        switch (fragmentId) {
            case R.layout.fragment_create_task:
                getMenuInflater().inflate(R.menu.toolbar_task, menu);
                break;
        }
        return true;
    }


}
