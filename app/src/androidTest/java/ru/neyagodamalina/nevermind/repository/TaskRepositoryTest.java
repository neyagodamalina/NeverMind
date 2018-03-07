package ru.neyagodamalina.nevermind.repository;

import org.junit.Test;

import ru.neyagodamalina.nevermind.CommonConnectDatabase;
import ru.neyagodamalina.nevermind.db.Project;
import ru.neyagodamalina.nevermind.db.Slot;
import ru.neyagodamalina.nevermind.db.Task;

import static org.junit.Assert.*;

/**
 * Created by developer on 05.03.2018.
 */
public class TaskRepositoryTest extends CommonConnectDatabase {
    @Test
    public void deleteAllProjects() throws Exception {
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

        taskRepository.deleteAllProjects();

        // 1 - default project, 0 - tasks, 0 - slots
        assertArrayEquals(new long[] {1,0,0}, new long[] {
                database.getProjectDao().selectAllProjects().size(),
                database.getTaskDao().selectAllTasks().getValue()==null? 0 : database.getTaskDao().selectAllTasks().getValue().size(),
                database.getSlotDao().selectAllSlot().size()});
    }
}