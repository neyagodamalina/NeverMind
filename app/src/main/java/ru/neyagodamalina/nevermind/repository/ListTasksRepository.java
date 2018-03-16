package ru.neyagodamalina.nevermind.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.neyagodamalina.nevermind.db.AppDatabase;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.db.TaskDao;

/**
 * Created by developer on 14.03.2018.
 */

public class ListTasksRepository {
    private final TaskDao taskDao;

    public ListTasksRepository(Context context){
        taskDao = AppDatabase.getDatabase(context).getTaskDao();
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskDao.selectAllTasks();
    }
}
