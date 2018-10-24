package ru.neyagodamalina.nevermind.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import ru.neyagodamalina.nevermind.util.Duration;
import ru.neyagodamalina.nevermind.util.StringCutter;

/**
 * Created by developer on 05.09.2017.
 */

@Entity(indices = {@Index(value = "id")})
public class Project extends Duration {
    @PrimaryKey(autoGenerate = true)
    private final long id;

    String text;
    String title;

    public Project(long id, String text, long timeStart, long timeStop){
        super(timeStart, timeStop);
        this.id = id;
        this.text = text;
        this.title = StringCutter.cut(text);
    }

    @Ignore
    public Project(String text) {
        this(0, text, 0, 0);
    }

    public long getId(){return this.id;}

    @Override
    public String toString() {
        return "Project (id,name) = (" + this.getId() + "," + this.title + ")\t" + super.toString();
    }

    @Ignore
    public String getTitle() {
        return title;
    }
}
