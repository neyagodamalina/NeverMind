package ru.neyagodamalina.nevermind.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.neyagodamalina.nevermind.db.Project;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.repository.ListTasksRepository;
import ru.neyagodamalina.nevermind.repository.TaskRepository;

/**
 * Created by developer on 14.03.2018.
 */

public class ListTasksViewModel extends AndroidViewModel {

    private ListTasksRepository listTasksRepository = new ListTasksRepository(this.getApplication());
    //private final Executor executor = Executors.newFixedThreadPool(1);

    public ListTasksViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Task>> getAllTasks(){
        return listTasksRepository.getAllTasks();
    }
}
