package ru.neyagodamalina.nevermind.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.List;
import static org.junit.Assert.assertEquals;


import ru.neyagodamalina.nevermind.InitDatabaseForTest;
import ru.neyagodamalina.nevermind.LiveDataTestUtil;

import static org.mockito.Mockito.verify;

/**
 * Created by developer on 14.11.2017.
 */

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest extends InitDatabaseForTest {


    @Test
    public void insert3Tasks() throws Exception {

        LiveData<List<Task>> tasks = mTaskDao.selectAllTasks();

        Task task1 = new Task("task 1");
        Task task2 = new Task("task 2");
        Task task3 = new Task("task 3");


        mTaskDao.insertTask(task1);
        mTaskDao.insertTask(task2);
        mTaskDao.insertTask(task3);
        assertEquals(3, LiveDataTestUtil.getValue(tasks).size());

    }


}