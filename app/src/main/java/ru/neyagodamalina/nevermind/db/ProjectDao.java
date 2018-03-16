package ru.neyagodamalina.nevermind.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by developer on 22.11.2017.
 */

@Dao
public interface ProjectDao {

    @Insert
    public long insertProject(Project project);

    @Query("delete from project")
    public void deleteAllProjects();

    @Delete
    public void deleteProject(Project project);

    @Query("select * from project order by title")
    public LiveData<List<Project>> selectAllProjects();

    @Query("select * from project where id = 1")
    public Project selectDefaultProject();


}
