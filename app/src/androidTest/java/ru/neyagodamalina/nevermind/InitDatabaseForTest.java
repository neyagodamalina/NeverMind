package ru.neyagodamalina.nevermind;

import androidx.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;

import ru.neyagodamalina.nevermind.db.AppDatabase;
import ru.neyagodamalina.nevermind.db.ProjectDao;
import ru.neyagodamalina.nevermind.db.SlotDao;
import ru.neyagodamalina.nevermind.db.TaskDao;

/**
 * Created by developer on 14.11.2017.
 */

public class InitDatabaseForTest {

    private AppDatabase database;

    protected ProjectDao mProjectDao;
    protected TaskDao   mTaskDao;
    protected SlotDao   mSlotDao;

    @Before
    public void init() {
        database = AppDatabase.getInstanceForTest(InstrumentationRegistry.getTargetContext());

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
        }
    }
}
