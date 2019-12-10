package ru.neyagodamalina.nevermind.repository;

import org.junit.Rule;
import org.junit.Test;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import ru.neyagodamalina.nevermind.InitDatabaseForTest;
import ru.neyagodamalina.nevermind.LiveDataTestUtil;
import ru.neyagodamalina.nevermind.db.Task;

import static org.junit.Assert.*;

public class NMRepositoryTest  extends InitDatabaseForTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void taskStartStop() throws Exception{
        NMRepository repository = new NMRepository(database);
        Task task = new Task("New task");
        long id = mTaskDao.insert(task);
        Task taskFromBD = LiveDataTestUtil.getValue(mTaskDao.selectTaskById(id));
        repository.startTask(taskFromBD);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repository.stopTask(taskFromBD);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        repository.startTask(taskFromBD);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repository.stopTask(taskFromBD);


        assertEquals(4000, taskFromBD.getTimeStop() - task.getTimeStart(), 100);

    }


}