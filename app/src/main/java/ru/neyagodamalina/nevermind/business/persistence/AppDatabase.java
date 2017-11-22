package ru.neyagodamalina.nevermind.business.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ru.neyagodamalina.nevermind.business.Duration;

/**
 * Created by developer on 08.11.2017.
 */

@Database(entities = {Slot.class, Task.class}, version = 4, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract SlotDao getSlotDao();
    public abstract TaskDao getTaskDao();

    public static AppDatabase getDatabase(Context context) {
        if ((INSTANCE == null) || (!INSTANCE.isOpen())){
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "never_mind_db")
//Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                            // Don't do this on a real app!
                            //.allowMainThreadQueries()
                            // recreate the database if necessary
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
