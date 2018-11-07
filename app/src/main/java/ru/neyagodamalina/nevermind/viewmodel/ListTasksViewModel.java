package ru.neyagodamalina.nevermind.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.repository.ListTasksRepository;

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
