package ru.neyagodamalina.nevermind.business.persistence;

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

    @Query("delete from project  where id <> 0")
    public void deleteAllProjects();

    @Delete
    public void deleteProject(Project project);

    @Query("select * from project")
    public List<Project> selectAllProjects();

    @Query("select * from project where id = 0")
    public Project selectDefaultProject();
}
