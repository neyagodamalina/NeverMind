package ru.neyagodamalina.nevermind.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ru.neyagodamalina.nevermind.MainActivity;
import ru.neyagodamalina.nevermind.business.util.Constants;

/**
 * Created by developer on 30.10.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBContract.SQL_CREATE_PROJECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DBContract.SQL_DELETE_PROJECTS);
        db.execSQL(DBContract.SQL_DELETE_TASKS);
        db.execSQL(DBContract.SQL_DELETE_SLOTS);
        onCreate(db);
    }

    public void insertTestData(){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.ProjectEntity.COLUMN_NAME_NAME, "Стройка дома");
        db.insert(DBContract.ProjectEntity.TABLE_NAME, null, contentValues);

        contentValues.put(DBContract.ProjectEntity.COLUMN_NAME_NAME, "Посадка дерева");
        db.insert(DBContract.ProjectEntity.TABLE_NAME, null, contentValues);

        contentValues.put(DBContract.ProjectEntity.COLUMN_NAME_NAME, "Родить сына");
        db.insert(DBContract.ProjectEntity.TABLE_NAME, null, contentValues);

        db.close();
    }

    public void logCursor(Cursor cursor){
        if (cursor != null){
            if (cursor.moveToFirst()){
                String str;
                do{
                    str = "";
                    for (String cn : cursor.getColumnNames()){
                        str = str.concat(cn + " - " + cursor.getString(cursor.getColumnIndex(cn)) + " ;");
                    }
                    Log.d(Constants.LOG_TAG, str);
                } while (cursor.moveToNext());
            }
        }
    }

}
