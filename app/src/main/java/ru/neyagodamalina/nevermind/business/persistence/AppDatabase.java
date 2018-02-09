package ru.neyagodamalina.nevermind.business.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import ru.neyagodamalina.nevermind.business.Duration;
import ru.neyagodamalina.nevermind.business.util.Constants;


/**
 * Created by developer on 08.11.2017.
 */

@Database(entities = {Slot.class, Task.class, Project.class}, version = 6, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;


    public abstract SlotDao getSlotDao();
    public abstract TaskDao getTaskDao();
    public abstract ProjectDao getProjectDao();

    public static AppDatabase getDatabase(Context context) {

        if ((INSTANCE == null) || (!INSTANCE.isOpen())){
            Log.d(Constants.LOG_TAG, "Build database");
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "never_mind_db")
//Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                            // Don't do this on a real app!
                            .allowMainThreadQueries()
                         //   .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6, MIGRATION_6_7, MIGRATION_7_8, MIGRATION_8_9, MIGRATION_9_10, MIGRATION_10_11, MIGRATION_11_12)
                            // recreate the database if necessary
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(Constants.LOG_TAG, "MIGRATION_1_2 Insert first project \"My live\"");
            database.beginTransaction();
            database.execSQL("insert into project values(0,\"My live\", 0,0)");
            database.endTransaction();
        }
    };

    static Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(Constants.LOG_TAG, "MIGRATION_2_3 Insert first project \"My live\"");
            database.beginTransaction();
            database.execSQL("insert into project values(0,\"My live\", 0,0)");
            database.endTransaction();
        }
    };

    static Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(Constants.LOG_TAG, "MIGRATION_3_4 Insert first project \"My live\"");
            database.beginTransaction();
            database.execSQL("insert into project values(0,\"My live\", 0,0)");
            database.endTransaction();
        }
    };

    static Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(Constants.LOG_TAG, "MIGRATION_4_5 Insert first project \"My live\"");
            database.beginTransaction();
            database.execSQL("insert into project values(0,\"My live\", 0,0)");
            database.endTransaction();
        }
    };

    static Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(Constants.LOG_TAG, "MIGRATION_5_6 Insert first project \"My live\"");
            database.execSQL("alter table task add column timeCreate INTEGER NOT NULL DEFAULT 0");
            database.execSQL("insert into project values(0,\"My live\", 0,0)");
        }
    };

    static Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(Constants.LOG_TAG, "MIGRATION_6_7 Insert first project \"My live\"");
            database.execSQL("insert into project values(0,\"My live\", 0,0)");
        }
    };

    static Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(Constants.LOG_TAG, "MIGRATION_7_8 Insert first project \"My live\"");
            database.beginTransaction();
            database.execSQL("insert into project values(0,\"My live\", 0,0)");
            database.endTransaction();
        }
    };

    static Migration MIGRATION_8_9 = new Migration(8, 9) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(Constants.LOG_TAG, "MIGRATION_9_10 Insert first project \"My live\"");
            database.beginTransaction();
            database.execSQL("insert into project values(0,\"My live\", 0,0)");
            database.endTransaction();
        }
    };

    static Migration MIGRATION_9_10 = new Migration(9, 10) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(Constants.LOG_TAG, "MIGRATION_10_11 Insert first project \"My live\"");
            database.beginTransaction();
            database.execSQL("insert into project values(0,\"My live\", 0,0)");
            database.endTransaction();
        }
    };


    static Migration MIGRATION_10_11 = new Migration(10, 11) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(Constants.LOG_TAG, "MIGRATION_10_11 Insert first project \"My live\"");
            database.beginTransaction();
            database.execSQL("insert into project values(0,\"My live\", 0,0)");
            database.endTransaction();
        }
    };

    static Migration MIGRATION_11_12 = new Migration(11, 12) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(Constants.LOG_TAG, "MIGRATION_11_12 Insert first project \"My live\"");
            database.beginTransaction();
            database.execSQL("insert into project values(0,\"My live\", 0,0)");
            database.endTransaction();
        }
    };


}
