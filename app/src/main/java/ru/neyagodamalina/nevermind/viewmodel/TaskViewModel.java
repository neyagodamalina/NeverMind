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
import ru.neyagodamalina.nevermind.repository.TaskRepository;

/**
 * Created by developer on 07.03.2018.
 */

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository taskRepository = new TaskRepository(this.getApplication());
    private final Executor executor = Executors.newFixedThreadPool(1);

    public TaskViewModel(@NonNull Application application) {
        super(application);
    }

    public void addTask(final Task task){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskRepository.addTask(task);
            }
        });
    }

    public void addProject(final Project project){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskRepository.addProject(project);
            }
        });
    }

    public LiveData<List<Project>> getAllProjects(){
        return taskRepository.getAllProjects();
    }

}
