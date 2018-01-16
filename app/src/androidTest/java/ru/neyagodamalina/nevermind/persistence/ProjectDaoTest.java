package ru.neyagodamalina.nevermind.persistence;

import org.junit.Test;

import ru.neyagodamalina.nevermind.business.persistence.Project;
import ru.neyagodamalina.nevermind.business.persistence.ProjectDao;
import ru.neyagodamalina.nevermind.business.persistence.Slot;
import ru.neyagodamalina.nevermind.business.persistence.Task;
import ru.neyagodamalina.nevermind.business.util.Constants;
import ru.neyagodamalina.nevermind.persistence.CommonConnectDatabase;
import android.util.Log;

import static org.junit.Assert.*;

/**
 * Created by developer on 22.11.2017.
 */
public class ProjectDaoTest extends CommonConnectDatabase{
    @Test
    public void insertProject() throws Exception {
        Project project = new Project(0, "Lonely project", 0, 0);
        database.getProjectDao().insertProject(project);
        assertEquals(2, database.getProjectDao().selectAllProjects().size());

    }


    @Test
    public void deleteProject() throws Exception {

        Project project = new Project(0, "Project1", 0, 0);
        long projectId = database.getProjectDao().insertProject(project);

        Task task1 = new Task(0, projectId, "Task11 for project1", 0, 0);
        database.getTaskDao().insertTask(task1);

        Task task2 = new Task(0, projectId, "Task12 for project1", 0, 0);
        long taskId2 = database.getTaskDao().insertTask(task2);

        Slot slot1 = new Slot(taskId2, 1,2);
        database.getSlotDao().insertSlot(slot1);

        Slot slot2 = new Slot(taskId2, 3,4);
        database.getSlotDao().insertSlot(slot2);

        Slot slot3 = new Slot(taskId2, 5,6);
        database.getSlotDao().insertSlot(slot3);

        database.getProjectDao().deleteAllProjects();

        assertArrayEquals(new long[] {0,0,0}, new long[] {
                database.getProjectDao().selectAllProjects().size(),
                database.getTaskDao().selectAllTasks().size(),
                database.getSlotDao().selectAllSlot().size()});
    }

}