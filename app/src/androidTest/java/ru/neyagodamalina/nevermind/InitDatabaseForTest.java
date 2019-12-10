package ru.neyagodamalina.nevermind;


import android.util.Log;

import 	androidx.test.core.app.ApplicationProvider;


import org.junit.After;
import org.junit.Before;

import ru.neyagodamalina.nevermind.db.AppDatabase;
import ru.neyagodamalina.nevermind.db.ProjectDao;
import ru.neyagodamalina.nevermind.db.SlotDao;
import ru.neyagodamalina.nevermind.db.TaskDao;
import ru.neyagodamalina.nevermind.util.Constants;

/**
 * Created by developer on 14.11.2017.
 */

public class InitDatabaseForTest {

    protected AppDatabase database;

    protected ProjectDao mProjectDao;
    protected TaskDao   mTaskDao;
    protected SlotDao   mSlotDao;

    @Before
    public void init() {

        database = AppDatabase.getInstanceForTest(ApplicationProvider.getApplicationContext());
        Log.i(Constants.LOG_TAG, ">>>>>> Init database for test " + this.getClass().getSimpleName() + ". Database instance is " + database);
        mProjectDao = database.getProjectDao();
        mTaskDao    = database.getTaskDao();
        mSlotDao    = database.getSlotDao();

        // Only for populate default data in test database
        database.query("select 1", null);

        // Wait while database is populate
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        MockitoAnnotations.initMocks(this);
    }

    @After
    public void close() {
        if (database != null) {
            database.close();
            Log.i(Constants.LOG_TAG, "<<<<<< Closed database for test " + this.getClass().getSimpleName()  + ". Database instance is " + database);
            Log.i(Constants.LOG_TAG,"------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }
}
