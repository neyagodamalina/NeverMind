package ru.neyagodamalina.nevermind.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by developer on 14.11.2017.
 */

@Entity(foreignKeys = @ForeignKey(
        entity = Task.class,
        parentColumns = "id",
        childColumns = "taskId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "id")})
public class Slot extends  Duration{

    @PrimaryKey(autoGenerate = true)
    private final long id;

    @ColumnInfo(index = true)
    private long taskId;


    public Slot(long id, long taskId, long timeStart, long timeStop){
        super(timeStart, timeStop);
        this.id = id;
        this.taskId = taskId;
    }

    @Ignore
    public Slot(long taskId, long timeStart, long timeStop){
        this(0, taskId, timeStart, timeStop);
    }

//    @Ignore
//    public Slot(long timeStart, long timeStop){
//        this(0, timeStart, timeStop);
//    }

    public long getId() {
        return id;
    }

    public long getTaskId() {
        return taskId;
    }

}
