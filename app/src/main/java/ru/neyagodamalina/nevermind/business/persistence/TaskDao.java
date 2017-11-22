package ru.neyagodamalina.nevermind.business.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by developer on 14.11.2017.
 */

@Dao
public interface TaskDao {

    @Insert
    public long insertTask(Task task);

    @Query("select * from task")
    public List<Task> selectAllTasks();

    @Query("select * from task where id = :taskId")
    public Task selectTaskById(int taskId);

    @Query("delete from task")
    public void deleteAllTasks();

}
