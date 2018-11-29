package ru.neyagodamalina.nevermind.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

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
    public long insert(Slot slot);


}
