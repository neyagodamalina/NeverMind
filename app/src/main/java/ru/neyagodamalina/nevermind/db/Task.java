package ru.neyagodamalina.nevermind.db;

import java.util.Calendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.neyagodamalina.nevermind.util.StringCutter;
import ru.neyagodamalina.nevermind.util.TaskState;

/**
 * Created by developer on 04.09.2017.
 */

@Entity(foreignKeys = @ForeignKey(
        entity = Project.class,
        parentColumns = "id",
        childColumns = "projectId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "id")})
public class Task extends Duration {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    String text;

    String title;

    long dateCreate;

    int state;

    @ColumnInfo(index = true)
    private long projectId;

    public Task(long id, long projectId, String text, long timeStart, long timeStop){
        super(timeStart, timeStop);
        this.id = id;
        this.text = text;
        this.title = StringCutter.cut(text);
        this.projectId = projectId;
        this.dateCreate = Calendar.getInstance().getTimeInMillis();
        this.state = TaskState.STATE_STOP;
    }

    // Default project with id = 1 must exists.
    @Ignore
    public Task(String text) {
        this(0, 1, text, 0, 0);
    }

    public long getId() {
        return id;
    }
    public long getProjectId() {
        return projectId;
    }
    public String getText() {
        return text;
    }

    @Ignore
    public String getTitle() {
        return title;
    }

    @Ignore
    public long getDateCreate() {
        return dateCreate;
    }

    @Ignore
    public int getState() {
        return state;
    }

    public void setState(int state){
        this.state = state;
    }




}
