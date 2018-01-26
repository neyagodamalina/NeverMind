package ru.neyagodamalina.nevermind.persistence;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.After;
import org.junit.Before;

import ru.neyagodamalina.nevermind.business.persistence.AppDatabase;
import ru.neyagodamalina.nevermind.business.persistence.Project;
import ru.neyagodamalina.nevermind.business.util.Constants;

/**
 * Created by developer on 14.11.2017.
 */

public class CommonConnectDatabase {
    AppDatabase database;

    @Before
    public void init(){
        database =
                //real database
                AppDatabase.getDatabase(InstrumentationRegistry.getTargetContext());
                //temp database
                //Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), AppDatabase.class).build();
        Log.d(Constants.LOG_TAG, "Database init - " + this.getClass().getName());
        database.getProjectDao().deleteAllProjects();
        database.getProjectDao().insertProject(new Project(1,"My live", 0, 0));
    }

    @After
    public void close(){
        if (database != null)
            database.close();
        Log.d(Constants.LOG_TAG, "Database close - " + this.getClass().getName());
    }
}