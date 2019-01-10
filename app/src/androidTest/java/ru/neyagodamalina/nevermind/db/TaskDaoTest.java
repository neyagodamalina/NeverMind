package ru.neyagodamalina.nevermind.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import static org.junit.Assert.assertEquals;


import ru.neyagodamalina.nevermind.InitDatabaseForTest;
import ru.neyagodamalina.nevermind.LiveDataTestUtil;

import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.Mockito.verify;

/**
 * Created by developer on 14.11.2017.
 */

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest extends InitDatabaseForTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Test
    public void insert3Tasks() throws Exception {

        LiveData<List<Task>> tasks = mTaskDao.selectAllTasks();

        Task task1 = new Task("task 1");
        Task task2 = new Task("task 2");
        Task task3 = new Task("task 3");


        mTaskDao.insert(task1);
        mTaskDao.insert(task2);
        mTaskDao.insert(task3);
        assertEquals(3, LiveDataTestUtil.getValue(tasks).size());

    }

    @Test
    public void zeroTimeStartTask(){
        Task task = new Task("New task");
        long id = mTaskDao.insert(task);
        Task taskFromBD = mTaskDao.selectTaskById(id);
        assertEquals(0, taskFromBD.getTimeStart());
    }

    @Test
    public void zeroTimeStopTask(){
        Task task = new Task("New task");
        long id = mTaskDao.insert(task);
        Task taskFromBD = mTaskDao.selectTaskById(id);
        assertEquals(0, taskFromBD.getTimeStop());
    }


}