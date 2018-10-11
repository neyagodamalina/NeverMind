package ru.neyagodamalina.nevermind.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.neyagodamalina.nevermind.util.Duration;

/**
 * Created by developer on 08.11.2017.
 */

@Dao
public interface SlotDao {

    @Query("select * from slot")
    public LiveData<List<Slot>> selectAllSlots();

    @Query("select * from slot where taskId = :taskId")
    public List<Slot> selectSlotsForTask(long taskId);

    @Insert
    public long insertSlot(Slot slot);


}
