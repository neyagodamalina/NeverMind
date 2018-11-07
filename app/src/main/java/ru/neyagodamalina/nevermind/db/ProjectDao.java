package ru.neyagodamalina.nevermind.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Created by developer on 22.11.2017.
 */

@Dao
public interface ProjectDao {

    @Insert
    public long insertProject(Project project);

    @Query("delete from project where id <> 1")
    public void deleteAllProjects();

    @Delete
    public int deleteProject(Project project);

    @Query("select * from project order by title")
    public LiveData<List<Project>> selectAllProjects();

    @Query("select * from project where id = 1")
    public Project selectDefaultProject();

    @Query("select * from project where id = :id")
    public Project selectProjectById(long id);


}
