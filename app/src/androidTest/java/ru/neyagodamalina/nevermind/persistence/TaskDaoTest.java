package ru.neyagodamalina.nevermind.persistence;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ru.neyagodamalina.nevermind.business.persistence.Task;

import static org.junit.Assert.*;

/**
 * Created by developer on 14.11.2017.
 */

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest extends CommonConnectDatabase {
    @Test
    public void insert3Tasks() throws Exception {

        database.getTaskDao().deleteAllTasks();
        Task task1 = new Task("task 1");
        Task task2 = new Task("task 2");
        Task task3 = new Task("task 3");

        database.getTaskDao().insertTask(task1);
        database.getTaskDao().insertTask(task2);
        database.getTaskDao().insertTask(task3);

        List<Task> tasks = database.getTaskDao().selectAllTasks();

        assertEquals(3, tasks.size());
    }


}