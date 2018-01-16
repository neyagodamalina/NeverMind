package ru.neyagodamalina.nevermind.business.persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

import ru.neyagodamalina.nevermind.business.Duration;

import static android.R.attr.id;

/**
 * Created by developer on 05.09.2017.
 */

@Entity(indices = {@Index(value = "id")})
public class Project extends Duration {
    @PrimaryKey(autoGenerate = true)
    private final long id;

    String name;

    public Project(long id, String name, long timeStart, long timeStop){
        super(timeStart, timeStop);
        this.id = id;
        this.name = name;
    }

    public long getId(){return this.id;}
}
