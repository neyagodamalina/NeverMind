package ru.neyagodamalina.nevermind.persistence;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;

import ru.neyagodamalina.nevermind.business.persistence.Slot;
import ru.neyagodamalina.nevermind.business.persistence.Task;

import static org.junit.Assert.assertEquals;


/**
 * Created by developer on 08.11.2017.
 */
@RunWith(AndroidJUnit4.class)
public class SlotDaoTest extends CommonConnectDatabase {

    @Test
    public void insert2SlotsFor1Task() throws Exception {

        database.getTaskDao().deleteAllTasks();

        Task task = new Task("task insert2SlotsFor1Task");
        long taskId = database.getTaskDao().insertTask(task);

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.JANUARY, 1, 23, 59, 59);
        Slot slot1 = new Slot(taskId, start.getTimeInMillis(), stop.getTimeInMillis());


        start = Calendar.getInstance();
        start.set(2017, Calendar.NOVEMBER, 1, 0, 0, 0);
        stop = Calendar.getInstance();
        stop.set(2017, Calendar.NOVEMBER, 2, 23, 59, 59);

        Slot slot2 = new Slot(taskId, start.getTimeInMillis(), stop.getTimeInMillis());

        database.getSlotDao().insertSlot(slot1);
        database.getSlotDao().insertSlot(slot2);

        List<Slot> slots = database.getSlotDao().selectSlotsForTask(taskId);
        assertEquals(2, slots.size());
    }

}

