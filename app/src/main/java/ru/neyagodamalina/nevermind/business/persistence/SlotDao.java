package ru.neyagodamalina.nevermind.business.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.neyagodamalina.nevermind.business.Duration;

/**
 * Created by developer on 08.11.2017.
 */

@Dao
public interface SlotDao {

    @Query("select * from slot")
    public List<Slot> selectAllSlot();

    @Query("select * from slot where taskId = :taskId")
    public List<Slot> selectSlotsForTask(long taskId);

    @Insert
    public long insertSlot(Slot slot);


}
