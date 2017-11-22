package ru.neyagodamalina.nevermind.business.persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Calendar;

import ru.neyagodamalina.nevermind.business.Duration;
import ru.neyagodamalina.nevermind.business.exception.WrongTimeStopTimeStartException;

/**
 * Created by developer on 04.09.2017.
 */

@Entity(indices = {@Index(value = "id")})
public class Task extends Duration {



    @PrimaryKey(autoGenerate = true)
    private final int id;
    String name;

    public Task(int id, String name, long timeStart, long timeStop){
        super(timeStart, timeStop);
        this.id = id;
        this.name = name;
    }

    @Ignore
    public Task(String name) {
        this(0, name, 0, 0);
    }

    public int getId() {
        return id;
    }
//    @PrimaryKey(autoGenerate = true)
//    private final int id;
//    private String name;
//
//    public Task(int id, String name){
//        this.id = id;
//        this.name = name;
//    }
//
//    @Ignore
//    private ArrayList<Duration> slots = new ArrayList<>();
//
//
//
//
//    public void start() {
//        if (slots.size() == 0)
//            this.setTimeStart(Calendar.getInstance().getTimeInMillis());
//    }
//
//    public void stop() {
//        try{
//            if (this.getTimeStart() <= this.getTimeStop())
//                throw new WrongTimeStopTimeStartException("Stop task before start.");
//
//            if (this.slots.size() == 0) {
//                this.setTimeStop(Calendar.getInstance().getTimeInMillis());
//                slots.add(new Duration(this.getTimeStart(), this.getTimeStop()));
//            }
//            else {
//                Duration slot = slots.get(slots.size() - 1);
//            slot.stop();
//            this.setTimeStop(slot.getTimeStop());
//        }
//        catch (WrongTimeStopTimeStartException e){
//            System.err.println(e);
//        }
//    }
}
