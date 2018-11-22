package ru.neyagodamalina.nevermind.db;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.VisibleForTesting;
import android.util.Log;

import java.util.concurrent.Executors;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.util.Constants;


/**
 * Created by developer on 08.11.2017.
 * AppDatabase is production database on the file never_mind_db
 */

@Database(entities = {Slot.class, Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "never_mind_db";

    private static AppDatabase sInstance;

    public abstract SlotDao     getSlotDao();
    public abstract TaskDao     getTaskDao();
    public abstract ProjectDao  getProjectDao();

    private static Context context;

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    AppDatabase.context = context;
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).addCallback(dbCallback).build();
                    Log.d(Constants.LOG_TAG, "Database has builded.");

                }
            }
        }
        return sInstance;
    }

    public synchronized static AppDatabase getInstanceForTest(final Context context) {
        if ((sInstance == null) || (!sInstance.isOpen())) {
            AppDatabase.context = context;
                if ((sInstance == null) || ((sInstance != null) && (!sInstance.isOpen()))) {
                    sInstance = Room.inMemoryDatabaseBuilder(AppDatabase.context, AppDatabase.class)
                            .addCallback(dbCallback)
                            .build();
                    Log.d(Constants.LOG_TAG, "Database for test has builded.");
                }
            }
        return sInstance;
    }

    /**
     * Insert first default data into database
     */
    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    insertDefaultData(AppDatabase.context);
                }
            });
        }
    };

    private static void insertDefaultData(Context context) {
        Project defaultProject = new Project(1, context.getResources().getString(R.string.name_default_project), 0, 0);
        long projectId = sInstance.getProjectDao().insertProject(defaultProject);
        Log.d(Constants.LOG_TAG, "Populate database. Insert default data inserted Project with id = " + projectId);
    }

}

//    public static NMDatabase getDatabase(Context context) {
//        if ((database == null) || (!database.isOpen())) {
//            Log.d(Constants.LOG_TAG, "Database for app has initiated - " + AppDatabase.class.getName());
//            database =
//                    Room.databaseBuilder(context, NMDatabase.class, "never_mind_db")
//                            //Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
//                            // To simplify the exercise, allow queries on the main thread.
//                            // Don't do this on a real app!
//                            //.allowMainThreadQueries()
//                            //   .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6, MIGRATION_6_7, MIGRATION_7_8, MIGRATION_8_9, MIGRATION_9_10, MIGRATION_10_11, MIGRATION_11_12)
//                            // recreate the database if necessary
//                            .build();
//
//            // Insert default project, if it doesn't exist.
//            Executors.newSingleThreadExecutor().execute(new Runnable() {
//                @Override
//                public void run() {
//                    if (database.getProjectDao().selectDefaultProject() == null)
//                        init(AppDatabase.context);
//                }
// getProjectDao().insertProject(new Project(1, context.getResources().getString(R.string.name_default_project), 0, 0));
//            });
//        }
//
//        return database;
//    }


