package ru.neyagodamalina.nevermind.db;

import android.arch.lifecycle.LiveData;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Iterator;
import java.util.List;

import ru.neyagodamalina.nevermind.LiveDataTestUtil;
import ru.neyagodamalina.nevermind.InitDatabaseForTest;
import ru.neyagodamalina.nevermind.util.Constants;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by developer on 22.11.2017.
 */
@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest extends InitDatabaseForTest {

    @Test
    public void deleteProject() throws Exception {

        Project project = new Project(0, "Project1", 0, 0);
        long projectId = mProjectDao.insertProject(project);

        Task task1 = new Task(0, projectId, "Task11 for project1", 0, 0);
        mTaskDao.insertTask(task1);

        Task task2 = new Task(0, projectId, "Task12 for project1", 0, 0);
        long taskId2 = mTaskDao.insertTask(task2);

        Slot slot1 = new Slot(taskId2, 1, 2);
        mSlotDao.insertSlot(slot1);

        Slot slot2 = new Slot(taskId2, 3, 4);
        mSlotDao.insertSlot(slot2);

        Slot slot3 = new Slot(taskId2, 5, 6);
        mSlotDao.insertSlot(slot3);

        Project projectFromDB = null;

        projectFromDB = mProjectDao.selectProjectById(projectId);

        int countDel = mProjectDao.deleteProject(projectFromDB);

        LiveData<List<Project>> projects = mProjectDao.selectAllProjects();
        LiveData<List<Task>> tasks = mTaskDao.selectAllTasks();
        LiveData<List<Slot>> slots = mSlotDao.selectAllSlots();

        assertEquals(1, countDel);

        // only 1 default project must be, 0 - tasks, 0 - slots
        assertArrayEquals(new long[]{1, 0, 0}, new long[]{
                LiveDataTestUtil.getValue(projects).size(),
                LiveDataTestUtil.getValue(tasks).size(),
                LiveDataTestUtil.getValue(slots).size()});
    }

    @Test
    public void insertProject() throws Exception {
        Project project = new Project(0, "Lonely project", 0, 0);
        long projectId = mProjectDao.insertProject(project);
        Log.d(Constants.LOG_TAG, "Test. Insert project with id = " + projectId);
        assertEquals(2, LiveDataTestUtil.getValue(mProjectDao.selectAllProjects()).size());
    }

    @Test
    public void deleteAllProjects() throws Exception {
        Project project = new Project(0, "Project1", 0, 0);
        long projectId = mProjectDao.insertProject(project);

        Task task1 = new Task(0, projectId, "Task11 for project1", 0, 0);
        mTaskDao.insertTask(task1);

        Task task2 = new Task(0, projectId, "Task12 for project1", 0, 0);
        long taskId2 = mTaskDao.insertTask(task2);

        Slot slot1 = new Slot(taskId2, 1, 2);
        mSlotDao.insertSlot(slot1);

        Slot slot2 = new Slot(taskId2, 3, 4);
        mSlotDao.insertSlot(slot2);

        Slot slot3 = new Slot(taskId2, 5, 6);
        mSlotDao.insertSlot(slot3);

        mProjectDao.deleteAllProjects();

        // 1 - default project, 0 - tasks, 0 - slots
        assertArrayEquals(new long[]{1, 0, 0}, new long[]{
                LiveDataTestUtil.getValue(mProjectDao.selectAllProjects()).size(),
                LiveDataTestUtil.getValue(mTaskDao.selectAllTasks()).size(),
                LiveDataTestUtil.getValue(mSlotDao.selectAllSlots()).size()});
    }


}