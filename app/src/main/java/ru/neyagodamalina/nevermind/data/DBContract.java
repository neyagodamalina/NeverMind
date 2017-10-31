package ru.neyagodamalina.nevermind.data;

import android.provider.BaseColumns;

/**
 * Created by developer on 30.10.2017.
 */

public final class DBContract {
    public DBContract(){}

    public static final String DB_NAME = "db";

    public static abstract class ProjectEntity implements BaseColumns{
        public static final String TABLE_NAME = "project";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static abstract class TaskEntity implements BaseColumns{
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PROJECT_ID = "project_id";
    }

    public static abstract class SlotEntity implements BaseColumns{
        public static final String TABLE_NAME = "slot";
        public static final String COLUMN_NAME_TIME_START   = "time_start";
        public static final String COLUMN_NAME_TIME_STOP    = "time_stop";
        public static final String COLUMN_NAME_TASK_ID = "task_id";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_PROJECT =
            "CREATE TABLE " + ProjectEntity.TABLE_NAME + " (" +
                    ProjectEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ProjectEntity.COLUMN_NAME_NAME + TEXT_TYPE +
                    ")";


    public static final String SQL_CREATE_TASK =
            "CREATE TABLE " + TaskEntity.TABLE_NAME + " (" +
                    TaskEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TaskEntity.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    TaskEntity.COLUMN_NAME_PROJECT_ID + INTEGER_TYPE +
                    ")";

    public static final String SQL_CREATE_SLOT =
            "CREATE TABLE " + SlotEntity.TABLE_NAME + " (" +
                    SlotEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SlotEntity.COLUMN_NAME_TIME_START + INTEGER_TYPE + COMMA_SEP +
                    SlotEntity.COLUMN_NAME_TIME_STOP + INTEGER_TYPE + COMMA_SEP +
                    SlotEntity.COLUMN_NAME_TASK_ID + INTEGER_TYPE +
                    ")";

    public static final String SQL_DELETE_PROJECTS =
            "DROP TABLE IF EXISTS " + ProjectEntity.TABLE_NAME;
    public static final String SQL_DELETE_TASKS =
            "DROP TABLE IF EXISTS " + TaskEntity.TABLE_NAME;
    public static final String SQL_DELETE_SLOTS =
            "DROP TABLE IF EXISTS " + SlotEntity.TABLE_NAME;



}
