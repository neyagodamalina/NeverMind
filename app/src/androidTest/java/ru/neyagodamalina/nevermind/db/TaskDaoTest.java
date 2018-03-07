package ru.neyagodamalina.nevermind.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.List;


import ru.neyagodamalina.nevermind.CommonConnectDatabase;

import static org.mockito.Mockito.verify;

/**
 * Created by developer on 14.11.2017.
 */

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest extends CommonConnectDatabase {

    @Mock
    private Observer<List<Task>> observer;


    @Test
    public void insert3Tasks() throws Exception {

        LiveData<List<Task>> liveDataTasks = database.getTaskDao().selectAllTasks();
        liveDataTasks.observeForever(observer);

        Task task1 = new Task("task 1");
        Task task2 = new Task("task 2");
        Task task3 = new Task("task 3");


        database.getTaskDao().insertTask(task1);
        database.getTaskDao().insertTask(task2);
        database.getTaskDao().insertTask(task3);

        Thread.sleep(1000); // необходимо, чтобы observer получил обновленные данные

        verify(observer).onChanged(liveDataTasks.getValue());
    }


}