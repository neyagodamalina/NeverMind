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

    public NMRepository(AppDatabase database){
        this.database = database;
        projectDao = database.getProjectDao();
        taskDao = database.getTaskDao();
        slotDao = database.getSlotDao();
    }

    public void deleteAllProjects(){
        projectDao.deleteAllProjects();
    }

    public void addTask(Task task){
        long id = taskDao.insert(task);
        Log.i(Constants.LOG_TAG, "Insert new task id=" + id);
    }

    public void editTask(Task task){
        taskDao.update(task);
        Log.i(Constants.LOG_TAG, "Update new task id=" + task.getId());
    }


    public void addProject(Project project){
        long id = projectDao.insertProject(project);
        Log.i(Constants.LOG_TAG, "Insert new project id=" + id);
    }

    public LiveData<List<Project>> getAllProjects(){
        return projectDao.selectAllProjects();
    }

    public void startTask(Task task){
        long timeStart = Calendar.getInstance().getTimeInMillis();
        Slot slot = new Slot(task.getId(), timeStart, timeStart); // When slot is working timeStop = timeStart.
        slotDao.insert(slot);
        task.setState(TaskState.STATE_REC);
        if (task.getTimeStop() == 0) // this is first work for task
            task.setDateStart(timeStart);
        taskDao.update(task);
    }


    public void stopTask(Task task){
        long timeStop = Calendar.getInstance().getTimeInMillis();
        Slot slot = slotDao.selectLastSlotForTask(task.getId());
        if (slot != null) {
            slot.setTimeStop(timeStop);
            slotDao.update(slot);
            task.setState(TaskState.STATE_STOP);
            task.setTimeStop(task.getTimeStop() + slot.getTimeStop() - slot.getTimeStart());
            task.setDateLast(timeStop);
            taskDao.update(task);
        }
    }

    public void deleteTask(Task task){
        taskDao.delete(task);
    }

    public LiveData<Task> getLiveDataTask(long id){
        return taskDao.selectTaskById(id);
    }



}
