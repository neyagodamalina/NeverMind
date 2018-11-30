package ru.neyagodamalina.nevermind.repository;

import androidx.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import ru.neyagodamalina.nevermind.db.AppDatabase;
import ru.neyagodamalina.nevermind.db.Project;
import ru.neyagodamalina.nevermind.db.ProjectDao;
import ru.neyagodamalina.nevermind.db.Slot;
import ru.neyagodamalina.nevermind.db.SlotDao;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.db.TaskDao;
import ru.neyagodamalina.nevermind.util.Constants;
import ru.neyagodamalina.nevermind.util.TaskState;

/**
 * Created by developer on 05.03.2018.
 */

public class NMRepository {
    private final ProjectDao projectDao;
    private final TaskDao taskDao;
    private final SlotDao slotDao;
    private final AppDatabase database;
    private Context context;

    public NMRepository(Context context){
        this.context = context;
        this.database = AppDatabase.getInstance(context);
        projectDao = database.getProjectDao();
        taskDao = database.getTaskDao();
        slotDao = database.getSlotDao();
    }

    public void deleteAllProjects(){
        projectDao.deleteAllProjects();
    }

    public void addTask(Task task){
        long id = taskDao.insert(task);
        Log.d(Constants.LOG_TAG, "Insert new task id=" + id);
    }


    public AppDatabase getDatabase(){
        return database;
    }

    public void addProject(Project project){
        long id = projectDao.insertProject(project);
        Log.d(Constants.LOG_TAG, "Insert new project id=" + id);
    }

    public LiveData<List<Project>> getAllProjects(){
        return projectDao.selectAllProjects();
    }

    public void startTask(Task task){
        Slot slot = new Slot(task.getId(), Calendar.getInstance().getTimeInMillis(), 0);
        slotDao.insert(slot);
        if (task.getTimeStart() == 0) {
            task.setTimeStart(slot.getTimeStart());
            task.setTimeStop(slot.getTimeStart()); // While new task is working set timeStop = timeStart. When task was stopped will change stopTime to current time.
            task.setState(TaskState.STATE_PLAY);
        }
        taskDao.update(task);

        Log.d(Constants.LOG_TAG, "Start task id=" + task.getId());
    }
}
