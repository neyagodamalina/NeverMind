package ru.neyagodamalina.nevermind.repository;

import android.content.Context;
import android.util.Log;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.AppDatabase;
import ru.neyagodamalina.nevermind.db.Project;
import ru.neyagodamalina.nevermind.db.ProjectDao;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.db.TaskDao;
import ru.neyagodamalina.nevermind.util.Constants;

/**
 * Created by developer on 05.03.2018.
 */

public class TaskRepository {
    private final ProjectDao projectDao;
    private final TaskDao taskDao;
    private final AppDatabase database;
    private Context context;

    public TaskRepository(Context context){
        this.context = context;
        this.database = AppDatabase.getDatabase(context);
        projectDao = database.getProjectDao();
        taskDao = database.getTaskDao();
    }

    public void deleteAllProjects(){
        projectDao.deleteAllProjects();
        // init add the default project. It exists always.
        AppDatabase.init(context);
    }

    public void addTask(Task task){
        long id = taskDao.insertTask(task);
        Log.d(Constants.LOG_TAG, "Insert new task id=" + id);
    }


    public AppDatabase getDatabase(){
        return database;
    }


}
