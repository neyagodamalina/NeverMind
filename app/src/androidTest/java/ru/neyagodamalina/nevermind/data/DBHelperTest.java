package ru.neyagodamalina.nevermind.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import ru.neyagodamalina.nevermind.business.Slot;
import ru.neyagodamalina.nevermind.business.util.Constants;
import ru.neyagodamalina.nevermind.business.util.FormatDuration;

import static org.junit.Assert.*;

/**
 * Created by developer on 30.10.2017.
 */
@RunWith(AndroidJUnit4.class)
public class DBHelperTest {

    @Test
    public void testDB() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        Log.d(Constants.LOG_TAG,"dwdwdwdwdwdwdw");

        DBHelper dbHelper = new DBHelper(appContext);

        dbHelper.insertTestData();

//        db.execSQL(DBContract.SQL_DELETE_PROJECTS);
//        db.execSQL(DBContract.SQL_DELETE_TASKS);
//        db.execSQL(DBContract.SQL_DELETE_SLOTS);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBContract.ProjectEntity.TABLE_NAME,null,null,null,null,null,null);
        dbHelper.logCursor(cursor);

        assertEquals("3y 1m 1d 1h 1m", "");
    }

}