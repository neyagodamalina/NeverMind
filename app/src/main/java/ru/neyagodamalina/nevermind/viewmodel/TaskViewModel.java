package ru.neyagodamalina.nevermind.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.neyagodamalina.nevermind.db.Project;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.repository.NMRepository;

/**
 * Created by developer on 07.03.2018.
 */

public class TaskViewModel extends AndroidViewModel {

    private NMRepository NMRepository = new NMRepository(this.getApplication());
    private final Executor executor = Executors.newFixedThreadPool(1);

    public TaskViewModel(@NonNull Application application) {
        super(application);
    }

    public void addTask(final Task task){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NMRepository.addTask(task);
            }
        });
    }

    public void addProject(final Project project){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NMRepository.addProject(project);
            }
        });
    }

    public LiveData<List<Project>> getAllProjects(){
        return NMRepository.getAllProjects();
    }

}
