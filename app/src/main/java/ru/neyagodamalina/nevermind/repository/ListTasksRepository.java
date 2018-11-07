package ru.neyagodamalina.nevermind.repository;

import androidx.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import ru.neyagodamalina.nevermind.db.AppDatabase;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.db.TaskDao;

/**
 * Created by developer on 14.03.2018.
 */

public class ListTasksRepository {
    private final TaskDao taskDao;

    public ListTasksRepository(Context context){
        taskDao = AppDatabase.getInstance(context).getTaskDao();
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskDao.selectAllTasks();
    }
}
