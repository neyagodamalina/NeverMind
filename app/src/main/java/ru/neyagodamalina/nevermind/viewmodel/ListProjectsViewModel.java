package ru.neyagodamalina.nevermind.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import ru.neyagodamalina.nevermind.db.Project;
import ru.neyagodamalina.nevermind.repository.ListProjectsRepository;

public class ListProjectsViewModel  extends AndroidViewModel{

    private ListProjectsRepository listProjectsRepository = new ListProjectsRepository(this.getApplication());

    public ListProjectsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Project>> getAllProjects(){
        return listProjectsRepository.getAllProjects();
    }

}


