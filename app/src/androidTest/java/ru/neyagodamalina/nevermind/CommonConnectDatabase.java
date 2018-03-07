package ru.neyagodamalina.nevermind;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import ru.neyagodamalina.nevermind.db.AppDatabase;
import ru.neyagodamalina.nevermind.repository.TaskRepository;
import ru.neyagodamalina.nevermind.util.Constants;

/**
 * Created by developer on 14.11.2017.
 */

public class CommonConnectDatabase {
    public AppDatabase database;
    public TaskRepository taskRepository;

    @Before
    public void init(){
        Context context = InstrumentationRegistry.getTargetContext();

        this.taskRepository = new TaskRepository(context);
        this.database = taskRepository.getDatabase();

        Log.d(Constants.LOG_TAG, "Database init - " + this.getClass().getName());

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void close(){
        if (database != null)
            database.close();
        Log.d(Constants.LOG_TAG, "Database close - " + this.getClass().getName());
    }
}
