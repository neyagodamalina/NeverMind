package ru.neyagodamalina.nevermind.repository;

import androidx.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import ru.neyagodamalina.nevermind.db.AppDatabase;
import ru.neyagodamalina.nevermind.db.Project;
import ru.neyagodamalina.nevermind.db.ProjectDao;

public class ListProjectsRepository {
    private final ProjectDao projectDao;

    public ListProjectsRepository(Context context){
        projectDao = AppDatabase.getInstance(context).getProjectDao();
    }

    public LiveData<List<Project>> getAllProjects(){
        return projectDao.selectAllProjects();
    }

}


