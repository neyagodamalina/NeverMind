package ru.neyagodamalina.nevermind.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Executors;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.util.Duration;
import ru.neyagodamalina.nevermind.util.Constants;


/**
 * Created by developer on 08.11.2017.
 */

@Database(entities = {Slot.class, Task.class, Project.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;


    public abstract SlotDao getSlotDao();

    public abstract TaskDao getTaskDao();

    public abstract ProjectDao getProjectDao();

    private static Context context;

    public static AppDatabase getDatabase(Context context) {

        AppDatabase.context = context;
        if ((INSTANCE == null) || (!INSTANCE.isOpen())) {
            Log.d(Constants.LOG_TAG, "Build database");
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "never_mind_db")
                            //Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                            // Don't do this on a real app!
                            //.allowMainThreadQueries()
                            //   .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6, MIGRATION_6_7, MIGRATION_7_8, MIGRATION_8_9, MIGRATION_9_10, MIGRATION_10_11, MIGRATION_11_12)
                            // recreate the database if necessary
                            .build();

            // Insert default project, if it doesn't exist.
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (INSTANCE.getProjectDao().selectDefaultProject() == null)
                        init(AppDatabase.context);
                }
            });
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public static void init(Context context) {
        INSTANCE.getProjectDao().insertProject(new Project(1, context.getResources().getString(R.string.name_default_project), 0, 0));

    }


}