package ru.neyagodamalina.nevermind.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import ru.neyagodamalina.nevermind.util.Duration;
import ru.neyagodamalina.nevermind.util.StringCutter;

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

    @ColumnInfo(index = true)
    private long projectId;

    public Task(long id, long projectId, String text, long timeStart, long timeStop){
        super(timeStart, timeStop);
        this.id = id;
        this.text = text;
        this.title = StringCutter.cut(text);
        this.projectId = projectId;
    }

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




}