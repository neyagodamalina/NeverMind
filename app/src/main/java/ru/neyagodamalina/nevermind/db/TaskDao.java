package ru.neyagodamalina.nevermind.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by developer on 14.11.2017.
 */

@Dao
public interface TaskDao {

    @Insert
    public long insert(Task task);

    @Query("select * from task")
    public LiveData<List<Task>> selectAllTasks();

    @Query("select * from task where id = :taskId")
    public Task selectTaskById(int taskId);

    @Query("delete from task")
    public void deleteAllTasks();

    @Update
    void update(Task task);

}
